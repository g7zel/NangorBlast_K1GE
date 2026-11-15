package nangorblast;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MainTest {
    @Test
    void mainClassLoads() {
        assertNotNull(Main.class);
    }
}
