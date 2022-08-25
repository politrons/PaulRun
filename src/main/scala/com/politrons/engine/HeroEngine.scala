package com.politrons.engine

import com.politrons.sprite.SpriteUtils.changeImageIcon
import com.politrons.sprite.Hero

import java.awt.event.{ActionEvent, ActionListener, KeyAdapter, KeyEvent}
import java.util.concurrent.Executors
import javax.swing.*
import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

class HeroEngine(var xPos: Integer,
                 var yPos: Integer,
                 val heartEngine1: HeartEngine,
                 val heartEngine2: HeartEngine,
                 val heartEngine3: HeartEngine,
                 val thunderboltEngine: BulletEngine,
                 var movements: Int = 0,
                 var live: Int = 3) extends JLabel with ActionListener {

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))

  val hero = new Hero(xPos, yPos)

  private var orientation = ""
  private var frame = 1

  /**
   * Map of land where the hero has to stop in case is going down.
   * Each Y coordinate has a range of land specify with X1-X2
   * Y -> Tuple land range from X
   */
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

  /**
   * Confogiure how fast do you want the [actionPerformed] to be invoked 
   * to refresh frames in the game.
   */
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
   * Also check if the hero go down out of the map to lose a life.
   */
  private def startGravity(): Unit = {
    Future {
      while (true) {
        val maybeCollision = (collisionLand ++ collisionBridge).find(gravity => {
          (gravity._1 == hero.y) && (hero.x >= gravity._2._1 && hero.x <= gravity._2._2)
        })
        if (maybeCollision.isEmpty) {
          hero.y += 1
          if (isEndOfLevel) {
            setDeadHero()
          }
        } else {
          movements = 0
        }
        Thread.sleep(5)
      }
    }
  }

  private def isEndOfLevel = {
    hero.y > 800
  }

  /**
   * Reduce the number of hearts(lifes) and run the hero animation of dead
   */
  def setDeadHero(): Unit = {
    live match {
      case 3 => heartEngine3.removeHeart()
      case 2 => heartEngine2.removeHeart()
      case 1 => heartEngine1.removeHeart(); //gameOverEngine.setVisible(true)
    }
    live -= 1
    heroDeadAnimation()
  }

  /**
   * Move hero to the initial position and make an effect of reset
   */
  private def heroDeadAnimation(): Unit = {
    Future {
      hero.x = 810
      hero.y = 267
      setLocation(hero.x, hero.y)
      0 to 50 foreach { _ =>
        setIcon(null)
        Thread.sleep(10)
        setIcon(hero.imageIcon)
        Thread.sleep(10)
        setIcon(null)
        Thread.sleep(10)
        setIcon(hero.imageIcon)
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

    /**
     * Only 4 movements in the air are allowed, every time we reach gthe land, the counter of movement is set to 0
     */
    override def keyPressed(e: KeyEvent): Unit = {
      if (movements < 4) {
        movements += 1
        pressedKeys.add(e.getKeyCode)
        if (pressedKeys.contains(KeyEvent.VK_LEFT) && pressedKeys.contains(KeyEvent.VK_SPACE)) {
          hero.y -= 50
          hero.x -= 125
          orientation = "left"
        } else if (pressedKeys.contains(KeyEvent.VK_RIGHT) && pressedKeys.contains(KeyEvent.VK_SPACE)) {
          hero.y -= 50
          hero.x += 125
          orientation = "right"
        } else {
          singleKeyPressed(e)
        }
      }
    }

    override def keyReleased(e: KeyEvent): Unit = {
      pressedKeys.remove(e.getKeyCode)
    }

  }

  private def singleKeyPressed(e: KeyEvent) = {
    e.getKeyCode match {
      case KeyEvent.VK_SPACE =>
        hero.y -= 100
      case KeyEvent.VK_LEFT =>
        hero.x -= 20
        orientation = "left"
        hero.imageIcon = changeImageIcon(hero.images(s"$orientation-" + increaseFrame()))
      case KeyEvent.VK_RIGHT =>
        hero.x += 20
        orientation = "right"
        hero.imageIcon = changeImageIcon(hero.images(s"$orientation-" + increaseFrame()))
      case KeyEvent.VK_DOWN =>
        hero.y += 10
      case KeyEvent.VK_F =>
        thunderboltEngine.directionOfThunderbolt(orientation, hero.x, hero.y)
      case _ =>
        println("Key not implemented")
    }
  }
}