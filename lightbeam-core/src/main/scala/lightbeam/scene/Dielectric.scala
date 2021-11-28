package lightbeam.scene

import lightbeam.math.Ray
import lightbeam.math.Vec3

class Dielectric(val indexOfRefraction: Double) extends Material:
  override def scatter(ray: Ray, hit: HitRecord): Option[ScatterData] =
    val refractionRatio =
      if hit.frontFace then 1.0 / indexOfRefraction else indexOfRefraction

    val cosTheta = (-ray.direction) dot hit.normal min 1.0
    val sinTheta = math.sqrt(1.0 - cosTheta * cosTheta)

    val direction =
      if refractionRatio * sinTheta > 1.0 ||
        reflectance(cosTheta, refractionRatio) > util.Random.nextDouble
      then ray.direction reflect hit.normal
      else ray.direction.refract(hit.normal, refractionRatio)

    Some(ScatterData(Ray(hit.point, direction), Vec3.One))

  private def reflectance(cosine: Double, refractionRatio: Double): Double =
    val r0 = math.pow((1.0 - refractionRatio) / (1.0 + refractionRatio), 2.0)
    r0 + (1.0 - r0) * math.pow(1.0 - cosine, 5.0)
