package com.politrons.engine

import com.politrons.SpriteUtils.scaleImage

import java.awt.Image
import javax.swing.*

class BackgroundEngine() extends JLabel  {

  val image: Image = scaleImage(new ImageIcon("src/main/resources/background-1.jpg").getImage, 1024, 768)

  val imageIcon = new ImageIcon(image)
  this.setIcon(imageIcon)

}