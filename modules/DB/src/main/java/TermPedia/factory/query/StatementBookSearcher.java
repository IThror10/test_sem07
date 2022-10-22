package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.dto.Book;
import TermPedia.dto.RatedBook;
import TermPedia.dto.TagBook;
import TermPedia.factory.adapters.ISearchAdapter;
import TermPedia.factory.query.common.BooksRequests;
import TermPedia.queries.books.*;
import org.jetbrains.annotations.NotNull;

import java.util.Vector;
import java.util.logging.Logger;

public class StatementBookSearcher implements BookSearcher {
    private final ISearchAdapter searcher;
    private final BooksRequests builder;
    private final static Logger logger;
    static { logger = Logger.getLogger("QueryDB"); }

    public StatementBookSearcher(@NotNull ISearchAdapter searcher, @NotNull BooksRequests builder) {
        this.builder = builder;
        this.searcher = searcher;
    }
    @Override
    public TagBookQueryResult searchByBookName(BaseSearchBookByBookNameQuery settings) throws ActionsException {
        String query = builder.bookSearchQuery(settings);
        try {
            Vector<TagBook> books = new Vector<>(settings.getSearchAmount());
            if (searcher.execute(query))
                while (searcher.next())
                    books.add(new TagBook(
                            searcher.getString("name"),
                            searcher.getString("type"),
                            searcher.getInt("year"),
                            jsonArrToVector(searcher.getString("authors")),
                            searcher.getDouble("rating")
                    ));
            return new TagBookQueryResult(books);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public TagBookQueryResult searchByTagName(BaseSearchBookByTagQuery settings) throws ActionsException {
        try {
            String query = builder.anySearchQuery(settings);
            Vector<TagBook> books = new Vector<>(settings.getSearchAmount());
            if (searcher.execute(query))
                while (searcher.next())
                    books.add(new TagBook(
                            searcher.getString("name"),
                            searcher.getString("type"),
                            searcher.getInt("year"),
                            jsonArrToVector(searcher.getString("authors")),
                            searcher.getDouble("rating")
                    ));
            return new TagBookQueryResult(books);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public BookQueryResult searchByAuthorName(BaseSearchBookByNameQuery settings) throws ActionsException {
        try {
            String query = builder.authorSearchQuery(settings);
            Vector<Book> books = new Vector<>(settings.getSearchAmount());
            if (searcher.execute(query))
                while (searcher.next())
                    books.add(new Book(
                            searcher.getString("name"),
                            searcher.getString("type"),
                            searcher.getInt("year"),
                            jsonArrToVector(searcher.getString("authors"))
                    ));
            return new BookQueryResult(books);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public RatedBookQueryResult searchByTermName(BaseSearchBookByTermQuery settings) throws ActionsException {
        String query = builder.termSearchQuery(settings);
        try {
            Vector<RatedBook> books = new Vector<>(settings.getSearchAmount());
            if (searcher.execute(query))
                while (searcher.next())
                    books.add(new RatedBook(
                            searcher.getString("name"),
                            searcher.getString("type"),
                            searcher.getInt("year"),
                            jsonArrToVector(searcher.getString("authors")),
                            searcher.getDouble("rating"),
                            searcher.getInt("rates_amount")
                    ));
            return new RatedBookQueryResult(books);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public RatedBookQueryResult searchConnectedBooks(BaseSearchBookByTermQuery settings) throws ActionsException {
        try {
            String query = builder.connectedTermsSearchQuery(settings);
            Vector<RatedBook> books = new Vector<>(settings.getSearchAmount());
            if (searcher.execute(query))
                while (searcher.next())
                    books.add(new RatedBook(
                            searcher.getString("name"),
                            searcher.getString("type"),
                            searcher.getInt("year"),
                            jsonArrToVector(searcher.getString("authors")),
                            searcher.getDouble("rating"),
                            searcher.getInt("rates_amount"),
                            searcher.getInt("user_rating")
                    ));
            return new RatedBookQueryResult(books);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    private Vector<String> jsonArrToVector(@NotNull String json) {
        Vector<String> vector = new Vector<>(5);

        int start_index = json.indexOf('"'), end_index = json.indexOf('"', start_index + 1);
        while (end_index != -1 && start_index != -1 && start_index < end_index) {
            vector.add(json.substring(start_index + 1, end_index));
            start_index = json.indexOf('"', end_index + 1);
            end_index = json.indexOf('"', start_index + 1);
        }
        return vector;
    }
}
