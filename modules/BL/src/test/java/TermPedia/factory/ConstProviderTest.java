package TermPedia.factory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
class ConstProviderTest {

    @Test
    void getKey() {
        //Arrange
        ConstProvider provider = new ConstProvider("Test");

        //Act
        String res1 = provider.getKey("Key");
        String res2 = provider.getKey("OtherKey");

        //Assert
        assertEquals("Test", res1);
        assertEquals("Test", res2);
    }
}