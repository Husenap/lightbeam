package lightbeam.scene

import lightbeam.math.Ray
import lightbeam.math.Vec3

class Camera(
    lookFrom: Vec3,
    lookAt: Vec3,
    up: Vec3,
    fov: Double,
    aspectRatio: Double,
    aperture: Double,
    focusDistance: Double
):
  private val theta          = math.toRadians(fov)
  private val h              = math.tan(theta * 0.5)
  private val viewportHeight = 2.0 * h
  private val viewportWidth  = aspectRatio * viewportHeight

  private val w = (lookFrom - lookAt).normalized
  private val u = (up cross w).normalized
  private val v = w cross u

  private val origin          = lookFrom
  private val horizontalAxis  = focusDistance * viewportWidth * u
  private val verticalAxis    = focusDistance * viewportWidth * v
  private val lowerLeftCorner = origin -
    horizontalAxis * 0.5 -
    verticalAxis * 0.5 -
    focusDistance * w

  private val lensRadius = aperture * 0.5

  def getRay(u: Double, v: Double): Ray =
    val rd     = lensRadius * Vec3.randomInUnitDisc
    val offset = u * rd.x + v * rd.y
    Ray(
      origin + offset,
      lowerLeftCorner + u * horizontalAxis + v * verticalAxis - origin - offset
    )
