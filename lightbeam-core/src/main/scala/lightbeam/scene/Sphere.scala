package lightbeam.scene

import lightbeam.math.Ray
import lightbeam.math.Vec3

class Sphere(val center: Vec3, val radius: Double, material: Material)
    extends Hittable:

  override def hit(ray: Ray, tMin: Double, tMax: Double): Option[HitRecord] =
    val oc = ray.origin - center
    val a  = ray.direction.magnitudeSquared
    val b2 = oc dot ray.direction
    val c  = (oc dot oc) - radius * radius

    val discriminant = b2 * b2 - a * c

    if discriminant > 0.0 then
      val root  = math.sqrt(discriminant)
      val root1 = (-b2 - root) / a
      if root1 < tMax && root1 > tMin then
        val point = ray.at(root1)
        Some(HitRecord(point, ray, (point - center) / radius, material, root1))
      else
        val root2 = (-b2 + root) / a
        if root2 < tMax && root2 > tMin then
          val point = ray.at(root2)
          Some(
            HitRecord(point, ray, (point - center) / radius, material, root2)
          )
        else None
    else None
