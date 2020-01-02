package com.richjohnson.blackjack

import com.richjohnson.blackjack.players.{Dealer, Player}

import scala.io.StdIn.readLine

class GameHelper {

  def showPrompt(): Unit = {
    print("\n(c)continue or (q)uit: ")
  }

  def getUserInput(): String = readLine.trim.toUpperCase

  def printGameState(players:List[Player], dealer:Dealer) = {
    println("=====================Game State=====================")
    players.foreach { x =>
      println(s"     ${x.name}: ${x.toString}\n")
    }
    println(s"     Dealer: ${dealer.toString}")
    println("====================================================")
  }


  def printFinalState(players:List[Player], dealer:Dealer) = {
    println("=====================Game Over=====================")
    players.foreach { x =>
      println(s"     ${x.name}: ${x.toString}\n")
    }
    println(s"     Dealer: ${dealer.toString}")
    println("====================================================")
  }

}
