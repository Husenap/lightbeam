package lightbeam.math

case class Vec3 private (
    var x: Double,
    var y: Double,
    var z: Double
):
  infix def +(other: Vec3): Vec3    = Vec3(x + other.x, y + other.y, z + other.z)
  infix def +(scalar: Double): Vec3 = Vec3(x + scalar, y + scalar, z + scalar)

  def +=(other: Vec3): Unit =
    x += other.x
    y += other.y
    z += other.z
  def +=(scalar: Double): Unit = this += Vec3(scalar, scalar, scalar)

  infix def -(other: Vec3): Vec3    = Vec3(x - other.x, y - other.y, z - other.z)
  infix def -(scalar: Double): Vec3 = Vec3(x - scalar, y - scalar, z - scalar)
  def unary_- : Vec3                = Vec3(-x, -y, -z)

  def -=(other: Vec3): Unit =
    x -= other.x
    y -= other.y
    z -= other.z
  def -=(scalar: Double): Unit = this -= Vec3(scalar, scalar, scalar)

  infix def *(scalar: Double): Vec3 = Vec3(x * scalar, y * scalar, z * scalar)
  def *=(scalar: Double): Unit =
    x *= scalar
    y *= scalar
    z *= scalar

  infix def /(scalar: Double): Vec3 = Vec3(x / scalar, y / scalar, z / scalar)
  def /=(scalar: Double): Unit =
    x /= scalar
    y /= scalar
    z /= scalar

  infix def dot(other: Vec3): Double =
    x * other.x + y * other.y + z * other.z

  infix def cross(other: Vec3): Vec3 = Vec3(
    y * other.z - z * other.y,
    z * other.x - x * other.z,
    x * other.y - y * other.x
  )

  def magnitudeSquared = dot(this)
  def magnitude        = math.sqrt(magnitudeSquared)
  def length           = magnitude

  def normalize(): Unit = this /= length
  def normalized: Vec3  = this / length

case object Vec3:
  def apply(x: Double, y: Double, z: Double): Vec3 = new Vec3(x, y, z)
  def apply(scalar: Double): Vec3                  = new Vec3(scalar, scalar, scalar)

  def Zero: Vec3    = apply(0.0)
  def One: Vec3     = apply(1.0)
  def Right: Vec3   = apply(1.0, 0.0, 0.0)
  def Up: Vec3      = apply(0.0, 1.0, 0.0)
  def Forward: Vec3 = apply(0.0, 0.0, 1.0)

  extension (scalar: Double)
    def +(v: Vec3) = v + scalar
    def -(v: Vec3) = Vec3(scalar) - v
    def *(v: Vec3) = v * scalar
