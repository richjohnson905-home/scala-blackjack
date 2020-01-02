package com.richjohnson.blackjack

class Logger(flag:Boolean) {

  def debug(msg:String) = {
    if (flag) println(msg)
  }

  def info(msg:String) = {
    if (flag) println(msg)
  }

  def error(msg:String) = {
    println(msg)
  }
}
