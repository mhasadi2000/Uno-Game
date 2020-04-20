package com.company;

/**the CColor enum is used to store colors of cards.
 * @author Mohammad Hossein Asadi
 * @version 18/4/2020**/
public enum CColor {
    RED,BLUE,GREEN,YELLOW,BLACK;

    private static CColor[] list = CColor.values();

    /**get  color from enum by color index.
     * @param i index of color
     * @return color with the given index**/
    public static CColor getColor(int i) {
        return list[i];
    }
}
