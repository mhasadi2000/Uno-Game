package com.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class UnoGame {
    private ArrayList<CardSet> cardSets;
    private ArrayList<Card> recentCards;
    private Repository repository;
    private Card middleCard;
    private int numberOfPlayer;
    private int direction;
    private CColor currColor;
    private int bufferOfPlus;
    private int clear;

    public UnoGame(){
        cardSets=new ArrayList<>();
        recentCards=new ArrayList<>();
        repository=new Repository();
        this.direction=0;
        this.bufferOfPlus=0;
        this.clear=0;
        GameSteps();
    }

    public void GameSteps(){
        startGame();
        middleCard=pickRandom();
        int i=whoStart();
        System.out.println(i);
        boolean condition=true;

        while (condition){
            setCurrColor(middleCard.getColor());
            System.out.println();
            Scanner sc=new Scanner(System.in);
            if (i==0) {
                drawMiddleCard();

                int t=sc.nextInt();
            }

            System.out.println(i);
            drawYourSet(i);
            putCard(i);
            System.out.println();
            if (cardSets.get(i).getCards()==null) {
                System.out.println();
                condition=false;
            }
            if (getDirection()==0)
                i++;
            else
                i--;
            if (i==getNumberOfPlayer())
                i=0;
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
        this.numberOfPlayer=numberOfPlayer;
    }

    public void createCardSet(){
        CardSet cardSet=new CardSet();
        for (int j=0;j<7;j++){
            Random rand=new Random();
            int rt;
            int rc;
            Card card=null;
            while (card==null){
                rt= rand.nextInt(15);
                if (rt==13 || rt==14)
                    rc=4;
                else
                    rc= rand.nextInt(4);
                card=repository.getCard(Type.getType(rt),CColor.getColor(rc));
            }
                cardSet.addCard(card);
                draw(card);
                repository.removeCard(card);

        }
        cardSets.add(cardSet);
        System.out.println();
    }

    public Card pickRandom(){
        Random rand=new Random();
        int rt;
        int rc;
        rt= rand.nextInt(15);
        if (rt==13 || rt==14)
            rc=4;
        else
            rc= rand.nextInt(4);
        CColor color=CColor.getColor(rc);
        Type type=Type.getType(rt);
        Card card = repository.getCard(type, color);
        if( card!=null)////if exist in rep
            return card;
        return pickRandom();
    }

    public void pickCard(int number,int index){
        for (int i=0;i<number;i++){
            Card card=pickRandom();
            cardSets.get(index).addCard(card);
            repository.removeCard(card);
        }
    }

    public void putCard(int playerNumber){
        Iterator<Card> ite=cardSets.get(playerNumber).getCards().iterator();
        Iterator<Card> it=cardSets.get(playerNumber).getCards().iterator();
        Card buf=null;

        if (middleCard.getColor()==CColor.BLACK){
            if(middleCard.getType()==Type.P4){
                while (it.hasNext()){
                    Card temp=it.next();
                    if(temp.getType()==Type.P2){
                        repository.addToRep(middleCard);
                        middleCard=temp;
                        setCurrColor(temp.getColor());
                        cardSets.get(playerNumber).removeCard(temp);
                        increaseBufferOfPlus(2);
                        return;
                    }else if (sameNumber(temp)){
                        repository.addToRep(middleCard);
                        middleCard=temp;
                        setCurrColor(temp.getColor());
                        cardSets.get(playerNumber).removeCard(temp);
                        increaseBufferOfPlus(4);
                        return;
                    }
                }
                pickCard(4 + getBufferOfPlus(),playerNumber);
                resetBufferOfPlus();
                setCurrColor(CColor.RED);//////////////////////////////
                return;
            }else if (middleCard.getType()==Type.CC){
                while (ite.hasNext()){
                    Card temp=ite.next();
                    if(sameColor(temp)){
                        repository.addToRep(middleCard);
                        middleCard=temp;
                        setCurrColor(temp.getColor());
                        cardSets.get(playerNumber).removeCard(temp);
                        return;
                    }
                }
                pickCard(1,playerNumber);
                return;
            }
        }

        if (middleCard.getType()==Type.P2){
            while (it.hasNext()){
                Card temp=it.next();
                if (temp.getType()==Type.P4)
                    buf=temp;
                if(sameNumber(temp)){
                    repository.addToRep(middleCard);
                    middleCard=temp;
                    setCurrColor(temp.getColor());
                    cardSets.get(playerNumber).removeCard(temp);
                    return;
                }
            }
            if (buf!=null){
                repository.addToRep(middleCard);
                middleCard=buf;
                setCurrColor(buf.getColor());
                cardSets.get(playerNumber).removeCard(buf);
                return;
            }
            pickCard(2,playerNumber);
            return;
        }

        while (ite.hasNext()){
            Card temp=ite.next();
            if(sameColor(temp)){
                repository.addToRep(middleCard);
                middleCard=temp;
                setCurrColor(temp.getColor());
                cardSets.get(playerNumber).removeCard(temp);
                return;
            }
        }

        while (it.hasNext()){
            Card temp=it.next();
            if (temp.getType()==Type.CC)
                buf=temp;
            if(sameNumber(temp)){
                repository.addToRep(middleCard);
                middleCard=temp;
                setCurrColor(temp.getColor());
                cardSets.get(playerNumber).removeCard(temp);
                return;
            }
        }

        if (buf!=null){
            changeColor(playerNumber);
        }
        pickCard(1,playerNumber);

    }

    public void drawBoard(){
        Iterator<CardSet> ite=cardSets.iterator();
        ite.next();
        int i=1;
        while (i<cardSets.size()){
            System.out.print("player "+i+"  "+cardSets.get(i).getCards().size());
            i++;
        }
        System.out.println();
        i=1;
        while (i<cardSets.size()){
            System.out.println(recentCards.get(i).getType());
            i++;
        }


    }

    public void drawMiddleCard(){
        if (middleCard.getColor()==CColor.RED)
            System.out.print(ColorConsole.RED);
        if (middleCard.getColor()==CColor.YELLOW)
            System.out.print(ColorConsole.YELLOW);
        if (middleCard.getColor()==CColor.GREEN)
            System.out.print(ColorConsole.GREEN);
        if (middleCard.getColor()==CColor.BLUE)
            System.out.print(ColorConsole.BLUE);
        if (middleCard.getColor()==CColor.BLACK)
            System.out.print(ColorConsole.BLACK);
        System.out.println("$$$$$$$$$$$$$$$$");
        System.out.println("$$            $$");
        System.out.println("$$     "+middleCard.getType()+"     $$");
        System.out.println("$$            $$");
        System.out.println("$$$$$$$$$$$$$$$$");
    }

    public void drawYourSet(int i){
        Iterator<Card> ite=cardSets.get(i).getCards().iterator();
        while(ite.hasNext()){
            Card temp=ite.next();
            draw(temp);
            System.out.print("  ");
        }
        System.out.println();
    }

    public void draw(Card card){

        if (card==null){
            System.out.println("zeki");
            return;
        }
        if (card.getColor().equals(CColor.RED))
            System.out.print(ColorConsole.RED);
        if (card.getColor()==CColor.YELLOW){
            System.out.print(ColorConsole.YELLOW);}
        if (card.getColor()==CColor.GREEN){
            System.out.print(ColorConsole.GREEN);}
        if (card.getColor()==CColor.BLUE){
            System.out.print(ColorConsole.BLUE);}
        if (card.getColor()==CColor.BLACK){
            System.out.print(ColorConsole.BLACK);}
        System.out.print(card.getType());
        System.out.print(ColorConsole.RESET);
    }
    public int whoStart(){
        return  (int) (Math.random() * getNumberOfPlayer());
    }

    public void changeDirection(){
        if (getDirection()==0)
            setDirection(1);
        else
            setDirection(0);
    }

    public void changeColor(int playerNumber){
        if (playerNumber==0){
            Scanner scn=new Scanner(System.in);
            String color=scn.nextLine();
            color=color.toUpperCase();
            System.out.println("select a color (R/B/G/Y)");
            switch (color){
                case "R":
                    setCurrColor(CColor.RED);
                    break;
                case "B":
                    setCurrColor(CColor.BLUE);
                    break;
                case "G":
                    setCurrColor(CColor.GREEN);
                    break;
                case "Y":
                    setCurrColor(CColor.YELLOW);
                    break;
                default:
                    System.out.println("the color that you entered is not possible");
                    changeColor(playerNumber);
            }
        }else {
            Random rand=new Random();
            int r=rand.nextInt(4);
            setCurrColor(CColor.getColor(r));
        }

    }

    public boolean sameColor(Card card){
        if (getCurrColor().equals(card.getColor()))//////////currColor
            return true;
        return false;
    }

    public boolean sameNumber(Card card){
        if (middleCard.getType().equals(card.getType()))
            return true;
        return false;
    }



    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public void setDirection(int i){
        this.direction=i;
    }

    public int getDirection() {
        return direction;
    }

    public CColor getCurrColor() {
        return currColor;
    }

    public void setCurrColor(CColor currColor) {
        this.currColor = currColor;
    }

    public int getBufferOfPlus() {
        return bufferOfPlus;
    }

    public void resetBufferOfPlus(){
        this.bufferOfPlus=0;
    }

    public void increaseBufferOfPlus(int increment) {
        this.bufferOfPlus += increment;
    }

    public ArrayList<Card> getRecentCards() {
        return recentCards;
    }

    public void setRecentCards(ArrayList<Card> recentCards) {
        this.recentCards = recentCards;
    }
}
