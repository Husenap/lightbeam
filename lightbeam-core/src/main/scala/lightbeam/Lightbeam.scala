package lightbeam

import lightbeam.image.Image
import lightbeam.math.Ray
import lightbeam.math.Vec3
import lightbeam.scene.Camera
import lightbeam.scene.HittableList
import lightbeam.scene.Scene

case class Settings(
    val aspectRatio: Double = 1.0,
    val imageWidth: Int = 256,
    val samplesPerPixel: Int = 64,
    val maxBounces: Int = 4,
    val fov: Double = 100.0
):
  val imageHeight = (imageWidth / aspectRatio).toInt

class Lightbeam(settings: Settings, scene: Scene):
  def render(path: String): Unit =
    val lookFrom = Vec3(0.5, 0.5, 0.99)
    val lookAt   = Vec3.Zero
    val camera   = Camera(
      lookFrom = lookFrom,
      lookAt = lookAt,
      up = Vec3.Up,
      fov = settings.fov,
      aspectRatio = settings.aspectRatio,
      aperture = 0.0,
      focusDistance = (lookFrom - lookAt).magnitude
    )

    val pixels    = new Array[Vec3](settings.imageWidth * settings.imageHeight)
    val invWidth  = 1.0 / (settings.imageWidth - 1)
    val invHeight = 1.0 / (settings.imageHeight - 1)

    for
      y <- 0 until settings.imageHeight
      x <- 0 until settings.imageWidth
    do
      val index = y * settings.imageWidth + x

      val u = x * invWidth
      val v = 1.0 - (y * invHeight)

      pixels(index) = rayColorLoop(
        camera.getRay(u, v),
        scene.world,
        scene.lightSources,
        settings.maxBounces
      )
      for s <- 1 until settings.samplesPerPixel do
        pixels(index) += rayColorLoop(
          camera.getRay(
            u + (util.Random.nextDouble - 0.5) * invWidth,
            v + (util.Random.nextDouble - 0.5) * invHeight
          ),
          scene.world,
          scene.lightSources,
          settings.maxBounces
        )

    val rgbPixels = pixels.map(color =>
      val result = Vec3.clamp(
        Vec3.pow(color / settings.samplesPerPixel, 1.0 / 2.2),
        0.0,
        1.0
      )

      ((result.r * 255.0).toInt << 16) | ((result.g * 255.0).toInt << 8) | (result.b * 255.0).toInt
    )

    val image = Image(settings.imageWidth, settings.imageHeight, rgbPixels)
    image.save(path)

  private def rayColorLoop(
      ray: Ray,
      world: HittableList,
      lightSources: HittableList,
      maxBounces: Int
  ): Vec3 =
    var currentRay = ray
    var acc        = Vec3.One
    var depth      = maxBounces

    while depth > 0 do
      val lightHit =
        lightSources.hit(currentRay, 0.005, Double.PositiveInfinity)
      val worldHit = world.hit(currentRay, 0.005, Double.PositiveInfinity)

      if (lightHit.isDefined && worldHit.isEmpty) || (
          lightHit.isDefined && worldHit.isDefined && lightHit.get.t < worldHit.get.t
        )
      then
        acc *= Vec3(1.0, 0.9, 0.6) * 4.0
        return acc
      else if worldHit.isDefined then
        val scatter = worldHit.get.material.scatter(currentRay, worldHit.get)
        if scatter.isDefined then
          acc *= scatter.get.attenuation
          currentRay = scatter.get.ray
          depth -= 1
        else return Vec3.Zero
      else
        acc *= Vec3.mix(
          Vec3(0.2, 0.4, 0.7),
          Vec3(4.5, 4.3, 4.0),
          scala.math.pow(
            (currentRay.direction dot Vec3(1.0, 1.0, -1.0).normalized) max 0,
            11.0
          )
        )
        return acc
    Vec3.Zero
