package com.company;

import java.util.ArrayList;

public class CardSet {
    private ArrayList<Card> cards;
    private int sum;

    public CardSet(){
        cards=new ArrayList<>();
        this.sum=0;
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

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
