package com.company;

public enum CColor {
    RED,BLUE,GREEN,YELLOW,BLACK;

    private static CColor[] list = CColor.values();

    public static CColor getColor(int i) {
        return list[i];
    }

    public static int listGetLastIndex() {
        return list.length - 1;
    }
}
