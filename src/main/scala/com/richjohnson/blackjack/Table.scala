package com.richjohnson.blackjack

import com.richjohnson.blackjack.cards.{Card, Shoe}

class Table {
  private val shoe = new Shoe
  private val theDeck = shoe.apply(4)
  private val it = theDeck.iterator
  private var ctr = 0

  def nextCard:Card = {
    ctr = ctr + 1
    shoe.next(it)
  }

  def deckCheck:Boolean = {
    //println("CTR: " + ctr)
    ctr > 180
  }
}
