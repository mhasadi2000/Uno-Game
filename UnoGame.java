package com.company;

import java.awt.image.ColorConvertOp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class UnoGame {
    private ArrayList<CardSet> cardSets;
    private Card[] recentCards;
    private Repository repository;
    private Card middleCard;
    private int numberOfPlayer;
    private int direction;
    private boolean isBlock;
    private CColor currColor;
    private Type currType;
    private int bufferOfPlus;
    private int startIndex;

    public UnoGame(){
        cardSets=new ArrayList<>();
        recentCards=new Card[5];
        repository=new Repository();
        this.isBlock=true;
        this.direction=0;
        this.bufferOfPlus=0;
        GameSteps();
    }

    public void GameSteps(){
        startGame();
        middleCard=startMiddle();
        int i=whoStart();
        setStartIndex(i);
        System.out.println(i);
        boolean condition=true;

        while (condition){
            setCurrColor(middleCard.getColor());
            System.out.println();
            Scanner sc=new Scanner(System.in);

            if (middleCard.getType()==Type.BL){
                if (getDirection()==0) {
                    i++;
                    this.isBlock=false;
                    if (i==getNumberOfPlayer()){
                        i=0;
                        clear();
                    }
                }
                if (getDirection()==1) {
                    i--;
                    this.isBlock=false;
                    if (i==-1){
                        i=getNumberOfPlayer()-1;
                        clear();
                    }
                }
            }

            /*if (isBlock && getDirection()==0) {
                i++;
                this.isBlock=false;
                if (i==getNumberOfPlayer()){
                    i=0;
                    clear();
                }
            }

            if (isBlock && getDirection()==1) {
                i--;
                this.isBlock=false;
                if (i==-1){
                    i=getNumberOfPlayer()-1;
                    clear();
                }
            }
*/
            drawBoard(i);
            System.out.println("player "+i+" turn");
            drawYourSet(i);
            putCard(i);
            if (i==0) {
                int t=sc.nextInt();
            }
            System.out.println();
            if (cardSets.get(i).getCards().size()==0) {
                System.out.println();
                condition=false;
            }
            if (getDirection()==0) {
                i++;
            }else{
                i--;
            }

            if (i==getNumberOfPlayer()){
                i=0;
                clear();
            }
            if (i==-1){
                i=getNumberOfPlayer()-1;
                clear();
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

    public Card startMiddle(){
        Random rand=new Random();
        int rt;
        int rc;
        rt= rand.nextInt(13);
        rc= rand.nextInt(4);
        CColor color=CColor.getColor(rc);
        Type type=Type.getType(rt);
        Card card = repository.getCard(type, color);
        if( card!=null){ ////if exist in rep
           setCurrType(type);
           setCurrColor(color);
            return card;
        }
        return pickRandom();
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
        Iterator<Card> iter=cardSets.get(playerNumber).getCards().iterator();
        Card buf=null;
        Card buff=null;

        if (getCurrType()==Type.P4){////////////////////change to currcolor
            //if(getCurrType()==Type.P4){/////////////////////change to currtype
                while (it.hasNext()){
                    Card temp=it.next();
                    if(temp.getType()==Type.P2){
                        repository.addToRep(middleCard);
                        middleCard=temp;
                        setCurrColor(temp.getColor());
                        setCurrType(temp.getType());
                        cardSets.get(playerNumber).removeCard(temp);
                        setRecentCards(playerNumber,temp);
                        increaseBufferOfPlus(2);
                        return;
                    }else if (sameNumber(temp)){
                        repository.addToRep(middleCard);
                        middleCard=temp;
                        changeColor(playerNumber);
                        setCurrType(temp.getType());
                        cardSets.get(playerNumber).removeCard(temp);
                        setRecentCards(playerNumber,temp);
                        increaseBufferOfPlus(4);
                        return;
                    }
                }
                pickCard(4 + getBufferOfPlus(),playerNumber);
                resetBufferOfPlus();
                //changeColor(playerNumber);
                setCurrType(Type.ColorOnly);
                return;
            //}
        }

        if (getCurrType()==Type.RV){
            changeDirection();
        }

        if (getCurrType()==Type.BL){
            this.isBlock=true;
        }

        if (middleCard.getType()==Type.P2 && getCurrType()!=Type.ColorOnly){
            while (it.hasNext()){
                Card temp=it.next();
                if (temp.getType()==Type.P4)
                    buf=temp;
                if(sameNumber(temp)){
                    repository.addToRep(middleCard);
                    middleCard=temp;
                    setCurrColor(temp.getColor());
                    setCurrType(temp.getType());
                    cardSets.get(playerNumber).removeCard(temp);
                    setRecentCards(playerNumber,temp);
                    increaseBufferOfPlus(2);
                    return;
                }
            }
            if (buf!=null){
                repository.addToRep(middleCard);
                middleCard=buf;
                changeColor(playerNumber);
                setCurrType(buf.getType());
                cardSets.get(playerNumber).removeCard(buf);
                setRecentCards(playerNumber,buf);
                increaseBufferOfPlus(4);
                return;
            }
            pickCard(2+getBufferOfPlus(),playerNumber);
            setCurrType(Type.ColorOnly);
            return;
        }

        if (getCurrType()==Type.ColorOnly){
            while (iter.hasNext()){
                Card temp=iter.next();
                if (playerNumber!=0)
                    changeColor(playerNumber);////////////
                System.out.println(getCurrColor().toString()+"/////////////////////////");
                if(temp.getColor()==getCurrColor() && getCurrColor()!=CColor.BLACK){
                    System.out.println("same color////////////////////////////");
                    repository.addToRep(middleCard);
                    middleCard=temp;
                    setCurrColor(temp.getColor());
                    setCurrType(temp.getType());
                    cardSets.get(playerNumber).removeCard(temp);
                    setRecentCards(playerNumber,temp);
                    return;
                }
            }
        }


        while (ite.hasNext()){
            Card temp=ite.next();
            if (temp.getColor()!= CColor.BLACK){
                if(temp.getColor()==getCurrColor()){
                    System.out.println("same color////////////////////////////");
                    repository.addToRep(middleCard);
                    middleCard=temp;
                    setCurrColor(temp.getColor());
                    setCurrType(temp.getType());
                    cardSets.get(playerNumber).removeCard(temp);
                    setRecentCards(playerNumber,temp);
                    return;
                }
            }
        }

        while (it.hasNext()){
            Card temp=it.next();
            if (temp.getType()==Type.P4)
                buff=temp;
            if (temp.getType()==Type.CC)
                buf=temp;
            if(sameNumber(temp) && temp.getType()!=Type.ColorOnly){
                repository.addToRep(middleCard);
                middleCard=temp;
                setCurrColor(temp.getColor());
                setCurrType(temp.getType());
                cardSets.get(playerNumber).removeCard(temp);
                setRecentCards(playerNumber,temp);
                return;
            }
        }

        if (buf!=null){///////////////CC
            repository.addToRep(middleCard);
            middleCard=buf;
            //setCurrColor(CColor.RED);////////////////////
            changeColor(playerNumber);
            System.out.println(getCurrColor().toString()+"/////////////////");
            setCurrType(Type.ColorOnly);
            cardSets.get(playerNumber).removeCard(buf);
            setRecentCards(playerNumber,buf);
            System.out.println("CC bi pedar////////////");
            return;
        }

        if (buff!=null){//////////////P4
            repository.addToRep(middleCard);
            middleCard=buff;
            changeColor(playerNumber);
            setCurrType(buff.getType());
            cardSets.get(playerNumber).removeCard(buff);
            setRecentCards(playerNumber,buff);
            increaseBufferOfPlus(4);
            return;

        }
        System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
        pickCard(1,playerNumber);
    }

    public void drawBoard(int playerNumber){
        Iterator<CardSet> ite=cardSets.iterator();
        ite.next();
        int i=1;
        while (i<cardSets.size()){
            System.out.print("player"+i+" "+cardSets.get(i).getCards().size()+"  |");
            i++;
        }
        System.out.println();
        int j=playerNumber;
        int counter=0;
        while (j<cardSets.size() && j>0){
            int k=0;
            while (k<j){
                System.out.print("    ");
                k++;
            }
            if (recentCards[j]!=null){
                System.out.print("    ");
                draw(recentCards[j]);
                System.out.print("    ");
            }
            counter++;
            if (getDirection()==0)
                j++;
            else
                j--;
        }
        if (counter!=i-1)
            System.out.print(" TURN");
        System.out.println();
        if (getDirection()==0)
            System.out.println("clockwise -->");
        else
            System.out.println("anti clockwise <--");

        drawMiddleCard();
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
        Type temp=card.getType();
        System.out.print(temp);
        System.out.print(ColorConsole.RESET);
    }

    public void clear(){
        int i=1;
        while(i<recentCards.length && recentCards[i]!=null){
            recentCards[i]=null;
            i++;
        }
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
            System.out.println("select a color (R/B/G/Y)");
            String color=scn.nextLine();
            color=color.toUpperCase();
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
            switch (r){
                case 0:
                    setCurrColor(CColor.RED);
                    break;
                case 1:
                    setCurrColor(CColor.BLUE);
                    break;
                case 2:
                    setCurrColor(CColor.GREEN);
                    break;
                case 3:
                    setCurrColor(CColor.YELLOW);
                    break;
                default:
                    System.out.println("the color that you entered is not possible");
                    changeColor(playerNumber);
            }
            //setCurrColor(CColor.getColor(r));
            System.out.println(getCurrColor().toString());/////////////////////
        }
    }

    public void freeType(){

    }

    public boolean sameColor(Card card){
        if (getCurrColor().equals(card.getColor()))//////////currColor
            return true;
        return false;
    }

    public boolean sameNumber(Card card){
        if (card!=null)
        if (getCurrType().equals(card.getType()))
            return true;
        return false;
    }


    public void changeTypeOfMiddleCard(Type type){
        middleCard.setType(type);
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


    public void setRecentCards(int index, Card card) {
        this.recentCards[index]=card;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public Type getCurrType() {
        return currType;
    }

    public void setCurrType(Type currType) {
        this.currType = currType;
    }
}
