import javax.imageio.ImageIO
import lightbeam.Lightbeam
import lightbeam.raytracing.Scene

@main
def main =
  val scene     = Scene()
  val lightbeam = Lightbeam(scene)

  lightbeam.render()
  lightbeam.save("output")
