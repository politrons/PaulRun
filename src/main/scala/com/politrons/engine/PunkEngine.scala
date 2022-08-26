package com.politrons.engine

import com.politrons.sprite.SpriteUtils.changeImageIcon
import com.politrons.sprite.Punk

import java.util.concurrent.Executors
import javax.swing.*
import scala.collection.*
import scala.concurrent.{ExecutionContext, Future}

class PunkEngine(var name: String,
                 var xPos: Integer,
                 var yPos: Integer,
                 val movePattern: Seq[String],
                 val heroEngine: HeroEngine,
                 val bulletEngine: BulletEngine,
                 var enemyAlive: Boolean = true
                ) extends JLabel {

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))

  val punk = new Punk(xPos, yPos)
  private var frame = 0

  init()

  private def init(): Unit = {
    setFocusable(true)
    setIcon(punk.imageIcon)
    setSize(this.getPreferredSize)
    movePatternAction()
    collisionEngine()
  }

  /**
   * Task to governance of the movement of the punk enemy.
   * Also after make every move we check if the enemy has been hit
   * by a bullet
   */
  def movePatternAction(): Future[Unit] = {
    Future {
      while (enemyAlive) {
        movePattern
          .foreach(move => {
            if (enemyAlive) {
              applyEnemyMovement(move)
              checkThunderboltCollision()
              Thread.sleep(100)
            }
          })
      }
    }
  }

  def applyEnemyMovement(move: String | Int): Unit = {
    move match {
      case "left" =>
        punk.x -= 5
        punk.imageIcon = changeImageIcon(punk.images("left-" + increaseFrame))
      case "right" =>
        punk.x += 5
        punk.imageIcon = changeImageIcon(punk.images("right-" + increaseFrame))
      case "up" =>
        punk.y -= 5
        punk.imageIcon = changeImageIcon(punk.images("up-" + increaseFrame))
      case "down" =>
        punk.y += 5
        punk.imageIcon = changeImageIcon(punk.images("down-" + increaseFrame))
      case -1 =>
        punk.x = -100
        punk.y = 0
      case _ => println("No movement configure")
    }
    setIcon(punk.imageIcon)
    setLocation(punk.x, punk.y)
  }

  private def increaseFrame: Int = {
    if (frame == 2) frame = 1
    else frame += 1
    frame
  }

  //  def (user:User).getUserInfo():String = s"Name:${user.name}, Surname${user.surname}, Age:${user.age} "


  /**
   * As long as enemies are alive, we check constantly if any of the enemies of the map hit the main hero.
   * Function to check if the hero collision with an enemy.
   * In case of collision we reduce one heart in the level, and we set the hero like dead.
   * In case we lose all hearts the game is over.
   */
  private def collisionEngine() = {
    Future {
      val deviation = 10
      while (enemyAlive) {
        val xComp = Math.abs(heroEngine.hero.x - punk.x)
        val yComp = Math.abs(heroEngine.hero.y - punk.y)
        if (xComp <= deviation && yComp <= deviation) {
          heroEngine.setDeadHero()
        }
        Thread.sleep(100)
      }
    }
  }

  /**
   * Function to check if the punk it's been hit by the bullet
   */
  private def checkThunderboltCollision(): Unit = {
    val deviation = 10
    val xComp = Math.abs(bulletEngine.bullet.x - punk.x)
    val yComp = Math.abs(bulletEngine.bullet.y - punk.y)
    if (xComp <= deviation && yComp <= deviation) {
      deadAnimation()
      applyEnemyMovement(-1)
      enemyAlive = false
    }
  }

  private def deadAnimation(): Unit = {
    punk.x = 540
    punk.y = 78
    0 to 50 foreach { _ =>
      setIcon(null)
      Thread.sleep(10)
      setIcon(punk.imageIcon)
      Thread.sleep(10)
      setIcon(null)
      Thread.sleep(10)
    }
  }


}