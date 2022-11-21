package com.intellij.bikerbikus;

public class G extends F {
    private G() {
        super();
        throw new IllegalStateException("Utility class");
    }
    public static void g(){
        System.out.println("da g chiamo f");
        f();
    }
}
