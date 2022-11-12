package ferranti.bikerbikus.controllers1;

import ferranti.bikerbikus.models.GaraExtended;
import ferranti.bikerbikus.models.Stagione;
import ferranti.bikerbikus.models.UtenteExtended;
import ferranti.bikerbikus.queries.ClassificaQuery;
import ferranti.bikerbikus.queries.StagioneQuery;

import java.util.ArrayList;
import java.util.List;

public class StagioneController1 {

    protected StagioneController1(){}

    protected static final List<GaraExtended> gare = new ArrayList<>();
    protected static final List<UtenteExtended> utente = new ArrayList<>();
    protected static final List<UtenteExtended> utenteGara = new ArrayList<>();

    public static void loadStagione(Stagione stagione) {
        gare.clear();
        gare.addAll(StagioneQuery.execute(stagione.getId()));
    }

    public static void loadClassifica(Stagione stagione){
        utente.clear();
        utente.addAll(ClassificaQuery.execute(stagione.getId()));

        for(int i=0;i<utente.size(); i++){
            utente.get(i).setPunteggio(ClassificaQuery.userPunteggio(stagione.getId(), utente.get(i).getId(), utente.get(i)));
        }
        bubbleSort(utente);
    }

    public static boolean loadClassificaGara(int idGara){
        utenteGara.clear();
        if(utenteGara.addAll(ClassificaQuery.classificaGara(idGara))){
            for(int i = 0; i < utenteGara.size(); i++) {
                boolean flag = false;
                for(int j = 0; j < utenteGara.size()-1; j++) {
                    if(utenteGara.get(j).getPunteggio() < utenteGara.get(j+1).getPunteggio()) {
                        UtenteExtended ne;
                        ne=utenteGara.get(j);
                        utenteGara.set(j,utenteGara.get(j+1));
                        utenteGara.set(j+1,ne);
                        flag=true;
                    }
                }
                if(!flag) break;
            }
            return true;
        }else{
            return false;
        }
    }

    public static void bubbleSort(List<UtenteExtended> array) {
        for(int i = 0; i < array.size(); i++) {
            boolean flag = false;
            for(int j = 0; j < array.size()-1; j++) {
                if(array.get(j).getPunteggio() < array.get(j+1).getPunteggio()) {

                    UtenteExtended ne;
                    ne=array.get(j);
                    array.set(j,array.get(j+1));
                    array.set(j+1,ne);
                    flag=true;
                }
            }
            if(!flag) break;
        }
        setNumber(array);
    }

    public static void setNumber(List<UtenteExtended> user){
        for(int j = 0; j < user.size(); j++){
            user.get(j).setPosizioneFinale(j+1);
        }
    }
}
