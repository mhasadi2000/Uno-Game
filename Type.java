package com.company;

public enum Type {
    N1,N2,N3,N4,N5,N6,N7,N8,N9,N0,P2,P4,RV,BL,CC;

    private static Type[] list = Type.values();

    public static Type getType(int i) {
        return list[i];
    }

    public static int listGetLastIndex() {
        return list.length - 1;
    }
}
