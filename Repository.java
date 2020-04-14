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
        for (int i=0;i<2;i++){
            for (CColor color : CColor.values()) {
                for (Type type : Type.values()){
                    if(type==Type.N0){
                        continue;
                    }
                    cards.add(new Card(type, color));
                }
            }

            /*
            cards.add(new Card(Type.N0,CColor.BLUE));
            cards.add(new Card(Type.N1,CColor.BLUE));
            cards.add(new Card(Type.N2,CColor.BLUE));
            cards.add(new Card(Type.N3,CColor.BLUE));
            cards.add(new Card(Type.N4,CColor.BLUE));
            cards.add(new Card(Type.N5,CColor.BLUE));
            cards.add(new Card(Type.N6,CColor.BLUE));
            cards.add(new Card(Type.N7,CColor.BLUE));
            cards.add(new Card(Type.N8,CColor.BLUE));
            cards.add(new Card(Type.N9,CColor.BLUE));
            cards.add(new Card(Type.BL,CColor.BLUE));
            cards.add(new Card(Type.CC,CColor.BLUE));
            cards.add(new Card(Type.RV,CColor.BLUE));
            cards.add(new Card(Type.P2,CColor.BLUE));
            cards.add(new Card(Type.P4,CColor.BLUE));*/
        }
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

    public void removeCard(Card card){
        cards.remove(card);
    }
}
