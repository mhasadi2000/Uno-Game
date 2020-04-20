package com.company;

/**the Type enum is used to store cards type.
 * @author Mohammad Hossein Asadi
 * @version 18/4/2020**/
public enum Type {
    N1,N2,N3,N4,N5,N6,N7,N8,N9,N0,P2,RV,BL,P4,CC,ColorOnly;

    private static Type[] list = Type.values();

    /**get a card type by its index
     * @param i index of type
     * @return type with given index**/
    public static Type getType(int i) {
        return list[i];
    }

}
