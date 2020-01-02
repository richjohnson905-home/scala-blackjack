package com.richjohnson.blackjack.cards

import com.richjohnson.blackjack.players.{Observer, Subject}

import scala.collection.mutable.ListBuffer
import scala.util.Random

class Shoe extends Subject {

  val observerList: ListBuffer[Observer] = ListBuffer[Observer]()

  def apply(numberOfDecks:Int = 1):List[Card] = {
    val deck:ListBuffer[Card] = new ListBuffer[Card]
    for (s <- 1 to numberOfDecks) {
      for (i <- 2 to 14) {
        deck += Card(i, CardSuit.Clubs)
      }
      for (i <- 2 to 14) {
        deck += Card(i, CardSuit.Spades)
      }
      for (i <- 2 to 14) {
        deck += Card(i, CardSuit.Hearts)
      }
      for (i <- 2 to 14) {
        deck += Card(i, CardSuit.Diamonds)
      }
    }

    Random.shuffle(deck.toList)
  }

  def next(it:Iterator[Card]):Card = {
    val card = it.next()
    notifyObservers(card)
    card
  }

  override def register(observer: Observer): Unit = {
    observerList += observer
  }

  override def unregister(observer: Observer): Unit = {
    observerList -= observer
  }

  override def notifyObservers(alert: Card): Unit = {
    observerList.foreach { x =>
      x.publish(alert)
    }
  }

}
