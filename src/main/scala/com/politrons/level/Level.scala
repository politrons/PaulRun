package com.politrons.level

import com.politrons.engine.{BackgroundEngine, HeroEngine, PunkEngine}

import java.awt.BorderLayout
import javax.swing.JFrame

class Level extends JFrame {

  val heroEngine = new HeroEngine(810, 267)
  val punk1 = new PunkEngine("Punk1", 270, 575, PunkPatterns.punk1MovePattern)
  val punk2 = new PunkEngine("Punk2", 490, 525, PunkPatterns.punk2MovePattern)
  val punk3 = new PunkEngine("Punk3", 250, 320, PunkPatterns.punk3MovePattern)
  val punk4 = new PunkEngine("Punk4", 290, 62, PunkPatterns.punk4MovePattern)

  initGame()

  private def initGame(): Unit = {
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
