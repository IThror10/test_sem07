package TermPedia.queries.visitors;

import TermPedia.dto.ActionsException;
import TermPedia.factory.query.*;
import TermPedia.queries.books.*;
import TermPedia.queries.instances.authors.*;
import TermPedia.queries.instances.tags.*;
import TermPedia.queries.instances.terms.*;
import TermPedia.queries.instances.types.*;

public class QueryHandlerVisitor implements QueryVisitor {
    public void visitFindTermQuery(FindTermByNameQuery query) throws ActionsException {
        TermsSearcher searcher = QueryFactory.instance().createTermSearcher();
        TermQueryResult result = searcher.getTermsByName(query);
        query.setResult(result);
    }

    @Override
    public void visitFindTagByNameQuery(FindTagByNameQuery query) throws ActionsException {
        TagsSearcher searcher = QueryFactory.instance().createTagSearcher();
        TagQueryResult result = searcher.getTagsByName(query);
        query.setResult(result);
    }

    @Override
    public void visitFindTagByTermNameQuery(FindTagByTermNameQuery query) throws ActionsException {
        TagsSearcher searcher = QueryFactory.instance().createTagSearcher();
        RatedTagQueryResult result = searcher.getTagsByTerm(query);
        query.setResult(result);
    }

    @Override
    public void visitFindAuthorByNameQuery(FindAuthorByNameQuery query) throws ActionsException {
        AuthorsSearcher searcher = QueryFactory.instance().createAuthorSearcher();
        AuthorQueryResult result = searcher.getAuthorByName(query);
        query.setResult(result);
    }

    @Override
    public void visitFindLitTypesByNameQuery(FindLitTypesByNameQuery query) throws ActionsException {
        LitTypesSearcher searcher = QueryFactory.instance().createLitTypesSearcher();
        LitTypesQueryResult result = searcher.getLitTypesByName(query);
        query.setResult(result);
    }

    @Override
    public void visitSearchBookByNameQuery(SearchBookByLikeNameQuery query) throws ActionsException {
        BookSearcher searcher = QueryFactory.instance().createBookSearcher();
        TagBookQueryResult result = searcher.searchByBookName(query);
        query.setResult(result);
    }

    @Override
    public void visitSearchBookByTagQuery(SearchBookByTagQuery query) throws ActionsException {
        BookSearcher searcher = QueryFactory.instance().createBookSearcher();
        TagBookQueryResult result = searcher.searchByTagName(query);
        query.setResult(result);
    }

    @Override
    public void visitSearchBookByAuthorQuery(SearchBookByAuthorQuery query) throws ActionsException {
        BookSearcher searcher = QueryFactory.instance().createBookSearcher();
        BookQueryResult result = searcher.searchByAuthorName(query);
        query.setResult(result);
    }

    @Override
    public void visitSearchBookByTermQuery(SearchBookByLikeTermQuery query) throws ActionsException {
        BookSearcher searcher = QueryFactory.instance().createBookSearcher();
        RatedBookQueryResult result = searcher.searchByTermName(query);
        query.setResult(result);
    }

    @Override
    public void visitSearchConnectedBooksQuery(SearchBookByTermQuery query) throws ActionsException {
        BookSearcher searcher = QueryFactory.instance().createBookSearcher();
        RatedBookQueryResult result = searcher.searchConnectedBooks(query);
        query.setResult(result);
    }
}
