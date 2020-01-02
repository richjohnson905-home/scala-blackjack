package com.richjohnson.blackjack

import com.richjohnson.blackjack.cards.{Card, CardSuit}
import com.richjohnson.blackjack.players.{Dealer, Player}
import com.richjohnson.blackjack.strategy.DealerHitsSoft17Strategy
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.{verify, when}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FunSuite, Matchers}
import org.mockito.Mockito.times

import scala.collection.mutable.ListBuffer

class GameTest extends FunSuite with MockitoSugar with Matchers {
  val mockLog = new Logger(true)
  val mockTable = mock[Table]
  val mockHelper = mock[GameHelper]
  val mockDealer = mock[Dealer]
  val mockPlayer1 = Player("P1", 1000, new DealerHitsSoft17Strategy("P1", mockLog))
  val players = List(mockPlayer1)
  val testObject = new Game(mockDealer, mockHelper)

  test ("Game Over") {
//    when(mockHelper.getUserInput()).thenReturn("q")
    testObject.mainLoop(players, mockTable, 0)

    verify(mockHelper).printFinalState(players, mockDealer)
  }

  test ("Happy one round") {
    mockPlayer1.takeCard(new Card(3, CardSuit.Diamonds))
    mockPlayer1.takeCard(new Card(3, CardSuit.Hearts))
//    when(mockHelper.getUserInput()).thenReturn("C", "q")
    when(mockDealer.checkDealerBlackJack(players)).thenReturn(false)
    when(mockDealer.play(mockPlayer1, mockTable)).thenReturn(None)
    when(mockDealer.hitDealerBusted(mockTable)).thenReturn(false)

    testObject.mainLoop(players, mockTable, 1)

//    verify(mockHelper, times(2)).showPrompt()
    verify(mockDealer).ante(players)
    verify(mockDealer).deal(players, mockTable)
    verify(mockDealer).checkBlackJack(mockPlayer1)
    verify(mockDealer).evaluate(mockPlayer1)
//    verify(mockHelper).printGameState(players, mockDealer)
    verify(mockHelper).printFinalState(players, mockDealer)
  }

  test ("Splits") {
    val mockSplit1 = Player("Split1", 1000, new DealerHitsSoft17Strategy("P1", mockLog))
    val mockSplit2 = Player("Split2", 1000, new DealerHitsSoft17Strategy("P1", mockLog))
    mockSplit1.takeCard(new Card(3, CardSuit.Diamonds))
    mockSplit1.takeCard(new Card(3, CardSuit.Hearts))
    mockSplit2.takeCard(new Card(3, CardSuit.Diamonds))
    mockSplit2.takeCard(new Card(3, CardSuit.Hearts))
    mockSplit1.pushBet(10)
    mockSplit2.pushBet(10)
    mockPlayer1.pushSplit(mockSplit1)
    mockPlayer1.pushSplit(mockSplit2)
//    when(mockHelper.getUserInput()).thenReturn("C", "q")
    when(mockDealer.checkDealerBlackJack(players)).thenReturn(false)
    when(mockDealer.play(mockPlayer1, mockTable)).thenReturn(Some(ListBuffer[Player](mockSplit1, mockSplit2)))
    when(mockDealer.hitDealerBusted(mockTable)).thenReturn(false)

    val captor = ArgumentCaptor.forClass(classOf[List[Player]])
    val captor2 = ArgumentCaptor.forClass(classOf[Dealer])

    testObject.mainLoop(players, mockTable, 1)
    verify(mockHelper).printFinalState(captor.capture(), captor2.capture())

    val players2 = captor.getValue.asInstanceOf[List[Player]]
    println(players2.toString)
    players2(0).bank should equal (1020)
  }
}
