package com.company;

import java.util.ArrayList;
import java.util.Iterator;

public class Repository {
    private ArrayList<Card> cards;

    public Repository(){
        cards=new ArrayList<>();
        createRepository();
    }

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

    public void addToRep(Card card){
        cards.add(card);
    }

    public void removeCard(Card card){
        cards.remove(card);
    }
}
