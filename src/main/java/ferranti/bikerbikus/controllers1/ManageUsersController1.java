package ferranti.bikerbikus.controllers1;

import ferranti.bikerbikus.models.Utente;
import ferranti.bikerbikus.queries.ManageUsersQuery;

import java.util.ArrayList;
import java.util.List;

public class ManageUsersController1 {

    protected ManageUsersController1(){}

    protected static final List<Utente> utenti = new ArrayList<>();

    public static List<Utente> loadUtenti() {
        utenti.clear();
        utenti.addAll(ManageUsersQuery.findAllUsers());
        return utenti;
    }

    public static void upgradeUser(int item){
                ManageUsersQuery.upgradeUser(item);
                loadUtenti();
    }
}
