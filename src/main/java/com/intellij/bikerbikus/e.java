package com.intellij.bikerbikus;

public class E extends D {
    E() {
        super();
        throw new IllegalStateException("Utility class");
    }
    public static void e(){
        System.out.println("da e chiamo d");
        d();
    }
}
