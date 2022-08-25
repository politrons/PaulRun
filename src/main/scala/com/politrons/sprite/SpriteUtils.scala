package com.politrons.sprite

import java.awt.image.BufferedImage
import java.awt.{Image, RenderingHints}
import javax.swing.ImageIcon

object SpriteUtils {

  def scaleImage(srcImg: Image, w: Int, h: Int): Image = {
    val resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)
    val g2 = resizedImg.createGraphics
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
    g2.drawImage(srcImg, 0, 0, w, h, null)
    g2.dispose()
    resizedImg
  }

  def changeImageIcon(imageIcon: ImageIcon): ImageIcon = {
    new ImageIcon(scaleImage(imageIcon.getImage, 40, 40))
  }
}
