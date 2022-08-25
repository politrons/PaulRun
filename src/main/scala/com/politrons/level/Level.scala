package com.politrons.level

import com.politrons.engine.{BackgroundEngine, HeartEngine, HeroEngine, PunkEngine}
import com.politrons.level.PunkPatterns.{punk1MovePattern, punk2MovePattern, punk3MovePattern, punk4MovePattern}

import java.awt.BorderLayout
import javax.swing.JFrame

class Level extends JFrame {

  val heart1Engine = new HeartEngine(100, 10)
  val heart2Engine = new HeartEngine(70, 10)
  val heart3Engine = new HeartEngine(40, 10)
  val heroEngine = new HeroEngine(810, 267, heart1Engine, heart2Engine, heart3Engine)
  val punk1 = new PunkEngine("Punk1", 270, 575, punk1MovePattern, heroEngine)
  val punk2 = new PunkEngine("Punk2", 490, 525, punk2MovePattern, heroEngine)
  val punk3 = new PunkEngine("Punk3", 250, 320, punk3MovePattern, heroEngine)
  val punk4 = new PunkEngine("Punk4", 290, 62, punk4MovePattern, heroEngine)

  initGame()

  private def initGame(): Unit = {
    this.add(heart1Engine)
    this.add(heart2Engine)
    this.add(heart3Engine)
    this.add(punk1)
    this.add(punk2)
    this.add(punk3)
    this.add(punk4)
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
