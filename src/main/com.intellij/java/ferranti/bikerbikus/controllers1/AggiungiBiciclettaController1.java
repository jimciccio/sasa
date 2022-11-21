package ferranti.bikerbikus.controllers1;

import ferranti.bikerbikus.models.BiciclettaNoleggio;
import ferranti.bikerbikus.models.BiciclettaVendita;
import ferranti.bikerbikus.queries.AggiungiBiciclettaQuery;

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