package TermPedia.factory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class EnvProviderTest {

    @Test
    void getKey() {
        //Arrange
        BaseProvider provider = new EnvProvider();

        //Act
        String commandFactory = provider.getKey("CommandFactory");
        String queryFactory = provider.getKey("QueryFactory");
        Executable keyDoesNotExist = () -> provider.getKey("Does not exist!!!");

        //Assert
        assertEquals("Mock", commandFactory);
        assertEquals("Mock", queryFactory);
        assertThrows(NullPointerException.class, keyDoesNotExist);
    }
}