package lightbeam.scene

import lightbeam.math.Ray
import lightbeam.math.Vec3

class Metal(val albedo: Vec3, roughness: Double) extends Material:
  require(
    roughness >= 0.0 && roughness <= 1.0,
    "Roughness must be within the range of [0,1]."
  )

  override def scatter(ray: Ray, hit: HitRecord): Option[ScatterData] =
    var reflected = ray.direction reflect hit.normal

    if roughness > 0.0 then reflected += roughness * Vec3.randomDirection

    if (reflected dot hit.normal) < 0 then None
    else Some(ScatterData(Ray(hit.point, reflected), albedo))
