package com.richjohnson.blackjack.players

import com.richjohnson.blackjack.cards.Card

trait Subject {
  def register(observer: Observer): Unit
  def unregister(observer: Observer): Unit
  def notifyObservers(alert: Card): Unit
}
