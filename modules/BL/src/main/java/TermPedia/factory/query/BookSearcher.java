package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.queries.books.*;

public interface BookSearcher {
    TagBookQueryResult searchByBookName(BaseSearchBookByBookNameQuery query) throws ActionsException;
    TagBookQueryResult searchByTagName(BaseSearchBookByTagQuery query) throws ActionsException;
    BookQueryResult searchByAuthorName(BaseSearchBookByNameQuery query) throws ActionsException;
    RatedBookQueryResult searchByTermName(BaseSearchBookByTermQuery query) throws ActionsException;
    RatedBookQueryResult searchConnectedBooks(BaseSearchBookByTermQuery query) throws ActionsException;
}
