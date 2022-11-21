package com.intellij.bikerbikus.queries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginQueryTest {

    @Test
    void execute() {
        String name = "utente@gmail.com";
        String ps = "1234";
        boolean value = LoginQuery.execute(name,ps);
        assertEquals(true, value);
    }
}