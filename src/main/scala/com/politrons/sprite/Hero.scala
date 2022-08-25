package com.politrons.sprite


import SpriteUtils._

import java.awt.Image
import javax.swing.ImageIcon

class Hero(var x: Integer, var y: Integer) {

  val images = Map(
    "left-" + 1 -> new ImageIcon("src/main/resources/hero/hero-left-1.png"),
    "left-" + 2 -> new ImageIcon("src/main/resources/hero/hero-left-2.png"),
    "right-" + 1 -> new ImageIcon("src/main/resources/hero/hero-right-1.png"),
    "right-" + 2 -> new ImageIcon("src/main/resources/hero/hero-right-2.png")
  )

  val image: Image = scaleImage(images("left-1").getImage, 40, 40)
  var imageIcon: ImageIcon = new ImageIcon(image)

}