package com.intellij.bikerbikus.controllers1;

import com.intellij.bikerbikus.queries.LoginQuery;

public class LoginController1{

    private LoginController1(){}

    public static boolean onActionLogin(String a, String b) {
        boolean expression = false;
        if(LoginQuery.execute(a, b)) {
            expression = true;
        }
        else {
            expression = false;
        }
        return expression;
    }
}
