package com.politrons.engine

import com.politrons.SpriteUtils.changeImageIcon
import com.politrons.sprite.Punk

import java.util.concurrent.Executors
import javax.swing.*
import scala.collection.*
import scala.concurrent.{ExecutionContext, Future}

class PunkEngine(var name: String,
                 var xPos: Integer,
                 var yPos: Integer,
                 val movePattern: Seq[String],
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
//    collisionEngine()
  }

  def movePatternAction(): Future[Unit] = {
    Future {
      while (enemyAlive) {
        movePattern
          .foreach(move => {
            if (enemyAlive) {
              applyEnemyMovement(move)
//              checkThunderboltCollision()
              Thread.sleep(100)
            }
          })
      }
    }
  }

  def applyEnemyMovement(move: String): Unit = {
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
      case "stop" =>
        punk.x = 0
        punk.y = 0
    }
    setIcon(punk.imageIcon)
    setLocation(punk.x, punk.y)
  }

  private def increaseFrame: Int = {
    if (frame == 2) frame = 1
    else frame += 1
    frame
  }
//
//  /**
//   * As long as enemies are alive, we check constantly if any of the enemies of the map hit the main character.
//   * Function to check if the character collision with an enemy.
//   * In case of collision we reduce one heart in the level, and we set
//   * the character like dead.
//   * In case we lose all hearts the game is over.
//   */
//  private def collisionEngine() = {
//    Future {
//      val deviation = 10
//      while (enemyAlive) {
//        val xComp = Math.abs(characterEngine.character.x - enemy.x)
//        val yComp = Math.abs(characterEngine.character.y - enemy.y)
//        if (xComp <= deviation && yComp <= deviation) {
//          characterEngine.live match {
//            case 3 => heart3Engine.removeHeart()
//            case 2 => heart2Engine.removeHeart()
//            case 1 => heart1Engine.removeHeart(); gameOverEngine.setVisible(true)
//          }
//          characterEngine.live -= 1
//          characterEngine.characterDeadAnimation()
//        }
//        Thread.sleep(100)
//      }
//    }
//  }
//
//  private def checkThunderboltCollision(): Unit = {
//    val deviation = 10
//    val charX = thunderboltEngine.thunderbolt.x
//    val charY = thunderboltEngine.thunderbolt.y
//    val xComp = Math.abs(charX - enemy.x)
//    val yComp = Math.abs(charY - enemy.y)
//    if (xComp <= deviation && yComp <= deviation) {
//      enemyDeadAnimation()
//      setLocation(0, 0)
//      enemyAlive = false
//    }
//  }

  private def enemyDeadAnimation(): Unit = {
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