package com.richjohnson.blackjack

import com.richjohnson.blackjack.players.{Dealer, Player}

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

class Game(dealer:Dealer, helper:GameHelper) {

  @tailrec
  final def mainLoop(players:List[Player], table: Table, count:Int)  {
    //helper.showPrompt()
    //val userInput = helper.getUserInput()

//    userInput match {
    count match {
      case x if (x > 0) => {
        dealer.ante(players)
        dealer.deal(players, table)

        if (!dealer.checkDealerBlackJack(players)) {
          players.foreach { x =>
            dealer.checkBlackJack(x)
          }
          players.foreach { x =>
            playSplit(x, dealer.play(x, table), table)
          }

          if (playersInGame(players)) {
            if (!dealer.hitDealerBusted(table)) {
              players.foreach { x =>
                dealer.evaluate(x)
              }
            }
          }
        }

        val copyPlayers = players.map(x => x.copy(bank = getBank(x)))

        helper.printGameState(copyPlayers, dealer)

        mainLoop(copyPlayers, dealer.checkShoe(table), count - 1)
      }
      case _   => {
        println("Game Over")
        helper.printFinalState(players, dealer)
        // return out of the recursion here
      }
    }
  }

  private def playersInGame(players:List[Player]):Boolean = {
    players.foreach { x =>
      if (x.getHand.size > 0) {
        return true
      }
    }
    false
  }

  private def getBank(p:Player):Double = {
    if (p.getSplits.size > 0) {
      p.bank + p.getSplits.head.getBet + p.getSplits.last.getBet
    } else {
      p.bank + p.getBet
    }
  }

  private def playSplit(p:Player, optionSplits:Option[ListBuffer[Player]], myTable:Table):Unit = {
    if (optionSplits.isDefined) {
      val split1 = optionSplits.get.head
      val split2 = optionSplits.get.last
      dealer.play(split1, myTable)
      dealer.play(split2, myTable)
    }
  }
}
