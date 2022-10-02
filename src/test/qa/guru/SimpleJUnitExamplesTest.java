package guru.qa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleJUnitExamplesTest {

    @Disabled("JRASERVER-21680")
    @Test
    void simpleTest() {
        Assertions.assertEquals("Str", "Str");

    }
    @DisplayName("Profile")
    @Test
    void simpleTest1() {

    }
    @Test
    void simpleTest2() {

    }
}
