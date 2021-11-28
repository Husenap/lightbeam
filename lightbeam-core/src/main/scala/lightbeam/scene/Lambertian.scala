package lightbeam.scene

import lightbeam.math.Vec3
import lightbeam.math.Ray

class Lambertian(val albedo: Vec3) extends Material:

  override def scatter(ray: Ray, hit: HitRecord): Option[ScatterData] = Some(
    ScatterData(
      Ray(hit.point, hit.normal + Vec3.randomDirection),
      albedo
    )
  )
