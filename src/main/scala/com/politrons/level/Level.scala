package com.politrons.level

import com.politrons.engine.{BackgroundEngine, HeroEngine, PunkEngine}

import java.awt.BorderLayout
import javax.swing.JFrame

class Level extends JFrame {

  val heroEngine = new HeroEngine(250, 250)

  initGame()

  private def initGame(): Unit = {
    this.add(new PunkEngine("Punk", 270, 575, PunkPatterns.punk1MovePattern))
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
