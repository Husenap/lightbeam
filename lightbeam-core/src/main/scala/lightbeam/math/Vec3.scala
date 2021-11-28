package lightbeam.math

/** 3-dimensional vector class that supports immutable operations.
 *  ### Example Usage
 *  ```
 *  val v1 = Vec3.Right
 *  val v2 = Vec3.Up
 *  val forward = v1 cross v2
 *  ```
 */
case class Vec3 private (
    val x: Double,
    val y: Double,
    val z: Double
):
  def r = x
  def g = y
  def b = z

  /** Immutable component-wise commutative addition between two vectors. */
  infix def +(other: Vec3): Vec3 = Vec3(x + other.x, y + other.y, z + other.z)

  /** Immutable addition between a vector and a scalar. */
  infix def +(scalar: Double): Vec3 = Vec3(x + scalar, y + scalar, z + scalar)

  /** Immutable component-wise subtraction between two vectors. */
  infix def -(other: Vec3): Vec3 = Vec3(x - other.x, y - other.y, z - other.z)

  /** Immutable subtraction between a vector and a scalar. */
  infix def -(scalar: Double): Vec3 = Vec3(x - scalar, y - scalar, z - scalar)

  /** Immutable unary negation of a vector. */
  lazy val unary_- : Vec3 = Vec3(-x, -y, -z)

  /** Immutable component-wise multiplication between two vectors. */
  infix def *(other: Vec3): Vec3 = Vec3(x * other.x, y * other.y, z * other.z)

  /** Immutable multiplication between a vector and a scalar. */
  infix def *(scalar: Double): Vec3 = Vec3(x * scalar, y * scalar, z * scalar)

  /** Immutable division between a vector and a scalar. */
  infix def /(scalar: Double): Vec3 = Vec3(x / scalar, y / scalar, z / scalar)

  /** Calculates the dot product of two vectors. */
  infix def dot(other: Vec3): Double =
    x * other.x + y * other.y + z * other.z

  /** Calculates the cross product of two vectors. */
  infix def cross(other: Vec3): Vec3 = Vec3(
    y * other.z - z * other.y,
    z * other.x - x * other.z,
    x * other.y - y * other.x
  )

  /** Calculates the squared magnitude of a vector. */
  lazy val magnitudeSquared = dot(this)

  /** Calculates the magnitude of a vector. */
  lazy val magnitude = math.sqrt(magnitudeSquared)

  /** Alias for [[magnitude]]. */
  def length = magnitude

  /** Immutable normalization of a vector. */
  lazy val normalized: Vec3 = this / length

  /** Reflect a vector around a normal.
   *  @param normal Normal to reflect this vector around.
   *  @return A new vector that is reflected.
   */
  infix def reflect(normal: Vec3): Vec3 = this - 2 * (this dot normal) * normal

  /** Refract a vector through a surface.
   *  @param normal Normal of the surface.
   *  @param ior Index Of Refraction.
   *  @return A new vector that is refracted through the surface.
   */
  def refract(normal: Vec3, ior: Double): Vec3 =
    val cosTheta       = (-this) dot normal
    val rPerpendicular = ior * (this + cosTheta * normal)
    val rParallel      =
      -math.sqrt(math.abs(1.0 - rPerpendicular.magnitudeSquared)) * normal
    rPerpendicular + rParallel

case object Vec3:
  /** Constructs a new vector.
   *  @param x X component of the vector.
   *  @param y Y component of the vector.
   *  @param z Z component of the vector.
   */
  def apply(x: Double, y: Double, z: Double): Vec3 = new Vec3(x, y, z)

  /** Constructs a new vector from a single scalar.
   *  @param scalar Scalar value to apply to all components.
   */
  def apply(scalar: Double): Vec3 = new Vec3(scalar, scalar, scalar)

  /** Default zero vector, defined as (0,0,0). */
  final val Zero: Vec3 = apply(0.0)

  /** Default one vector, defined as (1,1,1). */
  final val One: Vec3 = apply(1.0)

  /** Default right vector, pointing in the positive x-axis. */
  final val Right: Vec3 = apply(1.0, 0.0, 0.0)

  /** Default up vector, pointing in the positive y-axis. */
  final val Up: Vec3 = apply(0.0, 1.0, 0.0)

  /** Default forward vector, pointing in the positive z-axis. */
  final val Forward: Vec3 = apply(0.0, 0.0, 1.0)

  /** Generates a random direction vector. */
  def randomDirection: Vec3 =
    val a = util.Random.nextDouble * 2.0 * math.Pi
    val z = util.Random.nextDouble * 2.0 - 1.0
    val r = math.sqrt(1.0 - z * z)
    Vec3(r * math.cos(a), r * math.sin(a), z)

  /** Generates a uniformly distributed random vector in the unit disc. */
  def randomInUnitDisc: Vec3 =
    val a = util.Random.nextDouble
    val r = math.pow(util.Random.nextDouble, 1.0 / 3.0)
    Vec3(r * math.cos(a), r * math.sin(a), 0.0)

  /** Generates a random color with values [0,1). */
  def randomColor: Vec3 =
    Vec3(
      util.Random.nextDouble,
      util.Random.nextDouble,
      util.Random.nextDouble
    )

  def mix(a: Vec3, b: Vec3, t: Double): Vec3 = a * (1.0 - t) + b * t

  def clamp(v: Vec3, min: Double, max: Double): Vec3 = Vec3(
    math.min(math.max(v.x, min), max),
    math.min(math.max(v.y, min), max),
    math.min(math.max(v.z, min), max)
  )

  def pow(v: Vec3, exponent: Double): Vec3 = Vec3(
    math.pow(v.x, exponent),
    math.pow(v.y, exponent),
    math.pow(v.z, exponent)
  )

  extension (scalar: Double)
    /** Extension method that allows additions with vectors on the right-hand side.
     *  ### Example Usage
     *  ```
     *  5 + Vec3(1, 2, 3)
     *  ```
     */
    def +(v: Vec3) = v + scalar

    /** Extension method that allows subtractions with vectors on the right-hand side.
     *  ### Example Usage
     *  ```
     *  5 - Vec3(1, 2, 3)
     *  ```
     */
    def -(v: Vec3) = Vec3(scalar) - v

    /** Extension method that allows multiplications with vectors on the right-hand side.
     *  ### Example Usage
     *  ```
     *  5 * Vec3(1, 2, 3)
     *  ```
     */
    def *(v: Vec3) = v * scalar
