package com.richjohnson.blackjack

import com.richjohnson.blackjack.cards.Card

object BjUtil {

  def sumHand(xs: List[Card]): Int = {
    decreaseWithAce(doSumHand(xs), aceCount(xs))
  }

  private def decreaseWithAce(handValue:Int, aceCount:Int):Int = {
    if (aceCount > 0) {
      handValue match {
        case x if (handValue > 21) => decreaseWithAce(handValue - 10, aceCount - 1)
        case _ => handValue
      }
    } else {
      handValue
    }
  }

  private def doSumHand(xs: List[Card]): Int = {
    xs match {
      case x :: tail => x.getValue + doSumHand(tail)
      case Nil => 0
    }
  }

  def aceCount(cards:List[Card]):Int = {
    cards.filter(_.getValue == 11).size
  }

}
