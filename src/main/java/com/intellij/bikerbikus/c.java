package com.intellij.bikerbikus;

public class C extends B {
    C() {
        super();
        throw new IllegalStateException("Utility class");
    }
    public static void c(){
        System.out.println("da c chiamo b");
        b();
    }
}
