package com.richjohnson.blackjack.cards

import com.richjohnson.blackjack.cards.CardSuit.CardSuit

case class Card(private val value:Int, suit:CardSuit) {

  def getValue:Int = {
    if (value == 14) {
      11
    } else if (value > 10) {
      10
    } else {
      value
    }
  }

  override def toString: String = {

    val valString = value match {
      case 11 => "J"
      case 12 => "Q"
      case 13 => "K"
      case 14 => "A"
      case _ => value.toString
    }
    s"$valString $suit"
  }

}
