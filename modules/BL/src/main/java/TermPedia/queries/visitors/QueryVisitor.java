package TermPedia.queries.visitors;

import TermPedia.dto.ActionsException;
import TermPedia.queries.books.*;
import TermPedia.queries.instances.authors.*;
import TermPedia.queries.instances.tags.*;
import TermPedia.queries.instances.terms.*;
import TermPedia.queries.instances.types.*;

public interface QueryVisitor {
    void visitFindTermQuery(FindTermByNameQuery query) throws ActionsException;
    void visitFindTagByNameQuery(FindTagByNameQuery query) throws ActionsException;
    void visitFindTagByTermNameQuery(FindTagByTermNameQuery query) throws ActionsException;
    void visitFindAuthorByNameQuery(FindAuthorByNameQuery query) throws ActionsException;
    void visitFindLitTypesByNameQuery(FindLitTypesByNameQuery query) throws ActionsException;

    void visitSearchBookByNameQuery(SearchBookByLikeNameQuery query) throws ActionsException;
    void visitSearchBookByTagQuery(SearchBookByTagQuery query) throws ActionsException;
    void visitSearchBookByAuthorQuery(SearchBookByAuthorQuery query) throws ActionsException;
    void visitSearchBookByTermQuery(SearchBookByLikeTermQuery query) throws ActionsException;
    void visitSearchConnectedBooksQuery(SearchBookByTermQuery query) throws ActionsException;
}
