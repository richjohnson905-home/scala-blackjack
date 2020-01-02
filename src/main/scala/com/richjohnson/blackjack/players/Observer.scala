package com.richjohnson.blackjack.players

import com.richjohnson.blackjack.cards.Card

trait Observer {
  def publish(alert: Card): Unit
}