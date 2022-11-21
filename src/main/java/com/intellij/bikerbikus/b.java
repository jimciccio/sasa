package com.intellij.bikerbikus;

public class B extends A {
    B() {
        super();
        throw new IllegalStateException("Utility class");
    }
    public static void b(){
        System.out.println("da b chiamo a");
        strigona();
    }
}
