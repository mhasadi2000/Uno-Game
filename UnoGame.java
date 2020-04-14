package com.company;

import java.util.ArrayList;

public class UnoGame {
    private ArrayList<CardSet> cardSets;
    private Repository repository;
    public UnoGame(){
        cardSets=new ArrayList<>();
        repository=new Repository();
    }

    public void createCardSet(){
        CardSet cardSet=new CardSet();
        cardSets.add(cardSet);
        for (int j=0;j<7;j++){
            int rt= (int) (Math.random() * 8);
            int rc= (int) (Math.random() * 8);
            Card card=repository.getCard(Type.getType(rt),CColor.getColor(rc));
            cardSet.addCard(card);
            repository.removeCard(card);
        }
    }
}
