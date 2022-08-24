package com.politrons.engine

import com.politrons.sprite.Hero

import java.awt.event.{ActionEvent, ActionListener, KeyAdapter, KeyEvent}
import java.util.concurrent.Executors
import javax.swing.*
import scala.concurrent.{ExecutionContext, Future}

/**
 * Engine responsible to show the life(heart) and remove it when the a life is lost.
 */
class HeroEngine(var xPos: Integer,
                 var yPos: Integer) extends JLabel with ActionListener {

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))

  val heart = new Hero(xPos, yPos)

  // - Y -> Tuple range from X
  val collisionLand: List[Tuple2[Int, Tuple2[Int, Int]]] = List(
    new Tuple2(600, new Tuple2(0, 300)),
    new Tuple2(340, new Tuple2(240, 350))
  )
  init()

  private def init(): Unit = {
    addKeyListener(new KeyListener())
    setFocusable(true)
    setIcon(heart.imageIcon)
    setSize(this.getPreferredSize)
    setLocation(heart.x, heart.y)
    setFrameDelay()
//    startGravity()
  }

  private def setFrameDelay(): Unit = {
    val DELAY = 5
    val timer = new Timer(DELAY, this)
    timer.start()
  }

  override def actionPerformed(e: ActionEvent): Unit = {
    println(s"Hero X:${heart.x} Y:${heart.y}")
    setIcon(heart.imageIcon)
    setLocation(heart.x, heart.y)
  }

  /**
   * Task with the governance of apply gravity in the hero.
   */
  def startGravity(): Unit = {
    Future {
      while (true) {
        val maybeCollision = collisionLand.find(gravity => {
          (gravity._1 == heart.y) && (heart.x >= gravity._2._1 && heart.x <= gravity._2._2)
        })
        if (maybeCollision.isEmpty) {
          heart.y += 1
        }
        Thread.sleep(5)
      }
    }
  }

  /**
   * Key Listener for bird mode
   */
  private class KeyListener() extends KeyAdapter {

    override def keyPressed(e: KeyEvent): Unit = {
      e.getKeyCode match {
        case KeyEvent.VK_SPACE =>
          //          imageIcon = changeImageIcon(images(s"left-" + increaseFrame))
          heart.y -= 50
        case KeyEvent.VK_LEFT =>
          heart.x -= 30
        //          imageIcon = changeImageIcon(images(s"left-" + increaseFrame))
        case KeyEvent.VK_RIGHT =>
          heart.x += 30
        //          imageIcon = changeImageIcon(images(s"left-" + increaseFrame))
        case KeyEvent.VK_UP =>
          heart.y -= 5
        //          imageIcon = changeImageIcon(images(s"up-" + increaseFrame))
        case KeyEvent.VK_DOWN =>
          heart.y += 5
        //          imageIcon = changeImageIcon(images(s"down-" + increaseFrame))
        case _ =>
          println("Key not implemented")
      }
    }
  }


}