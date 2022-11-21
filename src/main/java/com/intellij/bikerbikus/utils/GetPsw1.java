package com.intellij.bikerbikus.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GetPsw1 {

    private GetPsw1(){}

    public static String getPsw1(){
        String psw = null;
        FileReader file = null;
        try {
            file = new FileReader("src/main/resources/connInfo/psw1.txt");
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
        try(BufferedReader br = new BufferedReader(file)) {
            psw=br.readLine();
        } catch (IOException e) {
            psw="Wc5mKjdrRu";
            e.getMessage();
        }
        return psw;
    }
}
