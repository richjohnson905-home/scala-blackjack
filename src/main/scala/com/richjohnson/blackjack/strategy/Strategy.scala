package com.richjohnson.blackjack.strategy

import com.richjohnson.blackjack.cards.Card
import com.richjohnson.blackjack.players.PlayerActionEnum.PlayerActionEnum

import scala.collection.mutable.ListBuffer

trait Strategy {

  def getAction(hand:ListBuffer[Card], dealerUpCard:Int):PlayerActionEnum
}
