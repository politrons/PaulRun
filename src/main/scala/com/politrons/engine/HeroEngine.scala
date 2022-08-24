package com.politrons.engine

import com.politrons.SpriteUtils.changeImageIcon
import com.politrons.sprite.Hero

import java.awt.event.{ActionEvent, ActionListener, KeyAdapter, KeyEvent}
import java.util.concurrent.Executors
import javax.swing.*
import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

class HeroEngine(var xPos: Integer,
                 var yPos: Integer) extends JLabel with ActionListener {

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))

  val hero = new Hero(xPos, yPos)

  private var frame = 1

  // - Y -> Tuple land range from X
  val collisionLand: List[Tuple2[Int, Tuple2[Int, Int]]] = List(
    new Tuple2(575, new Tuple2(0, 300)),
    new Tuple2(320, new Tuple2(240, 350)),
    new Tuple2(217, new Tuple2(30, 140)),
    new Tuple2(370, new Tuple2(545, 650)),
    new Tuple2(525, new Tuple2(490, 710)),
    new Tuple2(575, new Tuple2(810, 1030)),
    new Tuple2(217, new Tuple2(30, 145)),
    new Tuple2(62, new Tuple2(245, 755)),
    new Tuple2(525, new Tuple2(-15, 95)),
    new Tuple2(267, new Tuple2(795, 915)),
    new Tuple2(315, new Tuple2(915, 1030)),
    new Tuple2(60, new Tuple2(960, 1030)),
    new Tuple2(472, new Tuple2(650, 710))
  )

  val collisionBridge: List[Tuple2[Int, Tuple2[Int, Int]]] = List(
    new Tuple2(100, new Tuple2(210, 230)),
    new Tuple2(120, new Tuple2(170, 210)),
    new Tuple2(140, new Tuple2(150, 170)),
    new Tuple2(160, new Tuple2(30, 150))
  )
  init()

  private def init(): Unit = {
    addKeyListener(new KeyListener())
    setFocusable(true)
    setIcon(hero.imageIcon)
    setSize(this.getPreferredSize)
    setLocation(hero.x, hero.y)
    setFrameDelay()
    startGravity()
  }

  private def setFrameDelay(): Unit = {
    val DELAY = 1
    val timer = new Timer(DELAY, this)
    timer.start()
  }

  override def actionPerformed(e: ActionEvent): Unit = {
    println(s"Hero X:${hero.x} Y:${hero.y}")
    setIcon(hero.imageIcon)
    setLocation(hero.x, hero.y)
  }

  /**
   * Task with the governance of apply gravity in the hero.
   */
  def startGravity(): Unit = {
    Future {
      while (true) {
        val maybeCollision = (collisionLand ++ collisionBridge).find(gravity => {
          (gravity._1 == hero.y) && (hero.x >= gravity._2._1 && hero.x <= gravity._2._2)
        })
        if (maybeCollision.isEmpty) {
          hero.y += 1
        }
        Thread.sleep(5)
      }
    }
  }

  private def increaseFrame(): Int = {
    if (frame == 2) frame = 1
    else frame += 1
    frame
  }

  /**
   * Key Listener for bird mode
   */
  private class KeyListener() extends KeyAdapter {

    private val pressedKeys = new mutable.HashSet[Int]()

    override def keyPressed(e: KeyEvent): Unit = {
      pressedKeys.add(e.getKeyCode)
      if (pressedKeys.contains(KeyEvent.VK_LEFT) && pressedKeys.contains(KeyEvent.VK_SPACE)) {
        hero.y -= 100
        hero.x -= 100
      } else {
        e.getKeyCode match {
          case KeyEvent.VK_SPACE =>
            //          hero.imageIcon = changeImageIcon(hero.images(s"left-" + increaseFrame()))
            hero.y -= 100
          case KeyEvent.VK_LEFT =>
            hero.x -= 20
            hero.imageIcon = changeImageIcon(hero.images(s"left-" + increaseFrame()))
          case KeyEvent.VK_RIGHT =>
            hero.x += 20
            hero.imageIcon = changeImageIcon(hero.images(s"right-" + increaseFrame()))
          case KeyEvent.VK_UP =>
            hero.y -= 10
          //          hero.imageIcon = changeImageIcon(hero.images(s"up-" + increaseFrame()))
          case KeyEvent.VK_DOWN =>
            hero.y += 10
          //          hero.imageIcon = changeImageIcon(hero.images(s"down-" + increaseFrame()))
          case _ =>
            println("Key not implemented")
        }
      }
    }

    override def keyReleased(e: KeyEvent): Unit = {
      pressedKeys.remove(e.getKeyCode)
    }

  }

}