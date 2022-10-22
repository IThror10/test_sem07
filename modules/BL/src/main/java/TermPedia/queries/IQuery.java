package TermPedia.queries;

import TermPedia.dto.ActionsException;
import TermPedia.queries.visitors.QueryVisitor;

public interface IQuery {
    void acceptVisitor(QueryVisitor visitor) throws ActionsException;
    QueryResult getResult();
}
