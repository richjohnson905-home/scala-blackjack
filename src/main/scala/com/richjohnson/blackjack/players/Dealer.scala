package com.richjohnson.blackjack.players

import com.richjohnson.blackjack.cards.Card
import com.richjohnson.blackjack.{BjUtil, Logger, Table}

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

class Dealer(log:Logger) {
  private val myHand = new ListBuffer[Card]

  def ante(players:List[Player]) = {
    myHand.clear()
    players.foreach { x =>
      x.clearHand
      x.clearBet
      x.clearSplits
      x.ante
    }
  }

  def deal(players:List[Player], table:Table):Unit = {
    players.foreach { x =>
      x.takeCard(table.nextCard)
    }
    pushHoleCard(table.nextCard)
    players.foreach { x =>
      x.takeCard(table.nextCard)
    }
    pushUpCard(table.nextCard)
  }

  def checkDealerBlackJack(players:List[Player]):Boolean = {
    if (BjUtil.sumHand(myHand.toList) == 21) {
      log.info("Dealer BlackJack!")
      players.foreach { x =>
        playerLostToDealer(x)
      }
      true
    } else {
      false
    }
  }

  def checkShoe(table:Table): Table = {
    if (table.deckCheck) {
      log.info("Dealer Shuffling new shoe - new table")
      new Table
    } else {
      table
    }
  }

  private def playerBust(p:Player) = {
    log.info(s"${p.name} BUST with ${p.getHand}")
    val bet = p.getBet
    p.clearBet
    p.clearHand
    p.pushBet(bet * -1)
  }

  private def playerLostToDealer(p:Player) = {
    log.info(s"${p.name} LOST to Dealer with ${p.getHand}")
    val bet = p.getBet
    p.clearBet
    p.clearHand
    p.pushBet(bet * -1)
  }

  private def playerPush(p:Player) = {
    log.info(s"${p.name} PUSH with ${p.getHand}")
    p.clearBet
    p.clearHand
  }

  def checkBlackJack(p: Player) = {
    if (p.hasBlackJack) {
      log.info(s"Dealer PAYING BLACKJACK to: ${p.name} Hand: ${p.getHand}")
      val bet = p.getBet
      p.clearBet
      p.pushBet(bet * 1.5)
      p.clearHand
    }
  }

  def evaluate(p: Player) = {
    if (p.getSplits.size > 0) {
      evaluateHand(p.getSplits.head.getHand, p)
      evaluateHand(p.getSplits.last.getHand, p)
    } else {
      if (p.getHand.size > 0) {
        evaluateHand(p.getHand, p)
      }
    }
  }

  private def evaluateHand(hand:ListBuffer[Card], p:Player) = {
    val playerHandValue = BjUtil.sumHand(hand.toList)
    val dealerHandValue = BjUtil.sumHand(myHand.toList)
    if (playerHandValue < dealerHandValue) {
      playerLostToDealer(p)
    } else if (playerHandValue == dealerHandValue) {
      playerPush(p)
    }
  }

  def play(p:Player, table:Table):Option[ListBuffer[Player]] = {
    if (p.getHand.size > 0) {
      val action = p.getAction(getUpCardValue)
      if (action.equals(PlayerActionEnum.Split)) {
        val split1 = new Player(p.name + "-split1", 0, p.strategy)
        val split2 = new Player(p.name + "-split2", 0, p.strategy)

        split1.pushBet(p.getBet)
        split1.takeCard(p.getHand.head)
        split1.takeCard(table.nextCard)

        split2.pushBet(p.getBet)
        split2.takeCard(p.getHand.last)
        split2.takeCard(table.nextCard)
        p.pushSplit(split1)
        p.pushSplit(split2)
        return Some(ListBuffer[Player](split1, split2))
      } else if (action.equals(PlayerActionEnum.Hit)) {
        log.info(s"Dealer ${p.name} play is HIT")
        while (!p.getAction(getUpCardValue).equals(PlayerActionEnum.Stand)) {
          p.takeCard(table.nextCard)
        }
      } else if (action.equals(PlayerActionEnum.Double)) {
        log.info(s"Dealer: ${p.name} Double Down")
        p.pushBet(p.getBet)
        p.takeCard(table.nextCard)
      } else if (action.equals(PlayerActionEnum.Stand)) {
        log.info(s"Dealer: ${p.name} Stand")
      } else {
        log.error("DEV ERROR. UNKNOWN ACTION")
      }
      if (BjUtil.sumHand(p.getHand.toList) > 21) {
        playerBust(p)
      }
    }
    log.info(s"Play Done for ${p.toString}")
    None
  }

  def pushHoleCard(c:Card) = {
    myHand += c
  }

  def pushUpCard(c:Card) = {
    log.info("Dealer Up: " + c.getValue)
    myHand += c
  }

  def getUpCardValue:Int = {
    myHand(1).getValue
  }

  def hitDealerBusted(table:Table):Boolean = {
    log.info(s"Dealer: $toString")
    val handValue = BjUtil.sumHand(myHand.toList)
    if (handValue < 17 || (handValue < 18 && BjUtil.aceCount(myHand.toList) > 0)) {
      hitHand(myHand, table)
    }
    val newHandValue = BjUtil.sumHand(myHand.toList)
    if (newHandValue > 21) {
      log.info(s"Dealer BUSTED: $newHandValue")
      true
    } else {
      log.info(s"Dealer Stays with $newHandValue")
      false
    }
  }

  @tailrec
  final def hitHand(hand:ListBuffer[Card], table:Table):Unit = {
    hand += table.nextCard
    BjUtil.sumHand(hand.toList) match {
      case x if (x < 17) => hitHand(hand, table)
      case x if (x < 18 && BjUtil.aceCount(myHand.toList) > 0) => hitHand(hand, table)
      case x if ((18 to 21).contains(x)) => log.info(s"Dealer stands on $x")
      case _ => log.info(s"Dealer busts on ${BjUtil.sumHand(hand.toList)}")
    }
  }

  override def toString: String = {
    s"Hand: ${myHand.toString} HandValue: ${BjUtil.sumHand(myHand.toList)}"
  }

  // TEST METHODS
  def testGetHand = myHand
}
