package com.au.joe.poker;


import com.au.joe.poker.enums.Rank;
import com.au.joe.poker.vo.Hand;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by gosu on 18/2/18.
 */
public class PokerHandsTest {

  @Test
  public void testWinner() {
    // ROYAL FLUSH
    Hand handOne = new Hand("AS JS QS KS TS".split(" "));
    Hand handTwo = new Hand("7C 8D QS KS AS".split(" "));
    sortAndEval(handOne, handTwo);
    assertEquals(Rank.ROYAL_FLUSH, handOne.category);
    assertEquals("Player 1 Wins - Royal Flush",1, PokerHands.winner(handOne, handTwo));

    // STRAIGHT FLUSH
    handOne = new Hand("7C 8C 9C TC JC".split(" "));
    handTwo = new Hand("7C 8D QS KS AS".split(" "));
    sortAndEval(handOne, handTwo);
    assertEquals(Rank.STRAIGHT_FLUSH, handOne.category);
    assertEquals("Player 1 Wins - Straight Flush",1, PokerHands.winner(handOne, handTwo));

    // FOUR OF A KIND
    handOne = new Hand("7C 7D 7S 7H QC".split(" "));
    handTwo = new Hand("7C 8D QS KS AS".split(" "));
    sortAndEval(handOne, handTwo);
    assertEquals(Rank.FOUR_OF_A_KIND, handOne.category);
    assertEquals("Player 1 Wins - Four of a kind",1, PokerHands.winner(handOne, handTwo));

    // FULL HOUSE
    handOne = new Hand("7C 7D 7S TH TC".split(" "));
    handTwo = new Hand("7C 8D QS KS AS".split(" "));
    sortAndEval(handOne, handTwo);
    assertEquals(Rank.FULL_HOUSE, handOne.category);
    assertEquals("Player 1 Wins - Full House",1, PokerHands.winner(handOne, handTwo));

    // FLUSH
    handOne = new Hand("5C 7C 9C AC TC".split(" "));
    handTwo = new Hand("7C 8D QS KS AS".split(" "));
    sortAndEval(handOne, handTwo);
    assertEquals(Rank.FLUSH, handOne.category);
    assertEquals("Player 1 Wins - Flush",1, PokerHands.winner(handOne, handTwo));

    // STRAIGHT
    handOne = new Hand("7C 8D 9S TH JC".split(" "));
    handTwo = new Hand("7C 8D QS KS AS".split(" "));
    sortAndEval(handOne, handTwo);
    assertEquals(Rank.STRAIGHT, handOne.category);
    assertEquals("Player 1 Wins - Straight",1, PokerHands.winner(handOne, handTwo));

    // THREE OF A KIND
    handOne = new Hand("7C 7D 7S TH JC".split(" "));
    handTwo = new Hand("7C 8D QS KS AS".split(" "));
    sortAndEval(handOne, handTwo);
    assertEquals(Rank.THREE_OF_A_KIND, handOne.category);
    assertEquals("Player 1 Wins - Three of a kind",1, PokerHands.winner(handOne, handTwo));

    // TWO PAIRS
    handOne = new Hand("7C 7D 6S 8H 8C".split(" "));
    handTwo = new Hand("7C 8D QS KS AS".split(" "));
    sortAndEval(handOne, handTwo);
    assertEquals(Rank.TWO_PAIRS, handOne.category);
    assertEquals("Player 1 Wins - Two pair",1, PokerHands.winner(handOne, handTwo));

    // PAIR
    handOne = new Hand("7C 6D 3S 5H 7C".split(" "));
    handTwo = new Hand("7C 8D QS KS AS".split(" "));
    sortAndEval(handOne, handTwo);
    assertEquals(Rank.ONE_PAIR, handOne.category);
    assertEquals("Player 1 Wins - One paid",1, PokerHands.winner(handOne, handTwo));

    // HIGH CARD
    handOne = new Hand("3C 5D 9S KH JC".split(" "));
    handTwo = new Hand("7C 8D QS KS AS".split(" "));
    sortAndEval(handOne, handTwo);
    assertEquals(Rank.HIGH_CARD, handOne.category);
    assertEquals("Player 1 Wins - High Card",2, PokerHands.winner(handOne, handTwo));
  }

  public void sortAndEval(Hand h1, Hand h2) {
    h1.sortCards();
    h2.sortCards();
    h1.evaluate();
    h2.evaluate();
  }

  @Ignore
  @Test
  public void testMain() {
    System.out.println("Testing from input file");
    int winsPlayer1 = 0;
    int winsPlayer2 = 0;

    ClassLoader classLoader = getClass().getClassLoader();
    InputStream is = classLoader.getResourceAsStream("poker-hands.txt");
    InputStreamReader iss = new InputStreamReader(is);
    BufferedReader reader = new BufferedReader(iss);

    try {
      while (true) {
        String input = reader.readLine();
        if (input == null) {
          break;
        }
        if (!input.matches("(?:[2-9TJQKA][SCHD] ){9}[2-9TJQKA][SCHD]")) {
          System.out.println("Wrong input format.");
          break;
        }
        String[] cards = input.split(" ");
        String[] handOneStr = Arrays.asList(cards).subList(0, 5).toArray(new String[5]);
        String[] handTwoStr = Arrays.asList(cards).subList(5, 10).toArray(new String[5]);

        Hand handOne = new Hand(handOneStr);
        Hand handTwo = new Hand(handTwoStr);
        handOne.sortCards();
        handTwo.sortCards();

        handOne.evaluate();
        handTwo.evaluate();
        int res = PokerHands.winner(handOne, handTwo);
        if (res == 1) {
          winsPlayer1++;
        } else if (res == 2) {
          winsPlayer2++;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(winsPlayer1, 263);
    System.out.println("Player 1: 263 wins. PASSED.\n");
    assertEquals(winsPlayer2, 237);
    System.out.println("Player 2: 237 wins. PASSED.\n");
    System.out.println("##### Sample file ok! ######\n");
  }
}
