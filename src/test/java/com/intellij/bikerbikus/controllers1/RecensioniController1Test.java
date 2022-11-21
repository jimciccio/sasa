package com.intellij.bikerbikus.controllers1;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecensioniController1Test {

    @Test
    void loadLuoghi() {

        List observableList = RecensioniController1.loadLuoghi();

        assertTrue(observableList.size()>1);

    }
}