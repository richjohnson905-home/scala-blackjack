package com.richjohnson.blackjack

import com.richjohnson.blackjack.cards.{Card, CardSuit}
import org.scalatest.{FunSuite, Matchers}

import scala.collection.mutable.ListBuffer

class BjUtilTest extends FunSuite with Matchers {

  test ("Soft Hand Value") {
    val hand = ListBuffer[Card](Card(14, CardSuit.Diamonds), Card(12, CardSuit.Hearts), Card(14, CardSuit.Spades))

    BjUtil.sumHand(hand.toList) should equal (12)
  }

  test ("Soft Hand Value2") {
    val hand = ListBuffer[Card](Card(14, CardSuit.Diamonds), Card(12, CardSuit.Hearts), Card(14, CardSuit.Spades), Card(14, CardSuit.Spades))

    BjUtil.sumHand(hand.toList) should equal (13)
  }
  test ("Soft Hand Value3") {
    val hand = ListBuffer[Card](Card(10, CardSuit.Diamonds), Card(10, CardSuit.Hearts), Card(14, CardSuit.Spades))

    BjUtil.sumHand(hand.toList) should equal (21)
  }
  test ("Sum Hand with Ace") {
    val hand = ListBuffer[Card](Card(2, CardSuit.Diamonds), Card(14, CardSuit.Spades))

    BjUtil.sumHand(hand.toList) should equal (13)
  }
  test ("Sum Hand with two Ace") {
    val hand = ListBuffer[Card](Card(14, CardSuit.Diamonds), Card(5, CardSuit.Diamonds), Card(2, CardSuit.Diamonds), Card(14, CardSuit.Spades))

    BjUtil.sumHand(hand.toList) should equal (19)
  }
  test ("Bug") {
    val hand = ListBuffer[Card](
      Card(9, CardSuit.Diamonds),
      Card(6, CardSuit.Diamonds),
      Card(14, CardSuit.Diamonds),
      Card(13, CardSuit.Spades)
    )

    BjUtil.sumHand(hand.toList) should equal (26)
  }
}
