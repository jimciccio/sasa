package com.intellij.bikerbikus;

public class F extends E {
    F() {
        super();
        throw new IllegalStateException("Utility class");
    }
    public static void f(){
        System.out.println("da f chiamo e");
        e();
    }
}
