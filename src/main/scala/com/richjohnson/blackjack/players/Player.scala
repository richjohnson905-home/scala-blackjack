package com.richjohnson.blackjack.players

import com.richjohnson.blackjack.BjUtil
import com.richjohnson.blackjack.cards.Card
import com.richjohnson.blackjack.strategy.Strategy
import com.richjohnson.blackjack.players.PlayerActionEnum._

import scala.collection.mutable.ListBuffer

case class Player(name:String, bank:Double, strategy: Strategy) {

  private val myBets = new ListBuffer[Double]
  private val myHand = new ListBuffer[Card]
  private val mySplits = new ListBuffer[Player]
  private val myAnte = 10

  def getHand:ListBuffer[Card] = myHand
  def getSplits:ListBuffer[Player] = mySplits

  override def toString: String = {
    s"$name Bank: $bank Hand: ${myHand.toString()} HandValue: ${BjUtil.sumHand(myHand.toList)} Bet: ${myBets}"
  }

  def pushSplit(s:Player):Unit = {
    mySplits += s
  }

  def pushBet(money: Double):Unit = {
    myBets += money
  }

  def hasBlackJack: Boolean = {
    BjUtil.sumHand(myHand.toList) == 21
  }

  def ante: Unit = {
    myBets += myAnte
  }

  def clearBet:Unit = {
    myBets.clear()
  }

  def clearHand:Unit = {
    myHand.clear()
  }

  def clearSplits:Unit = {
    mySplits.clear()
  }

  def getAction(dealerUpCard:Int):PlayerActionEnum = {
    strategy.getAction(myHand, dealerUpCard)
  }

  def takeCard(card: Card) = {
    myHand += card
  }

  def getBet:Double = {
    sumBets(myBets.toList)
  }

  private def sumBets(xs: List[Double]): Double = {
    xs match {
      case x :: tail => x + sumBets(tail)
      case Nil => 0.0
    }
  }

}
