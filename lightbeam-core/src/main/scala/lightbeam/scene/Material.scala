package lightbeam.scene

import lightbeam.math.Ray
import lightbeam.math.Vec3

case class ScatterData(val ray: Ray, val attenuation: Vec3)

trait Material:
  def scatter(ray: Ray, hit: HitRecord): Option[ScatterData]
