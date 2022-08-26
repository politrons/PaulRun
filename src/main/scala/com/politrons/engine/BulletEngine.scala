package com.politrons.engine

import com.politrons.level.{Level, *}
import com.politrons.sprite.Bullet

import java.util.concurrent.Executors
import javax.swing.*
import scala.concurrent.{ExecutionContext, Future}

class BulletEngine() extends JLabel {

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))

  val bullet = new Bullet(250, 250)

  init()

  private def init(): Unit = {
    setFocusable(true)
    setIcon(bullet.imageIcon)
    setSize(this.getPreferredSize)
    setLocation(bullet.x, bullet.y)
  }

  def directionOfBullet(orientation: Level.Orientation, heroX: Int, heroY: Int): Unit = {
    val bulletDuration = System.currentTimeMillis() + 5000
    Future {
      bullet.x = heroX
      bullet.y = heroY
      while (bulletDuration > System.currentTimeMillis()) {
        println(s"########### Bullet X:${bullet.x} Y:${bullet.y}")
        orientation match {
          case Level.Orientation.Left => bullet.x -= 10
          case Level.Orientation.Right => bullet.x += 10
        }
        bullet.imageIcon = bullet.images(orientation.get)
        setBulletPosition()
        Thread.sleep(100)
      }
    }
  }

  private def setBulletPosition(): Unit = {
    setIcon(bullet.imageIcon)
    setLocation(bullet.x, bullet.y)
  }
}