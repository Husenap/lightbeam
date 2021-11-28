package lightbeam.image

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Image(width: Int, height: Int, rgbPixels: Array[Int]):
  private val underlying =
    BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
  underlying.setRGB(0, 0, width, height, rgbPixels, 0, width)

  def save(path: String) =
    ImageIO.write(underlying, "png", File(path))
