package com.au.joe.poker;

import com.au.joe.poker.vo.Hand;

import java.util.Arrays;

/**
 * Created by gosu on 18/2/18.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class PokerHands {
  public static void main(String[] args) {
    int winsPlayer1 = 0;
    int winsPlayer2 = 0;

    BufferedReader br = null;

    try {
      br = new BufferedReader(new InputStreamReader(System.in));
      // loop through input
      while (true) {
        String input = br.readLine();
        if (input == null) {
          break;
        }
        //validate
        if (!input.matches("(?:[2-9TJQKA][SCHD] ){9}[2-9TJQKA][SCHD]")) {
          System.out.println("Wrong input format.");
          break;
        }

        String[] cards = input.split(" ");
        List<String> handOneStr = Arrays.asList(cards).subList(0, 5);
        List<String> handTwoStr = Arrays.asList(cards).subList(5, 10);

        Hand handOne = new Hand(handOneStr.toArray(new String[5]));
        Hand handTwo = new Hand(handTwoStr.toArray(new String[5]));

        handOne.sortCards();
        handTwo.sortCards();

        handOne.evaluate();
        handTwo.evaluate();

        int res = winner(handOne, handTwo);
        if (res == 1) {
          winsPlayer1++;
        } else if (res == 2) {
          winsPlayer2++;
        }
      }

      System.out.println("Player 1: " + winsPlayer1 + " hands");
      System.out.println("Player 2: " + winsPlayer2 + " hands");

      System.exit(0);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static int winner(Hand hand1, Hand hand2) {
    if (hand1.getRank().getValue() > hand2.getRank()
            .getValue()) {
      return 1;
    } else if (hand1.getRank().getValue() < hand2.getRank()
            .getValue()) {
      return 2;
    } else {
      // final tie break!
      for (int i = 4; i >= 0; i--) {
        if (hand1.getCard(i).getValue() > hand2.getCard(i).getValue()) {
          return 1;
        } else if (hand1.getCard(i).getValue() < hand2.getCard(i).getValue()) {
          return 2;
        }
      }
      return -1;
    }

  }
}
