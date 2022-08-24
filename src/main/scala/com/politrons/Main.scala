package com.politrons

import com.politrons.Level

import java.awt.EventQueue

object Main extends App {
  EventQueue.invokeLater(() => {
    val ex = new Level()
    ex.setVisible(true)
  })
}

