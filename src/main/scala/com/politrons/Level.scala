package com.politrons

import com.politrons.engine.{BackgroundEngine, HeroEngine}

import java.awt.BorderLayout
import javax.swing.JFrame

class Level extends JFrame {

  initGame()

  private def initGame(): Unit = {
    this.add(new HeroEngine(250,250))
    this.add(new BackgroundEngine(), BorderLayout.CENTER)
    this.setResizable(false)
    this.pack()
    this.setVisible(true)
    setTitle("PaulRun")
    setLocationRelativeTo(null)
    setResizable(false)
    setDefaultCloseOperation(3)
  }

}
