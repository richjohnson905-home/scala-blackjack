package com.richjohnson.blackjack

import com.richjohnson.blackjack.players.{Dealer, Player}
import com.richjohnson.blackjack.strategy.DealerHitsSoft17Strategy

object Main extends App {

  val log = new Logger(true)
  val table = new Table

  val numberOfPlayers = 1
  val players = for (i <- 1 to numberOfPlayers)
    yield (new Player(s"P$i", 1000, new DealerHitsSoft17Strategy(s"P$i", log)))

  val game = new Game(new Dealer(log), new GameHelper)
  game.mainLoop(players.toList, table, 1)

}
