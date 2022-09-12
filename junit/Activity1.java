package activities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class Activity1 {
    ArrayList<String> list;
    @BeforeEach
    public void setUp() {
        // Initialize a new ArrayList
        list = new ArrayList<String>();

        // Add values to the list
        list.add("alpha"); // at index 0
        list.add("beta"); // at index 1
    }

    @Test
    public void insertTest() {
        assertEquals(2, list.size());
        list.add("gamma");
        assertEquals(3, list.size());
        assertAll(
            () -> assertEquals("alpha", list.get(0)),
            () -> assertEquals("beta", list.get(1)),
            () -> assertEquals("gamma", list.get(2))
        );
    }

    @Test
    public void replaceTest() {
        assertEquals(2, list.size());
        list.add("gamma");
        assertEquals(3, list.size());
        list.set(1,"delta"); //replacing an item in the list
        assertAll(
                () -> assertEquals("alpha", list.get(0)),
                () -> assertEquals("delta", list.get(1)),
                () -> assertEquals("gamma", list.get(2))
        );
    }
}