import lightbeam.*
import lightbeam.math.*
import lightbeam.scene.*
@main
def main =
  val materials = Vector(
    Lambertian(Colors.Pantone812C),
    Dielectric(1.5),
    Lambertian(Colors.Pantone636C),
    Metal(Colors.Pantone712C, 0.0),
    Lambertian(Colors.White),
    Lambertian(Colors.Pantone712C),
    Lambertian(Colors.Pantone636C),
    Lambertian(1.0 - Colors.Pantone712C)
  )

  val world        = HittableList()
  val lightSources = HittableList()

  val wallRadius = 1000.0
  // floor
  world.add(Sphere(-Vec3.Up * (wallRadius + 1), wallRadius, materials(7)))
  // ceiling
  world.add(Sphere(Vec3.Up * (wallRadius + 1), wallRadius, materials(0)))
  // left-right walls
  world.add(Sphere(-Vec3.Right * (wallRadius + 1), wallRadius, materials(6)))
  world.add(Sphere(Vec3.Right * (wallRadius + 1), wallRadius, materials(6)))
  // front-back walls
  world.add(Sphere(-Vec3.Forward * (wallRadius + 1), wallRadius, materials(5)))
  world.add(Sphere(Vec3.Forward * (wallRadius + 1), wallRadius, materials(5)))

  world.add(Sphere(Vec3(0.5, -0.6, -0.5), 0.4, materials(0)))
  world.add(Sphere(Vec3(-0.5, -0.6, 0.5), 0.4, materials(3)))

  for
    i <- Vector(-0.5, 0.5)
    j <- Vector(-0.5, 0.5)
  do
    world.add(Sphere(Vec3(i, 1.0, j), -0.21, materials(1)))
    world.add(Sphere(Vec3(i, 1.0, j), 0.22, materials(1)))
    lightSources.add(Sphere(Vec3(i, 1.0, j), 0.15, materials(4)))

  val lightbeam = Lightbeam(
    Settings(
      maxBounces = 8,
      samplesPerPixel = 512
    ),
    Scene(world, lightSources)
  )

  lightbeam.render("output.png")
