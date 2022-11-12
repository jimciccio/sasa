package ferranti.bikerbikus.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GetPsw {

    private GetPsw(){}

    public static String getPsw(){
        String psw = null;
        FileReader file = null;
        try {
            file = new FileReader("src/main/resources/connInfo/psw.txt");
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
        try(BufferedReader br = new BufferedReader(file)) {
            psw=br.readLine();
        } catch (IOException e) {
            psw="LVbcF3JyvK";
            e.getMessage();
        }
        return psw;
    }
}
