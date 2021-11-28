package lightbeam.scene

import lightbeam.math.Ray
import scala.collection.mutable.ArrayBuffer

class HittableList extends Hittable:
  private val objects = ArrayBuffer.empty[Hittable]

  def clear(): Unit                 = objects.clear()
  def add(hittable: Hittable): Unit = objects.addOne(hittable)

  override def hit(ray: Ray, tMin: Double, tMax: Double): Option[HitRecord] =
    var closestHit: Option[HitRecord] = None
    var closestSoFar                  = tMax

    for hittable <- objects do
      val hit = hittable.hit(ray, tMin, closestSoFar)
      if hit.isDefined then
        closestHit = hit
        closestSoFar = hit.get.t

    closestHit
