package com.company;

/**the Card class save info of a card like type and color.
 * @author Mohammad Hossein Asadi
 * @version 18/4/2020**/
public class Card {
    private Type type;
    private CColor color;

    /**construct type and color of the card.
     * @param type of card
     * @param color of card**/
    public Card(Type type,CColor color){
        this.type=type;
        this.color=color;
    }

    /**get type of the card
     * @return type**/
    public Type getType() {
        return type;
    }

    /**get color of the card
     * @return color**/
    public CColor getColor() {
        return color;
    }

}
