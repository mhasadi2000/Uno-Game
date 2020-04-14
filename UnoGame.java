package com.company;

import java.awt.image.ColorConvertOp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class UnoGame {
    private ArrayList<CardSet> cardSets;
    private Repository repository;
    private Card middleCard;
    public UnoGame(){
        cardSets=new ArrayList<>();
        repository=new Repository();
        middleCard=pickRandom();
        GameSteps();
    }

    public Card pickRandom(){
        int rt= (int) (Math.random() * 7);
        int rc= (int) (Math.random() * 5);
        CColor color=CColor.getColor(rc);
        Type type=Type.getType(rt);
        Card card=repository.getCard(type,color);
        if( card!=null)////if exist in the list.
            return card;
        return pickRandom();
    }

    public void createCardSet(){
        CardSet cardSet=new CardSet();
        for (int j=0;j<7;j++){
            int rt= (int) (Math.random() * 7);
            int rc= (int) (Math.random() * 5);
            Type type=Type.getType(rt);
            CColor color=CColor.getColor(rc);
            if (type==Type.P4 || type==Type.CC || color==CColor.BLACK){
                createCardSet();
            }else{
                Card card=repository.getCard(Type.getType(rt),CColor.getColor(rc));
                cardSet.addCard(card);
                repository.removeCard(card);
                cardSets.add(cardSet);
            }
        }
    }

    public void startGame(){
        Scanner scn=new Scanner(System.in);
        int numberOfPlayer= scn.nextInt();
        int i=0;
        while(i<numberOfPlayer){
            createCardSet();
            i++;
        }
    }

    public void putCard(int playerNumber){
        Iterator<Card> ite=cardSets.get(playerNumber-1).getCards().iterator();
        Iterator<Card> it=cardSets.get(playerNumber-1).getCards().iterator();
        while (ite.hasNext()){
            Card temp=ite.next();
            if(sameColor(temp)){
                repository.addToRep(middleCard);
                middleCard=temp;
                cardSets.get(playerNumber-1).removeCard(temp);
                return;
            }
        }

        while (it.hasNext()){
            Card temp=it.next();
            if(sameNumber(temp)){
                repository.addToRep(middleCard);
                middleCard=temp;
                cardSets.get(playerNumber-1).removeCard(temp);
                return;
            }
        }

        cardSets.get(playerNumber-1).addCard(pickRandom());

    }

    public boolean sameColor(Card card){
        if (middleCard.getColor().equals(card.getColor()))
            return true;
        return false;
    }

    public boolean sameNumber(Card card){
        if (middleCard.getType().equals(card.getType()))
            return true;
        if (card.getType().equals(Type.P4) || card.getType().equals(Type.CC)){
            return true;
        }
        return false;
    }

    public void GameSteps(){
        startGame();
        while (true){
            putCard(1);
        }


    }
}
