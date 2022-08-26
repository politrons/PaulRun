package com.politrons.level

import com.politrons.engine.{BackgroundEngine, BulletEngine, HeartEngine, HeroEngine, PunkEngine}
import com.politrons.level.PunkPatterns.{punk1MovePattern, punk2MovePattern, punk3MovePattern, punk4MovePattern}

import java.awt.BorderLayout
import javax.swing.JFrame

/**
 * Main [JFrame] Level where we add all [JLabel](Engines) of the game.
 */
class Level extends JFrame {

  val bulletEngine = new BulletEngine()
  val heart1Engine = new HeartEngine(100, 10)
  val heart2Engine = new HeartEngine(70, 10)
  val heart3Engine = new HeartEngine(40, 10)
  val heroEngine = new HeroEngine(810, 267, List(heart1Engine, heart2Engine, heart3Engine), bulletEngine)
  val punk1 = new PunkEngine("Punk1", 270, 575, punk1MovePattern, heroEngine, bulletEngine)
  val punk2 = new PunkEngine("Punk2", 490, 525, punk2MovePattern, heroEngine, bulletEngine)
  val punk3 = new PunkEngine("Punk3", 250, 320, punk3MovePattern, heroEngine, bulletEngine)
  val punk4 = new PunkEngine("Punk4", 290, 62, punk4MovePattern, heroEngine, bulletEngine)

  initGame()

  private def initGame(): Unit = {
    this.add(heart1Engine)
    this.add(heart2Engine)
    this.add(heart3Engine)
    this.add(punk1)
    this.add(punk2)
    this.add(punk3)
    this.add(punk4)
    this.add(bulletEngine)
    this.add(new BackgroundEngine(heroEngine), BorderLayout.CENTER)
    this.setResizable(false)
    this.pack()
    this.setVisible(true)
    setTitle("PaulRun")
    setLocationRelativeTo(null)
    setResizable(false)
    setDefaultCloseOperation(3)
  }
}

object Level {

  enum Orientation(orientation: String) {

    def get: String = orientation

    case Left extends Orientation("left")

    case Right extends Orientation("right")

  }
}
