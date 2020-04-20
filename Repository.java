package com.company;

import java.util.ArrayList;
import java.util.Iterator;
/**the Repository class creates and stores cards.
 * @author Mohammad Hossein Asadi
 * @version 18/4/2020**/
public class Repository {
    private ArrayList<Card> cards;

    /**construct cards arraylist and call the
     * createRepository method.**/
    public Repository(){
        cards=new ArrayList<>();
        createRepository();
    }

    /**create cards of repository.**/
    public void createRepository(){
        for (int i=0;i<2;i++) {
            for (CColor color : CColor.values()) {
                if (color == CColor.BLACK) {
                    continue;
                }
                for (Type type : Type.values()) {
                    if (type == Type.N0 || type == Type.P4 || type == Type.CC) {
                        continue;
                    }
                    cards.add(new Card(type, color));
                }
            }
        }
            for (int j=0;j<4;j++){
                cards.add(new Card(Type.P4, CColor.BLACK));
                cards.add(new Card(Type.CC, CColor.BLACK));
            }
            cards.add(new Card(Type.N0, CColor.BLUE));
            cards.add(new Card(Type.N0, CColor.RED));
            cards.add(new Card(Type.N0, CColor.GREEN));
            cards.add(new Card(Type.N0, CColor.YELLOW));

    }

    /**@return card with given type and color if exist.
     * @param type of the card that we want
     * @param color of the card that we want**/
    public Card getCard(Type type,CColor color){
        Iterator<Card> ite=cards.iterator();
        while (ite.hasNext()){
            Card temp=ite.next();
            if (temp.getColor().equals(color) && temp.getType().equals(type)){
                return temp;
            }
        }
        return null;
    }

    /**add acard to repository.
     * @param card to add to repository**/
    public void addToRep(Card card){
        cards.add(card);
    }

    /**remove a card from repository
     * @param card to remove from repository**/
    public void removeCard(Card card){
        cards.remove(card);
    }
}
