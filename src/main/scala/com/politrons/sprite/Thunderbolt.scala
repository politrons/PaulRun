package com.politrons.sprite

import com.politrons.sprite.SpriteUtils.{changeImageIcon, scaleImage}
import java.awt.Image
import javax.swing.ImageIcon

class Thunderbolt(var x: Integer = 0, var y: Integer = 0) {

  var image: Image = scaleImage(null, 40, 40)
  var imageIcon: ImageIcon = new ImageIcon(image)

  val images = Map(
    "left" -> new ImageIcon("src/main/resources/thunderbolt/thunderbolt-left.png"),
    "right" -> new ImageIcon("src/main/resources/thunderbolt/thunderbolt-right.png")
  )

}