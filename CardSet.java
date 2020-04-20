package com.company;

import java.util.ArrayList;
/**the CardSet class save and moderate cards of each player.
 * @author Mohammad Hossein Asadi
 * @version 18/4/2020**/
public class CardSet {
    private ArrayList<Card> cards;
    private int sum;

    /**construct cards arraylist**/
    public CardSet(){
        cards=new ArrayList<>();
        this.sum=0;
    }

    /**add a card for a player
     * @param card to add**/
    public void addCard(Card card){
        cards.add(card);
    }

    /**remove a card from a player
     * @param card to remove**/
    public void removeCard(Card card){
        cards.remove(card);
    }

    /**@return the set of cards that the player has**/
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**get sum of cards score**/
    public int getSum() {
        return sum;
    }

    /**set the sum of cards score
     * @param sum of cards score**/
    public void setSum(int sum) {
        this.sum = sum;
    }
}
