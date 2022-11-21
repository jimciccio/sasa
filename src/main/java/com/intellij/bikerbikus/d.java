package com.intellij.bikerbikus;

public class D extends C {
    D() {
        super();
        throw new IllegalStateException("Utility class");
    }
    public static void d(){
        System.out.println("da d chiamo c");
        c();
    }
}
