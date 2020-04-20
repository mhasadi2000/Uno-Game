package com.company;


import java.util.*;

/**the UnoGame class is used to moderate the game steps from start to end.
 * @author Mohammad Hossein Asadi
 * @version 18/4/2020**/
public class UnoGame {
    private ArrayList<CardSet> cardSets;
    private Card[] recentCards;
    private Repository repository;
    private Card middleCard;
    private Card previousCard;
    private int numberOfPlayer;
    private int direction;
    private boolean isBlock;
    private CColor currColor;
    private Type currType;
    private int bufferOfPlus;
    private int startIndex;
    private int changedColor;

    /**construct arraylists and objects of field and call the GameSteps method.**/
    public UnoGame(){
        cardSets=new ArrayList<>();
        recentCards=new Card[5];
        repository=new Repository();
        this.isBlock=true;
        this.direction=0;
        this.bufferOfPlus=0;
        GameSteps();
    }

    /**represent steps of a game from start to end and call required methods.**/
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
            //clear console
            for(int clear = 0; clear < 100; clear++)
            {
                System.out.println("\b") ;
            }

            //check if previous card is block or not
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

            drawBoard();
            if (i==0) {
                System.out.println("it is your turn");
                System.out.println("enter the card number from 1");
                drawYourSet(0);
                putCardManually();
            }else{
                System.out.println("player "+i+" turn");
                drawYourSet(0);
                putCard(i);
            }
            System.out.println();
            if (cardSets.get(i).getCards().size()==0) {
                System.out.println();
                System.out.print(ColorConsole.MAGENTA);
                if (i==0)
                    System.out.println("you win the game");
                else
                    System.out.println("player"+i+" win the game");
                System.out.println();
                checkTotal();
                condition=false;
            }
            if (getDirection()==0) {
                i++;
            }else{
                i--;
            }

            if (i==getNumberOfPlayer()){
                i=0;
                //clear resent cards
                clear();
            }
            if (i==-1){
                i=getNumberOfPlayer()-1;
                clear();
            }
            //delay
            int delay=2000;
            long start = System.currentTimeMillis();
            while(start >= System.currentTimeMillis() - delay);

        }
    }

    /**start the game by scanning number of player and creating cardSets.**/
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

    /**calculate total score of each player and draw the sorted list.**/
    public void checkTotal(){
        ArrayList<Integer> finall=new ArrayList<>();
        int i=0;

        while(i<cardSets.size()){
            int sum=0;
            Iterator<Card> cardIterator=cardSets.get(i).getCards().iterator();
            while (cardIterator.hasNext()){
                switch (cardIterator.next().getType()){
                    case P2:
                        sum+=20;
                        break;
                    case BL:
                        sum+=20;
                        break;
                    case P4:
                        sum+=50;
                        break;
                    case RV:
                        sum+=20;
                        break;
                    case CC:
                        sum+=50;
                        break;
                    case N0:
                        break;
                    case N1:
                        sum+=1;
                        break;
                    case N2:
                        sum+=2;
                        break;
                    case N3:
                        sum+=3;
                        break;
                    case N4:
                        sum+=4;
                        break;
                    case N5:
                        sum+=5;
                        break;
                    case N6:
                        sum+=6;
                        break;
                    case N7:
                        sum+=7;
                        break;
                    case N8:
                        sum+=8;
                        break;
                    case N9:
                        sum+=9;
                        break;
                    default:
                        sum+=0;
                }
            }
            finall.add(sum);
            cardSets.get(i).setSum(sum);
            i++;
        }
        Collections.sort(finall);
        int j=0;
        while (j<finall.size()){
            int k=0;
            while (k<cardSets.size()){
                if (finall.get(j)==cardSets.get(k).getSum()){
                    System.out.print(ColorConsole.CYAN);
                    if (k==0)
                        System.out.println("YOUR"+" score: "+cardSets.get(k).getSum());
                    else
                        System.out.println("player "+k+" score: "+cardSets.get(k).getSum());
                    break;
                }
                k++;
            }
            j++;
        }
        System.out.print(ColorConsole.RESET);
    }

    /**create cardSets by random.**/
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

    /**put the first middle card
     * @return the middle card**/
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
           if (type==Type.P2)
               increaseBufferOfPlus(2);
           if (type==Type.RV)
               changeDirection();
           if (type==Type.BL)
               this.isBlock=true;
            return card;
        }
        return pickRandom();
    }

    /**pick a card by random if exist in the repository.
     * @return card that picked**/
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

    /**give a number of random card to the player we want.
     * @param number of cards
     * @param index of player**/
    public void pickCard(int number,int index){
        for (int i=0;i<number;i++){
            Card card=pickRandom();
            cardSets.get(index).addCard(card);
            repository.removeCard(card);
        }
    }

    /**steps of putting a card at the floor.
     * @param temp the card to put
     * @param playerNumber that want to put card**/
    public void putONBoard(Card temp,int playerNumber){
        repository.addToRep(middleCard);
        setPreviousCard(middleCard);
        middleCard=temp;
        setCurrColor(temp.getColor());
        setCurrType(temp.getType());
        cardSets.get(playerNumber).removeCard(temp);
        setRecentCards(playerNumber,temp);
    }

    /**put a card on the middle by other players and different situation.
     * @param playerNumber that want to put card**/
    public void putCard(int playerNumber){
        Iterator<Card> ite=cardSets.get(playerNumber).getCards().iterator();
        Iterator<Card> it=cardSets.get(playerNumber).getCards().iterator();
        Iterator<Card> iter=cardSets.get(playerNumber).getCards().iterator();
        Card buf=null;
        Card buff=null;

        if (getCurrType()==Type.P4){
                while (it.hasNext()){
                    Card temp=it.next();
                    if(temp.getType()==Type.P2){
                        putONBoard(temp,playerNumber);
                        increaseBufferOfPlus(2);
                        setChangedColor(temp.getColor());
                        return;
                    }else if (sameNumber(temp)){
                        repository.addToRep(middleCard);
                        setPreviousCard(middleCard);
                        middleCard=temp;
                        changeColor(playerNumber);
                        setCurrType(temp.getType());
                        cardSets.get(playerNumber).removeCard(temp);
                        setRecentCards(playerNumber,temp);
                        increaseBufferOfPlus(4);
                        return;
                    }
                }
                pickCard( getBufferOfPlus(),playerNumber);
                resetBufferOfPlus();
                setCurrType(Type.ColorOnly);
                setPreviousCard(null);
                return;
        }



        if (middleCard.getType()==Type.P2 && getCurrType()!=Type.ColorOnly){
            while (it.hasNext()){
                Card temp=it.next();
                if (temp.getType()==Type.P4)
                    buf=temp;
                if(sameNumber(temp)){
                    putONBoard(temp,playerNumber);
                    increaseBufferOfPlus(2);
                    setChangedColor(temp.getColor());
                    return;
                }
            }
            if (buf!=null){
                repository.addToRep(middleCard);
                setPreviousCard(middleCard);
                middleCard=buf;
                changeColor(playerNumber);
                setCurrType(buf.getType());
                cardSets.get(playerNumber).removeCard(buf);
                setRecentCards(playerNumber,buf);
                increaseBufferOfPlus(4);
                return;
            }
            pickCard(getBufferOfPlus(),playerNumber);
            resetBufferOfPlus();
            setCurrType(Type.ColorOnly);
            setPreviousCard(null);
            return;
        }

        if (getCurrType()==Type.ColorOnly){
            setCurrColor(CColor.getColor(changedColor));
            while (iter.hasNext()){
                Card temp=iter.next();
                if(temp.getColor()==getCurrColor() && getCurrColor()!=CColor.BLACK){
                    putONBoard(temp,playerNumber);

                    if (getCurrType()==Type.P2)
                        setChangedColor(temp.getColor());

                    if (getCurrType()==Type.RV){
                        changeDirection();
                    }

                    if (getCurrType()==Type.BL){
                        this.isBlock=true;
                    }

                    return;
                }
            }
        }


        while (ite.hasNext()){
            Card temp=ite.next();
            if (temp.getColor()!= CColor.BLACK){
                if(temp.getColor()==getCurrColor()){
                    putONBoard(temp,playerNumber);

                    if (temp.getType()==Type.P2)
                        setChangedColor(temp.getColor());

                    if (getCurrType()==Type.RV){
                        changeDirection();
                    }

                    if (getCurrType()==Type.BL){
                        this.isBlock=true;
                    }
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
                putONBoard(temp,playerNumber);

                if (temp.getType()==Type.P2)
                    setChangedColor(temp.getColor());

                if (getCurrType()==Type.RV){
                    changeDirection();
                }

                if (getCurrType()==Type.BL){
                    this.isBlock=true;
                }
                return;
            }
        }

        //CC
        if (buf!=null){
            repository.addToRep(middleCard);
            setPreviousCard(middleCard);
            middleCard=buf;
            changeColor(playerNumber);
            setCurrType(Type.ColorOnly);
            cardSets.get(playerNumber).removeCard(buf);
            setRecentCards(playerNumber,buf);
            return;
        }

        //P4
        if (buff!=null){
            repository.addToRep(middleCard);
            setPreviousCard(middleCard);
            middleCard=buff;
            changeColor(playerNumber);
            setCurrType(buff.getType());
            cardSets.get(playerNumber).removeCard(buff);
            setRecentCards(playerNumber,buff);
            increaseBufferOfPlus(4);
            return;

        }

        Card cardTemp=pickRandom();
        cardSets.get(playerNumber).addCard(cardTemp);
        repository.removeCard(cardTemp);
        if (cardTemp.getType()==getCurrType() || cardTemp.getColor()==getCurrColor()){
            putONBoard(cardTemp,playerNumber);
            System.out.print("put the taken card: ");
            draw(cardTemp);
        }

        setPreviousCard(null);
    }

    /**put a card manually when its your turn**/
    public void putCardManually(){
        Scanner scn=new Scanner(System.in);
        int index=scn.nextInt();
        while (index-1>=cardSets.get(0).getCards().size() || index-1<0){
            System.out.println("the number you entered is out of band.");
            index=scn.nextInt();
        }
        put(index-1);
    }

    /**put card if its your turn in different situation
     * @param index of tha card to put**/
    public void put(int index){
        Card card=cardSets.get(0).getCards().get(index);

        if (getCurrType()==Type.P4){
            if (card.getType()==Type.P2){
                putONBoard(card,0);
                increaseBufferOfPlus(2);
                setChangedColor(card.getColor());
                return;
            }

                if (sameNumber(card)){
                    repository.addToRep(middleCard);
                    setPreviousCard(middleCard);
                    middleCard=card;
                    changeColor(0);
                    setCurrType(card.getType());
                    cardSets.get(0).removeCard(card);
                    setRecentCards(0,card);
                    increaseBufferOfPlus(4);
                    return;
                }

            pickCard( getBufferOfPlus(),0);
            resetBufferOfPlus();
            setCurrType(Type.ColorOnly);
            setPreviousCard(null);
            return;
        }


        if (middleCard.getType()==Type.P2 && getCurrType()!=Type.ColorOnly){
                if(card.getType()==Type.P2){
                    putONBoard(card,0);
                    increaseBufferOfPlus(2);
                    setChangedColor(card.getColor());
                    return;
                }

            if (card.getType()==Type.P4){
                repository.addToRep(middleCard);
                setPreviousCard(middleCard);
                middleCard=card;
                changeColor(0);
                setCurrType(card.getType());
                cardSets.get(0).removeCard(card);
                setRecentCards(0,card);
                increaseBufferOfPlus(4);
                return;
            }
            pickCard(getBufferOfPlus(),0);
            resetBufferOfPlus();
            setCurrType(Type.ColorOnly);
            setPreviousCard(null);
            return;
        }

        if (getCurrType()==Type.ColorOnly){
                setCurrColor(CColor.getColor(changedColor));
                if(sameColor(card) && getCurrColor()!=CColor.BLACK){
                    putONBoard(card,0);

                    if (getCurrType()==Type.P2)
                        setChangedColor(card.getColor());

                    if (getCurrType()==Type.RV){
                        changeDirection();
                    }

                    if (getCurrType()==Type.BL){
                        this.isBlock=true;
                    }

                    return;
                }
        }



            if (card.getColor()!= CColor.BLACK){
                if(card.getColor()==getCurrColor()){
                    putONBoard(card,0);

                    if (getCurrType()==Type.P2)
                        setChangedColor(card.getColor());

                    if (getCurrType()==Type.RV){
                        changeDirection();
                    }

                    if (getCurrType()==Type.BL){
                        this.isBlock=true;
                    }

                    return;
                }
            }

            if(sameNumber(card) && card.getType()!=Type.ColorOnly){
                putONBoard(card,0);

                if (getCurrType()==Type.P2)
                    setChangedColor(card.getColor());

                if (getCurrType()==Type.RV){
                    changeDirection();
                }

                if (getCurrType()==Type.BL){
                    this.isBlock=true;
                }

                return;
            }

         //CC
        if (card.getType()==Type.CC){
            repository.addToRep(middleCard);
            setPreviousCard(middleCard);
            middleCard=card;
            changeColor(0);
            setCurrType(Type.ColorOnly);
            cardSets.get(0).removeCard(card);
            setRecentCards(0,card);
            return;
        }

        //P4
        if (card.getType()==Type.P4){
            repository.addToRep(middleCard);
            setPreviousCard(middleCard);
            middleCard=card;
            changeColor(0);
            setCurrType(card.getType());
            cardSets.get(0).removeCard(card);
            setRecentCards(0,card);
            increaseBufferOfPlus(4);
            return;
        }
        Card cardTemp=pickRandom();
        cardSets.get(0).addCard(cardTemp);
        repository.removeCard(cardTemp);
        if (cardTemp.getType()==getCurrType() || cardTemp.getColor()==getCurrColor()){
            putONBoard(cardTemp,0);
            System.out.print("put the taken card:");
            draw(cardTemp);
        }

        setPreviousCard(null);
    }



    /**draw the game board that includes players and their score,
     *  the previous card, the middle card and whose turn it is**/
    public void drawBoard(){
        Iterator<CardSet> ite=cardSets.iterator();
        ite.next();
        int i=1;
        while (i<cardSets.size()){
            System.out.print(ColorConsole.CYAN);
            System.out.print("player"+i+" "+cardSets.get(i).getCards().size()+"| ");
            i++;
        }
        System.out.print(ColorConsole.RESET);
        System.out.print(ColorConsole.MAGENTA);
        System.out.println();
        System.out.println("previous movement:");
        System.out.print(ColorConsole.RESET);
        Card previous=getPreviousCard();
        if (previous!=null){
            if (previous.getColor()==CColor.RED)
                System.out.print(ColorConsole.RED);
            if (previous.getColor()==CColor.YELLOW)
                System.out.print(ColorConsole.YELLOW);
            if (previous.getColor()==CColor.GREEN)
                System.out.print(ColorConsole.GREEN);
            if (previous.getColor()==CColor.BLUE)
                System.out.print(ColorConsole.BLUE);
            if (previous.getColor()==CColor.BLACK)
                System.out.print(ColorConsole.BLACK);
            System.out.println("$$$$$$$$");
            System.out.println("$  "+previous.getType()+"  $");
            System.out.println("$$$$$$$$");
        }

        System.out.println();
        System.out.print(ColorConsole.MAGENTA);
        if (getDirection()==0)
            System.out.println("clockwise -->");
        else
            System.out.println("anti clockwise <--");
        System.out.print(ColorConsole.RESET);

        drawMiddleCard();
        if (getCurrType()==Type.ColorOnly){
            System.out.print(ColorConsole.MAGENTA);
            System.out.println(CColor.getColor(changedColor).toString());
            System.out.print(ColorConsole.RESET);
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

    /**draw your cards each round.
     * @param i your index**/
    public void drawYourSet(int i){
        Iterator<Card> ite=cardSets.get(i).getCards().iterator();
        while(ite.hasNext()){
            Card temp=ite.next();
            draw(temp);
            System.out.print("  ");
        }
        System.out.println();
    }

    /**draw a card with type and changing its color
     * @param card to draw**/
    public void draw(Card card){

        if (card==null){
            System.out.println("card is null");
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

    /**clear the recent cards(cards of a round)**/
    public void clear(){
        int i=1;
        while(i<recentCards.length && recentCards[i]!=null){
            recentCards[i]=null;
            i++;
        }
    }

    /**determine who start the game by random**/
    public int whoStart(){
        return  (int) (Math.random() * getNumberOfPlayer());
    }

    /**change the direction of rotation whether its
     *  clockwise or anti clockwise**/
    public void changeDirection(){
        if (getDirection()==0)
            setDirection(1);
        else
            setDirection(0);
    }

    /**change the color.
     * its used when there is a P4 or CC type on the middle.
     * @param playerNumber that want change color**/
    public void changeColor(int playerNumber){
        if (playerNumber==0){
            Scanner scn=new Scanner(System.in);
            System.out.println("select a color (R/B/G/Y)");
            String color=scn.nextLine();
            color=color.toUpperCase();
            switch (color){
                case "R":
                    setCurrColor(CColor.RED);
                    setChangedColor(0);
                    break;
                case "B":
                    setCurrColor(CColor.BLUE);
                    setChangedColor(1);
                    break;
                case "G":
                    setCurrColor(CColor.GREEN);
                    setChangedColor(2);
                    break;
                case "Y":
                    setCurrColor(CColor.YELLOW);
                    setChangedColor(3);
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
            System.out.println(getCurrColor().toString());
            setChangedColor(r);
        }

    }


    /**check if given card has same color as the middle card.
     * @param card to compression
     * @return true if possible**/
    public boolean sameColor(Card card){
        if (getCurrColor().equals(card.getColor()))
            return true;
        return false;
    }

    /**check if the given card has same type as the middle card.
     * @param card to compression
     * @return true if possible**/
    public boolean sameNumber(Card card){
        if (card!=null)
        if (getCurrType().equals(card.getType()))
            return true;
        return false;
    }

    /**increase the buffer of plus and it is used for P2 and P4
     * and a sequence of them.
     * @param increment how much we want to increase
     * whether 2 or 4.**/
    public void increaseBufferOfPlus(int increment) {
        this.bufferOfPlus += increment;
    }

    /**reset the buffer of plus**/
    public void resetBufferOfPlus(){
        this.bufferOfPlus=0;
    }

    /**set recent cards by assigning a card to its index.
     * @param index to of the cardSet
     * @param card to assign**/
    public void setRecentCards(int index, Card card) {
        this.recentCards[index]=card;
    }

    /**set the color to change the CurrColor.
     * @param color to assign **/
    public void setChangedColor(CColor color){
        switch (color){
            case RED:
                setCurrColor(CColor.RED);
                setChangedColor(0);
                break;
            case BLUE:
                setCurrColor(CColor.BLUE);
                setChangedColor(1);
                break;
            case GREEN:
                setCurrColor(CColor.GREEN);
                setChangedColor(2);
                break;
            case YELLOW:
                setCurrColor(CColor.YELLOW);
                setChangedColor(3);
                break;
            default:
                System.out.println("the color that you entered is not possible");
        }

    }
    /**set the previous card to show in another method.
     * @param previousCard to assign**/
    public void setPreviousCard(Card previousCard) {
        this.previousCard = previousCard;
    }

    /**@return number of player**/
    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }
    /**@param i the type of direction to set**/
    public void setDirection(int i){
        this.direction=i;
    }
    /**@return the current direction**/
    public int getDirection() {
        return direction;
    }
    /**@return current color**/
    public CColor getCurrColor() {
        return currColor;
    }
    /**@param currColor the color to set CurrColor**/
    public void setCurrColor(CColor currColor) {
        this.currColor = currColor;
    }
    /**@return the buffer of plus**/
    public int getBufferOfPlus() {
        return bufferOfPlus;
    }
    /**@param startIndex the index of player to start**/
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
    /**@return the current type**/
    public Type getCurrType() {
        return currType;
    }
    /**@param currType the type to assign current type**/
    public void setCurrType(Type currType) {
        this.currType = currType;
    }
    /**@param changedColor the number of color to assign**/
    public void setChangedColor(int changedColor) {
        this.changedColor = changedColor;
    }
    /**@return the previous card**/
    public Card getPreviousCard() {
        return previousCard;
    }
}
