package TermPedia.events.visitors;

import TermPedia.dto.ActionsException;
import TermPedia.events.data.AddTermEvent;
import TermPedia.events.data.DataEvent;
import TermPedia.events.user.AuthorizeEvent;
import TermPedia.events.user.RegisterEvent;

public interface EventVisitor {
    void visitDataEvent(DataEvent event) throws ActionsException;
    void visitRegisterEvent(RegisterEvent event) throws ActionsException;
    void visitAuthorizeEvent(AuthorizeEvent event) throws ActionsException;
    void visitAddTermEvent(AddTermEvent event) throws ActionsException;
}
