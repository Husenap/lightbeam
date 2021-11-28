package lightbeam.math

class Ray(val origin: Vec3, val direction: Vec3):
  def at(t: Double) = origin + direction * t
