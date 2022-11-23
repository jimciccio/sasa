package ferranti.bikerbikus.controllers1;


import ferranti.bikerbikus.queries.RegisterQuery;

public class RegisterController1 {

    protected RegisterController1(){}

    public static boolean onActionRegister(String nome,String cognome, String email, String password) {
        if(nome.isBlank() || cognome.isBlank() || email.isBlank() || password.isBlank()) {
            return false;
        }
        else {
            int count=0;
            count = RegisterQuery.countUsers();
            RegisterQuery.execute(nome,cognome,email,password, count);
            return true;
        }
    }
}
