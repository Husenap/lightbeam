package lightbeam.scene

import lightbeam.math.Ray
import lightbeam.math.Vec3

class Lambertian(val albedo: Vec3) extends Material:
  override def scatter(ray: Ray, hit: HitRecord): Option[ScatterData] = Some(
    ScatterData(
      Ray(hit.point, hit.normal + Vec3.randomDirection),
      albedo
    )
  )
