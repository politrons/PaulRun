package com.politrons.engine

import com.politrons.sprite.Thunderbolt

import java.util.concurrent.Executors
import javax.swing.*
import scala.concurrent.{ExecutionContext, Future}

class ThunderboltEngine() extends JLabel {

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))

  val thunderbolt = new Thunderbolt(250,250)

  init()

  private def init(): Unit = {
    setFocusable(true)
    setIcon(thunderbolt.imageIcon)
    setSize(this.getPreferredSize)
    setLocation(thunderbolt.x, thunderbolt.y)
  }

  def directionOfThunderbolt(orientation:String,characterX: Int, characterY: Int): Unit = {
    val thunderboltDuration = System.currentTimeMillis() + 5000
    Future {
      thunderbolt.x = characterX
      thunderbolt.y = characterY
      while (thunderboltDuration > System.currentTimeMillis()) {
        println(s"########### Thunderbolt X:${thunderbolt.x} Y:${thunderbolt.y}")
        orientation match {
          case "left" => thunderbolt.x -= 10
          case "right" => thunderbolt.x += 10
          case "up" => thunderbolt.y -= 10
          case "down" => thunderbolt.y += 10
        }
        thunderbolt.imageIcon = thunderbolt.images(orientation)
        setThunderboltPosition()
        Thread.sleep(100)
      }
    }
  }

  private def setThunderboltPosition(): Unit = {
    setIcon(thunderbolt.imageIcon)
    setLocation(thunderbolt.x, thunderbolt.y)
  }
}