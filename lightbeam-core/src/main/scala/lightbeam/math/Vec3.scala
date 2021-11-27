package lightbeam.math

/** 3-dimensional vector class that supports both mutable and immutable operations.
 *  ### Example Usage
 *  ```
 *  val v1 = Vec3(1, 2, 3)
 *  val v2 = Vec3.One
 *  v1 += v2
 *
 *  val forward = Vec3.Right cross Vec3.Up
 *  ```
 */
case class Vec3 private (
    var x: Double,
    var y: Double,
    var z: Double
):
  /** Immutable component-wise commutative addition between two vectors. */
  infix def +(other: Vec3): Vec3 = Vec3(x + other.x, y + other.y, z + other.z)

  /** Immutable addition between a vector and a scalar. */
  infix def +(scalar: Double): Vec3 = Vec3(x + scalar, y + scalar, z + scalar)

  /** Mutable component-wise commutative addition between a vector and a scalar. */
  def +=(other: Vec3): Unit =
    x += other.x
    y += other.y
    z += other.z

  /** Mutable addition between a vector and a scalar. */
  def +=(scalar: Double): Unit = this += Vec3(scalar, scalar, scalar)

  /** Immutable component-wise subtraction between two vectors. */
  infix def -(other: Vec3): Vec3 = Vec3(x - other.x, y - other.y, z - other.z)

  /** Immutable subtraction between a vector and a scalar. */
  infix def -(scalar: Double): Vec3 = Vec3(x - scalar, y - scalar, z - scalar)

  /** Immutable unary negation of a vector. */
  def unary_- : Vec3 = Vec3(-x, -y, -z)

  /** Mutable component-wise subtraction between two vectors. */
  def -=(other: Vec3): Unit =
    x -= other.x
    y -= other.y
    z -= other.z

  /** Mutable subtraction between a vector and a scalar. */
  def -=(scalar: Double): Unit = this -= Vec3(scalar, scalar, scalar)

  /** Immutable multiplication between a vector and a scalar. */
  infix def *(scalar: Double): Vec3 = Vec3(x * scalar, y * scalar, z * scalar)

  /** Mutable multiplication between a vector and a scalar. */
  def *=(scalar: Double): Unit =
    x *= scalar
    y *= scalar
    z *= scalar

  /** Immutable division between a vector and a scalar. */
  infix def /(scalar: Double): Vec3 = Vec3(x / scalar, y / scalar, z / scalar)

  /** Mutable division between a vector and a scalar. */
  def /=(scalar: Double): Unit =
    x /= scalar
    y /= scalar
    z /= scalar

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
  def magnitudeSquared = dot(this)

  /** Calculates the magnitude of a vector. */
  def magnitude = math.sqrt(magnitudeSquared)

  /** Alias for [[magnitude]]. */
  def length = magnitude

  /** Normalize a vector in-place. */
  def normalize(): Unit = this /= length

  /** Immutable normalization of a vector. */
  def normalized: Vec3 = this / length

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
  def Zero: Vec3 = apply(0.0)

  /** Default one vector, defined as (1,1,1). */
  def One: Vec3 = apply(1.0)

  /** Default right vector, pointing in the positive x-axis. */
  def Right: Vec3 = apply(1.0, 0.0, 0.0)

  /** Default up vector, pointing in the positive y-axis. */
  def Up: Vec3 = apply(0.0, 1.0, 0.0)

  /** Default forward vector, pointing in the positive z-axis. */
  def Forward: Vec3 = apply(0.0, 0.0, 1.0)

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
