CREATE SCHEMA if NOT EXISTS data;
CREATE TABLE IF NOT EXISTS data.terms (
    name varchar(50) PRIMARY KEY,
    description text,
    vector tsvector NOT NULL);
Create Index idx_terms_gin ON data.terms USING GIN (vector);
Create Index idx_terms_lower ON data.terms (lower(name));

CREATE TABLE IF NOT EXISTS data.tags (
    name varchar(50) UNIQUE,
    vector tsvector);
Create Index idx_tag_gin ON data.tags USING GIN (vector);
Create Index idx_tag_lower ON data.tags (lower(name));
INSERT INTO data.tags values(NULL, NULL);

CREATE TABLE IF NOT EXISTS data.authors (
    name varchar(50) PRIMARY KEY);

CREATE TABLE IF NOT EXISTS data.lit_types (
    name varchar(50) PRIMARY KEY,
    vector tsvector NOT NULL);
Create Index idx_littype_gin ON data.lit_types USING GIN (vector);
Create Index idx_littype_lower ON data.lit_types (lower(name));

CREATE TABLE IF NOT EXISTS data.users (
    UID int PRIMARY KEY,
    CHECK (UID >= 0));

CREATE TABLE IF NOT EXISTS data.lit (
    name varchar(50) NOT NULL,
    year int NOT NULL,
    type varchar(50) NOT NULL,
    authors jsonb NOT NULL,
    vector tsvector,
    LID serial UNIQUE,

    UNIQUE (name, year, type, authors),
    PRIMARY KEY (LID),
    FOREIGN KEY (type) REFERENCES data.lit_types (name));
Create Index idx_lit_gin ON data.lit USING GIN (vector);
Create Index idx_lit_lower ON data.lit (lower(name));


CREATE TABLE IF NOT EXISTS data.terms_tags (
    term varchar(50) NOT NULL,
    tag varchar(50),
    rates_amount int NOT NULL DEFAULT 0,
    rates_sum int NOT NULL DEFAULT 0,
    rating real NOT NULL DEFAULT 0,

    UNIQUE (term, tag),
    FOREIGN KEY (term) REFERENCES data.terms (name),
    FOREIGN KEY (tag) REFERENCES data.tags (name));

CREATE TABLE IF NOT EXISTS data.terms_lit (
    term varchar(50) NOT NULL,
    LID int NOT NULL,
    rates_amount int NOT NULL DEFAULT 0,
    rates_sum int NOT NULL DEFAULT 0,
    rating real NOT NULL DEFAULT 0,

    UNIQUE (term, LID),
    FOREIGN KEY (term) REFERENCES data.terms(name),
    FOREIGN KEY (LID) REFERENCES data.lit(LID));

CREATE TABLE IF NOT EXISTS data.authors_lit (
    author varchar(50) NOT NULL,
    LID int NOT NULL,

    UNIQUE (author, LID),
    FOREIGN KEY (author) REFERENCES data.authors (name),
    FOREIGN KEY (LID) REFERENCES data.lit (LID));

CREATE TABLE IF NOT EXISTS data.term_tag_rates (
    UID int NOT NULL,
    term varchar(50) NOT NULL,
    tag varchar(50),
    rating int NOT NULL,

    UNIQUE (UID, term, tag),
    FOREIGN KEY (UID) REFERENCES data.users (UID),
    FOREIGN KEY (term, tag) REFERENCES data.terms_tags (term, tag),
    CHECK (rating >= 0 and rating <= 5));

CREATE TABLE IF NOT EXISTS data.term_lit_rates (
    UID int NOT NULL,
    term varchar(50) NOT NULL,
    LID int NOT NULL,
    rating int NOT NULL,

    UNIQUE (UID, term, LID),
    FOREIGN KEY (UID) REFERENCES data.users (UID),
    FOREIGN KEY (term, LID) REFERENCES data.terms_lit (term, LID),
    CHECK (rating >= 0 and rating <= 5)
);


CREATE TABLE IF NOT EXISTS data.book_search (
    LID int NOT NULL,
    name varchar(50) NOT NULL,
    year int NOT NULL,
    type varchar(50) NOT NULL,
    authors jsonb NOT NULL,
    tag varchar(50),
    rating real NOT NULL DEFAULT 0,

    UNIQUE (name, tag),
    FOREIGN KEY (LID) REFERENCES data.lit (LID),
    FOREIGN KEY (tag) REFERENCES data.tags (name),
    CHECK (rating >= 0 and rating <= 5)
);
CREATE INDEX IF NOT EXISTS term_idx ON data.book_search (tag);



-- #### DATA.LIT
Create or Replace Function data.for_new_authors()
    Returns Trigger
AS $Body$
DECLARE
    i json;
Begin
    FOR i IN SELECT * FROM jsonb_array_elements(new.authors) LOOP
        -- Add Author
        INSERT INTO data.authors values (i#>>'{}')
            ON CONFLICT DO NOTHING;
        -- Add Author-Lit Pare
        INSERT INTO data.authors_lit VALUES (i#>>'{}', new.LID)
            ON CONFLICT DO NOTHING;
    END LOOP;
    -- Book search with Null Tag
    INSERT INTO data.book_search
        SELECT new.LID, new.name, new.year, new.type, new.authors, null;
    return new;
End $Body$ language plpgsql;

Create or Replace Trigger after_lit_insert
    AFTER INSERT on data.lit for each row
        execute Function data.for_new_authors();

-- #### DATA.TERMS_TAGS
Create or Replace Function data.on_new_term_tag()
    Returns Trigger
AS $Body$ Begin
    INSERT INTO data.book_search
        SELECT tl.LID, l.name, l.year, l.type, l.authors, new.tag
            FROM data.terms_lit tl join data.lit l on tl.term = new.term and l.lid = tl.lid
        ON CONFLICT DO NOTHING;
    return new;
End $Body$ language plpgsql;

Create or Replace Trigger after_terms_tags_insert
    AFTER INSERT on data.terms_tags for each row
        execute Function data.on_new_term_tag();

Create or Replace Function data.on_term_tag_rating_change()
    Returns Trigger
AS $Body$ Begin
    -- Change Tag Rating
    with lit_tag_rating(lit_id, tag_rating) as
        (SELECT tl.LID, max(tl.rating * tt.rating / 5)
            FROM data.terms_lit tl join data.terms_tags tt on tt.tag = new.tag and tt.term = tl.term
                GROUP BY tl.LID)
    UPDATE data.book_search
        SET rating = tag_rating
            FROM lit_tag_rating
                WHERE lid = lit_id and tag = new.tag;
    return new;
End $Body$ language plpgsql;

Create or Replace Trigger after_terms_tags_update
    AFTER UPDATE on data.terms_tags for each row
        execute Function data.on_term_tag_rating_change();

-- #### DATA.TERMS_LIT
Create or Replace Function data.on_new_term_lit()
    Returns Trigger
AS $Body$ Begin
    -- Associate book with new tags
    INSERT INTO data.book_search
        SELECT l.LID, l.name, l.year, l.type, l.authors, tt.tag
            FROM data.lit l join data.terms_tags tt on l.LID = new.LID and tt.term = new.term
        ON CONFLICT DO NOTHING;
    return new;
End $Body$ language plpgsql;

Create or Replace Trigger after_term_lit_insert
    AFTER INSERT on data.terms_lit for each row
        execute Function data.on_new_term_lit();

Create or Replace Function data.on_term_lit_rating_change()
    Returns Trigger
AS $Body$ Begin
    -- Change Tag Rating
    with lit_tag_rating (tag_name, tag_rating) as
        (SELECT tt.tag, max(tl.rating * tt.rating / 5) as rating
            FROM data.terms_lit tl join data.terms_tags tt on tl.lid = new.lid and tt.term = tl.term
                and tt.tag in (SELECT tag FROM data.terms_tags WHERE term = new.term)
            GROUP BY tt.tag)
    UPDATE data.book_search
        SET rating = tag_rating
            FROM lit_tag_rating
                WHERE tag = tag_name and lid = new.lid;
    return new;
End $Body$ language plpgsql;

Create or Replace Trigger after_terms_lit_update
    AFTER UPDATE on data.terms_lit for each row
        execute Function data.on_term_lit_rating_change();

-- #### Keep Actual Rating in data.terms_tags && data.terms_lit
Create or Replace Function data.keep_actual_rating()
    Returns Trigger
AS $Body$ Begin
    new.rating := new.rates_sum::real / new.rates_amount::real;
    return new;
End $Body$ language plpgsql;

Create or Replace Trigger before_tag_rating_change
    BEFORE UPDATE on data.terms_tags
        for each row
            execute Function data.keep_actual_rating();

Create or Replace Trigger before_lit_rating_change
    BEFORE UPDATE on data.terms_lit for each row
        execute Function data.keep_actual_rating();

-- #### Term_Lit_Rates
Create or Replace Function data.on_change_lit_rating()
    Returns Trigger
AS $Body$ Begin
    -- Changed rating
    IF (TG_OP = 'INSERT') then
        UPDATE data.terms_lit
        SET (rates_amount, rates_sum) = (rates_amount + 1, rates_sum + new.rating)
            WHERE lid = new.lid and term = new.term;
    ELSE
        UPDATE data.terms_lit
        SET rates_sum = rates_sum + new.rating - old.rating
            WHERE lid = new.lid and term = new.term;
    END IF;
    return new;
End $Body$ language plpgsql;

Create or Replace Trigger after_term_lit_rate_change
    AFTER INSERT or UPDATE on data.term_lit_rates for each row
        execute Function data.on_change_lit_rating();

-- #### Term_Tag_Rates
Create or Replace Function data.on_change_tag_rating()
    Returns Trigger
AS $Body$ Begin
    -- Changed rating
    IF (TG_OP = 'INSERT') then
        UPDATE data.terms_tags
        SET (rates_amount, rates_sum) = (rates_amount + 1, rates_sum + new.rating)
            WHERE term = new.term and tag = new.tag;
    ELSE
        UPDATE data.terms_tags
        SET rates_sum = rates_sum + new.rating - old.rating
            WHERE term = new.term and tag = new.tag;
    END IF;
    return new;
End $Body$ language plpgsql;

Create or Replace Trigger after_term_tag_rate_change
    AFTER INSERT or UPDATE on data.term_tag_rates
        for each row
            execute Function data.on_change_tag_rating();