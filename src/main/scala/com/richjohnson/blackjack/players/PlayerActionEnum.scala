package com.richjohnson.blackjack.players


object PlayerActionEnum extends Enumeration {
  type PlayerActionEnum = Value
  val Stand, Hit, Double, Split, Surrender = Value
}