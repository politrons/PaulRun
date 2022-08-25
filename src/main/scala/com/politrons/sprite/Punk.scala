package com.politrons.sprite

import SpriteUtils._

import java.awt.Image
import javax.swing.ImageIcon

/**
 * All logic related with the movement of the sprite [Enemy]
 */
class Punk(var x:Integer, var y:Integer) {

  var image: Image = null
  var imageIcon: ImageIcon = null

  val images = Map(
    "left-" + 1 -> new ImageIcon("src/main/resources/punk/punk-left-1.png"),
    "left-" + 2 -> new ImageIcon("src/main/resources/punk/punk-left-2.png"),
    "right-" + 1 -> new ImageIcon("src/main/resources/punk/punk-right-1.png"),
    "right-" + 2 -> new ImageIcon("src/main/resources/punk/punk-right-2.png")
  )

  loadImage()

  private def loadImage(): Unit = {
    imageIcon = images("left-1")
    image = imageIcon.getImage
    image = scaleImage(image, 40, 40)
    imageIcon = new ImageIcon(image)
  }
}