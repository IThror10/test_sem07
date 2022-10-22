package TermPedia.factory.query.common;

import java.sql.Connection;

public interface IQueryConnection {
    Connection establishReaderConnection() throws Exception;
    Connection establishUpdaterConnection() throws Exception;
}
