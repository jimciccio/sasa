package com.intellij.bikerbikus.controllers1;

import com.intellij.bikerbikus.queries.ManageUsersQuery;
import com.intellij.bikerbikus.models.Utente;

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
