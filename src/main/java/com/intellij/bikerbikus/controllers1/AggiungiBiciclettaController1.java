package com.intellij.bikerbikus.controllers1;

import com.intellij.bikerbikus.models.BiciclettaNoleggio;
import com.intellij.bikerbikus.models.BiciclettaVendita;
import com.intellij.bikerbikus.queries.AggiungiBiciclettaQuery;

public class AggiungiBiciclettaController1 {

    protected AggiungiBiciclettaController1(){}

    public static boolean onActionConfermaNuova(BiciclettaVendita bicicletta) {
        boolean expression = false;
                    if(AggiungiBiciclettaQuery.insertBiciclettaComprabile(bicicletta)) {
                        expression = true;
                    }else{
                        expression = false;
                    }
                    return expression;
    }

    public static boolean checkBiciclettaNuova(BiciclettaVendita bici){
        boolean expression;
        if(Boolean.TRUE.equals(AggiungiBiciclettaQuery.checkBiciNuova(bici))){
            expression = true;
        }else{
            expression = false;
        }
        return expression;
    }

    public static boolean onActionConfermaNoleggiabile(BiciclettaNoleggio bicicletta) {
            boolean expression = false;
            if (AggiungiBiciclettaQuery.insertBiciclettaNoleggiabile(bicicletta)) {
                expression = true;
            }else{
                expression = false;
            }
        return expression;
    }
}