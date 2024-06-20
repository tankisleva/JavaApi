package badtests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringLengthTest {

    @Test
    public void testStringLength() {
        String text = "Some text longer than 15 characters";
        assertTrue(text.length()>15,"Length is not greater than 15");
    }
}
