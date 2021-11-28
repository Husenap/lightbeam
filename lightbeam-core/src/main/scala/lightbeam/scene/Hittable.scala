package lightbeam.scene

import scala.compiletime.ops.boolean
import lightbeam.math.Vec3
import lightbeam.math.Ray

case class HitRecord private (
    val point: Vec3,
    var normal: Vec3,
    val material: Int,
    val t: Double,
    var frontFace: Boolean
)

case object HitRecord:
  def apply(
      point: Vec3,
      ray: Ray,
      outwardNormal: Vec3,
      material: Int,
      t: Double
  ): HitRecord =
    val frontFace = (ray.direction dot outwardNormal) < 0.0
    val normal    = if frontFace then outwardNormal else -outwardNormal

    new HitRecord(point, normal, material, t, frontFace)

trait Hittable:
  def hit(ray: Ray, tMin: Double, tMax: Double): Option[HitRecord]
