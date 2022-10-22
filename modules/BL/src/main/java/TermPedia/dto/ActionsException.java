package TermPedia.dto;

public class ActionsException extends Exception {
    public ActionsException() { super(); }
    public ActionsException(String message) { super(message); }
    public ActionsException(String message, Throwable cause) { super(message, cause); }
    public ActionsException(Throwable cause) { super(cause); }
}
