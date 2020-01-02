package com.richjohnson.blackjack.strategy

import com.richjohnson.blackjack.Logger
import com.richjohnson.blackjack.cards.{Card, CardSuit}
import com.richjohnson.blackjack.players.PlayerActionEnum
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FunSuite, Matchers}

import scala.collection.mutable.ListBuffer

class DealerHitsSoft17StrategyTest extends FunSuite with Matchers with MockitoSugar {

  val mockLog = mock[Logger]
  val testObject = new DealerHitsSoft17Strategy("p1", mockLog)

  test ("getAction Hard") {
    testObject.getAction(ListBuffer[Card](Card(4, CardSuit.Spades), Card(2, CardSuit.Spades)), 2) should equal (PlayerActionEnum.Hit)
    testObject.getAction(ListBuffer[Card](Card(4, CardSuit.Spades), Card(5, CardSuit.Spades)), 3) should equal (PlayerActionEnum.Double)
    testObject.getAction(ListBuffer[Card](Card(4, CardSuit.Spades), Card(8, CardSuit.Spades)), 4) should equal (PlayerActionEnum.Stand)
    testObject.getAction(ListBuffer[Card](Card(9, CardSuit.Spades), Card(7, CardSuit.Spades)), 7) should equal (PlayerActionEnum.Hit)
    testObject.getAction(ListBuffer[Card](Card(12, CardSuit.Spades), Card(9, CardSuit.Spades)), 11) should equal (PlayerActionEnum.Stand)
  }

  test ("getAction Soft") {
    testObject.getAction(ListBuffer[Card](Card(14, CardSuit.Spades), Card(2, CardSuit.Spades)), 2) should equal (PlayerActionEnum.Hit)
    testObject.getAction(ListBuffer[Card](Card(14, CardSuit.Spades), Card(2, CardSuit.Spades)), 5) should equal (PlayerActionEnum.Double)
    testObject.getAction(ListBuffer[Card](Card(14, CardSuit.Spades), Card(5, CardSuit.Spades)), 5) should equal (PlayerActionEnum.Double)
    testObject.getAction(ListBuffer[Card](Card(14, CardSuit.Spades), Card(6, CardSuit.Spades)), 5) should equal (PlayerActionEnum.Double)
    testObject.getAction(ListBuffer[Card](Card(14, CardSuit.Spades), Card(6, CardSuit.Spades)), 7) should equal (PlayerActionEnum.Hit)
    testObject.getAction(ListBuffer[Card](Card(14, CardSuit.Spades), Card(7, CardSuit.Spades)), 2) should equal (PlayerActionEnum.Double)
    testObject.getAction(ListBuffer[Card](Card(14, CardSuit.Spades), Card(7, CardSuit.Spades)), 7) should equal (PlayerActionEnum.Stand)
    testObject.getAction(ListBuffer[Card](Card(14, CardSuit.Spades), Card(7, CardSuit.Spades)), 9) should equal (PlayerActionEnum.Hit)
    testObject.getAction(ListBuffer[Card](Card(14, CardSuit.Spades), Card(8, CardSuit.Spades)), 6) should equal (PlayerActionEnum.Double)
    testObject.getAction(ListBuffer[Card](Card(14, CardSuit.Spades), Card(8, CardSuit.Spades)), 7) should equal (PlayerActionEnum.Stand)
    testObject.getAction(ListBuffer[Card](Card(14, CardSuit.Spades), Card(9, CardSuit.Spades)), 7) should equal (PlayerActionEnum.Stand)
  }

  test ("getAction Split") {
    testObject.getAction(ListBuffer[Card](Card(2, CardSuit.Spades), Card(2, CardSuit.Spades)), 2) should equal (PlayerActionEnum.Split)
    testObject.getAction(ListBuffer[Card](Card(2, CardSuit.Spades), Card(2, CardSuit.Spades)), 8) should equal (PlayerActionEnum.Hit)
    testObject.getAction(ListBuffer[Card](Card(3, CardSuit.Spades), Card(3, CardSuit.Spades)), 2) should equal (PlayerActionEnum.Split)
    testObject.getAction(ListBuffer[Card](Card(3, CardSuit.Spades), Card(3, CardSuit.Spades)), 8) should equal (PlayerActionEnum.Hit)
    testObject.getAction(ListBuffer[Card](Card(4, CardSuit.Spades), Card(4, CardSuit.Spades)), 4) should equal (PlayerActionEnum.Hit)
    testObject.getAction(ListBuffer[Card](Card(4, CardSuit.Spades), Card(4, CardSuit.Spades)), 5) should equal (PlayerActionEnum.Split)
    testObject.getAction(ListBuffer[Card](Card(6, CardSuit.Spades), Card(6, CardSuit.Spades)), 6) should equal (PlayerActionEnum.Split)
    testObject.getAction(ListBuffer[Card](Card(6, CardSuit.Spades), Card(6, CardSuit.Spades)), 7) should equal (PlayerActionEnum.Hit)
    testObject.getAction(ListBuffer[Card](Card(7, CardSuit.Spades), Card(7, CardSuit.Spades)), 7) should equal (PlayerActionEnum.Split)
    testObject.getAction(ListBuffer[Card](Card(7, CardSuit.Spades), Card(7, CardSuit.Spades)), 8) should equal (PlayerActionEnum.Hit)

  }
}
