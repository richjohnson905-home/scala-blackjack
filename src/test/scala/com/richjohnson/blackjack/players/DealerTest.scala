package com.richjohnson.blackjack.players

import com.richjohnson.blackjack.{Logger, Table}
import com.richjohnson.blackjack.cards.{Card, CardSuit}
import com.richjohnson.blackjack.strategy.DealerHitsSoft17Strategy
import org.scalatest.{FunSuite, Matchers}
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito.when

class DealerTest extends FunSuite with MockitoSugar with Matchers {

  val mockLog = mock[Logger]
  val testObject = new Dealer(mockLog)
  val mockPlayer1 = Player("P1", 1000, new DealerHitsSoft17Strategy("P1", mockLog))
  val mockPlayer2 = Player("P2", 1000, new DealerHitsSoft17Strategy("P2", mockLog))
  val mockPlayer3 = Player("P3", 1000, new DealerHitsSoft17Strategy("P3", mockLog))
  val mockTable = mock[Table]
  val players = List(mockPlayer1, mockPlayer2, mockPlayer3)

  test ("checkDealerBlackJack") {
    testObject.ante(players)
    testObject.pushHoleCard(Card(10, CardSuit.Spades))
    testObject.pushUpCard(Card(14, CardSuit.Hearts))
    testObject.checkDealerBlackJack(players) should equal (true)

    mockPlayer1.getBet should equal (-10)
    mockPlayer2.getBet should equal (-10)
    mockPlayer3.getBet should equal (-10)
  }

  test ("checkBlackJack for player") {
    testObject.ante(players)

    mockPlayer1.takeCard(Card(10, CardSuit.Spades))
    mockPlayer1.takeCard(Card(14, CardSuit.Spades))

    testObject.checkBlackJack(mockPlayer1)

    mockPlayer1.getBet should equal (15)
  }

  test ("Play Double ") {
    when(mockTable.nextCard).thenReturn(Card(5, CardSuit.Diamonds))

    testObject.ante(players)
    testObject.pushHoleCard(Card(10, CardSuit.Spades))
    testObject.pushUpCard(Card(3, CardSuit.Hearts))
    mockPlayer1.takeCard(Card(4, CardSuit.Spades))
    mockPlayer1.takeCard(Card(5, CardSuit.Spades))

    testObject.play(mockPlayer1, mockTable)

    mockPlayer1.getHand.size should equal (3)
    mockPlayer1.getHand.last.getValue should equal (5)
    mockPlayer1.getBet should equal (20)
  }

  test ("hitDealerBusted Busted with 25") {
    when(mockTable.nextCard).thenReturn(Card(10, CardSuit.Diamonds))
    testObject.pushHoleCard(Card(7, CardSuit.Spades))
    testObject.pushUpCard(Card(8, CardSuit.Hearts))

    testObject.hitDealerBusted(mockTable) should equal (true)
  }

  test ("hitDealerBusted Soft 17 Hits") {
    testObject.ante(players)
    when(mockTable.nextCard).thenReturn(Card(10, CardSuit.Diamonds))
    testObject.pushHoleCard(Card(14, CardSuit.Spades))
    testObject.pushUpCard(Card(6, CardSuit.Hearts))

    testObject.hitDealerBusted(mockTable) should equal (true)
    testObject.testGetHand.size should equal (4)
  }

  test ("Splits") {
    testObject.ante(players)
    testObject.pushHoleCard(Card(2, CardSuit.Spades))
    testObject.pushUpCard(Card(10, CardSuit.Hearts))

    mockPlayer1.takeCard(Card(14, CardSuit.Spades))
    mockPlayer1.takeCard(Card(14, CardSuit.Diamonds))

    val optionSplits = testObject.play(mockPlayer1, mockTable)

    mockPlayer1.getSplits.size should equal (2)

    optionSplits.get.toList.size should equal (2)
    optionSplits.get.toList(0).name should equal ("P1-split1")
    optionSplits.get.toList(1).name should equal ("P1-split2")
    optionSplits.get.toList(0).getHand.size should equal (2)
    optionSplits.get.toList(1).getHand.size should equal (2)
    optionSplits.get.toList(0).getBet should equal (10)
    optionSplits.get.toList(1).getBet should equal (10)
  }
}
