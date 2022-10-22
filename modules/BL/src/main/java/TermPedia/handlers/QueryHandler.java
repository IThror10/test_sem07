package TermPedia.handlers;

import TermPedia.dto.ActionsException;
import TermPedia.queries.IQuery;
import TermPedia.queries.visitors.QueryHandlerVisitor;

public class QueryHandler {
    public void handle(IQuery query) throws ActionsException {
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();
        query.acceptVisitor(visitor);
    }
}
