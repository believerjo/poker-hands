/**
 * Created by gosu on 18/2/18.
 */
package com.au.joe.poker.vo;

import com.au.joe.poker.enums.Rank;
import java.util.Arrays;

public class Hand {
  public Card[] cards;

  public Rank category;

  public Hand(Card[] cards) {
    this.cards = cards;
  }

  public Hand(String[] strArr) {
    if (strArr.length != 5) {
      System.out.println("Unable to parse. Hand size has to be 5.");
    }
    else {
      Card[] cards = new Card[5];
      for (int i = 0; i < 5; i++) {
        cards[i] = new Card(strArr[i]);
      }
      this.cards = cards;
    }
  }

  public void sortCards() {
    Arrays.sort(this.cards);
  }

  public Card getCard(int index) {
    if (index >= 5) {
      return null;
    }
    return cards[index];
  }

  public String toString() {
    String str = "";
    for (Card card : this.cards) {
      str += card.toString() + " ";
    }
    if (str.length() > 0) {
      str += "(" + this.getRank().getDesc() + ")";
    }
    return str;
  }

  public Rank getRank() {
    return this.category;
  }

  public void evaluate() {
    // ROYAL FLUSH
    if (isSameSuit() && isStraight() && this.cards[0].getValue() == 10) {
      this.category = Rank.ROYAL_FLUSH;
      return;
    }
    // STRAIGHT FLUSH
    if (isStraight() && isSameSuit()) {
      this.category = Rank.STRAIGHT_FLUSH;
      return;
    }
    // FOUR OF A KIND
    if (threeOrFour(4)) {
      this.category = Rank.FOUR_OF_A_KIND;
      return;
    }
    // FULL HOUSE
    if (threeOrFour(3) && isPair()) {
      this.category = Rank.FULL_HOUSE;
      return;
    }
    // FLUSH
    if (isSameSuit()) {
      this.category = Rank.FLUSH;
      return;
    }
    // STRAIGHT
    if (isStraight()) {
      this.category = Rank.STRAIGHT;
      return;
    }
    // THREE OF A KIND
    if (threeOrFour(3)) {
      this.category = Rank.THREE_OF_A_KIND;
      return;
    }
    // TWO PAIR
    if (isTwoPair()) {
      this.category = Rank.TWO_PAIRS;
      return;
    }
    // ONE PAIR
    if (isPair()) {
      this.category = Rank.ONE_PAIR;
      return;
    }
    // HIGH CARD
    this.category = Rank.HIGH_CARD;
  }

  public boolean isSameSuit() {
    int first = this.cards[0].getSuit();
    for (int i = 1; i < 5; i++) {
      if (this.cards[i].getSuit() != first) {
        return false;
      }
      first = this.cards[0].getSuit();
    }
    return true;
  }

  public boolean isStraight() {
    int first = this.cards[0].getValue();
    for (int i = 1; i < 5; i++) {
      if (this.cards[i].getValue() != first + 1) {
        return false;
      }
      first++;
    }
    return true;
  }

  public boolean threeOrFour(int n) {
    int third = this.cards[2].getValue();
    int count = 1;

    for (int i = 0; i < 5; i++) {
      if (i != 2 && third == this.cards[i].getValue()) {
        count++;
      }
    }

    if (count == n) {
      return true;
    }
    return false;
  }

  public boolean isPair() {
    int first = this.cards[0].getValue();
    int count = 1;
    for (int i = 1; i < 5; i++) {
      if (this.cards[i].getValue() == first) {
        count++;
        if (count > 2) {
          count = 1;
        }
      }
      first = this.cards[i].getValue();
    }
    // One and only pair
    if (count == 2) {
      return true;
    }
    return false;
  }

  public boolean isTwoPair() {
    int first = this.cards[0].getValue();
    int prev = first;
    int count = 1;
    for (int i = 1; i < 5; i++) {
      if (first == this.cards[i].getValue()) {
        count++;
      }
      if (count > 2 && prev != first) {
        count++;
      }
      prev = first;
      first = this.cards[i].getValue();
    }
    if (count == 4) {
      return true;
    }
    return false;
  }
}
