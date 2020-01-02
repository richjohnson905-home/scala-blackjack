package com.richjohnson.blackjack.strategy

import com.richjohnson.blackjack.{BjUtil, Logger}
import com.richjohnson.blackjack.cards.Card
import com.richjohnson.blackjack.players.PlayerActionEnum._

import scala.collection.mutable.ListBuffer

class DealerHitsSoft17Strategy(name:String, log:Logger) extends Strategy {

  override def getAction(hand:ListBuffer[Card], dealerUpCard:Int): PlayerActionEnum = {
    if (hand.size == 2 && BjUtil.sumHand(hand.toList) != 20 && BjUtil.sumHand(hand.toList) != 10 && (hand.head.getValue == hand.last.getValue)) {
      doSplit(hand, dealerUpCard)
    } else if (BjUtil.aceCount(hand.toList) > 0) {
      doSoft(hand, dealerUpCard)
    } else {
      doHard(hand, dealerUpCard)
    }
  }

  private def doSplit(hand:ListBuffer[Card], dealerUpCard:Int): PlayerActionEnum = {
    log.info(s"$name Doing Split Strategy for ${hand.toString()}")
    hand.head.getValue match {
      case x if (2 to 3).contains(x) => if ((8 to 11).contains(dealerUpCard)) Hit else Split
      case 4 => if ((5 to 6).contains(dealerUpCard)) Split else Hit
      case 6 => if ((2 to 6).contains(dealerUpCard)) Split else Hit
      case 7 => if ((2 to 7).contains(dealerUpCard)) Split else Hit
      case 8 => Split
      case 9 => if ((2 to 9).contains(dealerUpCard)) Split else Stand
      case 11 => Split
    }
  }

  private def doSoft(hand:ListBuffer[Card], dealerUpCard:Int): PlayerActionEnum = {
    val handValue = BjUtil.sumHand(hand.toList)
    log.info(s"$name Doing Soft Strategy for $handValue and ${hand.toString()}")
    handValue match {
      case x if (13 to 14).contains(x) => if ((5 to 6).contains(dealerUpCard)) Double else Hit
      case x if (15 to 16).contains(x) => if ((4 to 6).contains(dealerUpCard)) Double else Hit
      case 17 => if ((3 to 6).contains(dealerUpCard)) Double else Hit
      case 18 => {
        if ((2 to 6).contains(dealerUpCard)) {
          Double
        } else if ((7 to 8).contains(dealerUpCard)) {
          Stand
        } else {
          Hit
        }
      }
      case 19 => if (dealerUpCard == 6) Double else Stand
      case _ => {
        log.info(s"$name Soft Standing on: " + handValue)
        Stand
      }
    }
  }

  private def doHard(hand:ListBuffer[Card], dealerUpCard:Int): PlayerActionEnum = {
    val handValue = BjUtil.sumHand(hand.toList)
    log.info(s"$name Doing Hard Strategy for $handValue with ${hand.toString()}")
    handValue match {
      case x if (4 to 8).contains(x) => Hit
      case 9 => if ((3 to 6).contains(dealerUpCard)) Double else Hit
      case 10 => if ((2 to 9).contains(dealerUpCard)) Double else Hit
      case 11 => Double
      case 12 => if ((4 to 6).contains(dealerUpCard)) Stand else Hit
      case x if (13 to 16).contains(x) => if ((2 to 6).contains(dealerUpCard)) Stand else Hit
      case x if (17 to 21).contains(x) => Stand
      case _ => {
        log.info(s"$name Hard Busted on: " + handValue)
        Stand
      }
    }
  }

}
