package TermPedia;

import TermPedia.dto.ActionsException;
import TermPedia.factory.EnvProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnvProviderTest {

    @Test
    void getKey() {
        EnvProvider provider = new EnvProvider();
        assertAll(
                () -> assertEquals("Mock", provider.getKey("CommandFactory")),
                () -> assertEquals("Mock", provider.getKey("QueryFactory")),
                () -> assertThrows(NullPointerException.class, () -> provider.getKey("Does not exist!!123"))
        );
    }
}