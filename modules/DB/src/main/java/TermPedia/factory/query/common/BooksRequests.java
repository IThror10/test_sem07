package TermPedia.factory.query.common;

import TermPedia.dto.ActionsException;
import TermPedia.queries.books.*;

public interface BooksRequests {
    String authorSearchQuery(BaseSearchBookByAuthorNameQuery query) throws ActionsException;
    String bookSearchQuery(BaseSearchBookByBookNameQuery query) throws ActionsException;
    String anySearchQuery(BaseSearchBookByTagQuery query) throws ActionsException;
    String termSearchQuery(BaseSearchBookByTermQuery query) throws ActionsException;
    String connectedTermsSearchQuery(BaseSearchBookByTermQuery query) throws ActionsException;
}
