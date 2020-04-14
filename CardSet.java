package com.company;

import java.util.ArrayList;

public class CardSet {
    private ArrayList<Card> cards;

    public CardSet(){
        cards=new ArrayList<>();
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public void removeCard(Card card){
        cards.remove(card);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
