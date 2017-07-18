scalaVersion := "2.11.11"

enablePlugins(ScalaNativePlugin)

lazy val skiaCompile = taskKey[File]("Build Skia with ninja")
lazy val glfwCompile = taskKey[File]("Build GLFW with cmake")

//target in skiaCompile := target.value / "native"

skiaCompile := {
  val log = streams.value.log
  log.info("Building Skia...")

  val skiaDir = baseDirectory.value / "third_party" / "skia"

  log.info("Syncing Skia dependencies...")
  var ec = Process(Seq("python", (skiaDir / "tools" / "git-sync-deps").getAbsolutePath)) ! log
  if (ec != 0) sys.error(s"Failed syncing skia deps. Exit code: $ec")

  val buildDir = (target.value / "skia-build" / "out" / "Debug")

  log.info("Generating Skia build files...")

  ec = Process(Seq(
    (skiaDir / "bin" / "gn").getAbsolutePath,
    "gen",
    buildDir.getAbsolutePath,
    s"--root=${skiaDir.getAbsolutePath}",
    "--args=is_debug=false is_component_build=true extra_cflags=[\"-DSKIA_C_DLL\"]"
  )) ! log
  if (ec != 0) sys.error(s"Failed generating skia build definition. Exit code: $ec")

  log.info("Compiling Skia...")
  ec = Process(Seq("ninja", "-C", buildDir.getAbsolutePath)) ! log
  if (ec != 0) sys.error(s"Failed building skia. Exit code: $ec")

  val lib = buildDir// / "libskia.so"
  log.success(s"Skia build complete, library generated at ${lib.getAbsolutePath}")
  lib
}

glfwCompile := {
  val log = streams.value.log
  val glfwDir = baseDirectory.value / "third_party" / "glfw"
  val buildDir = (target.value / "glfw-build")
  IO.createDirectory(buildDir)
  Process(Seq(
    "cmake",
    s"-DCMAKE_INSTALL_PREFIX:PATH=${buildDir.getAbsolutePath}",
    "-DCMAKE_BUILD_TYPE=Release",
    glfwDir.getAbsolutePath
  ), buildDir) ! log
  Process(Seq("make", "glfw"), buildDir) ! log
  buildDir / "src"
}

envVars in run += ("DYLD_LIBRARY_PATH", skiaCompile.value.getAbsolutePath)
nativeLinkingOptions += s"-L${skiaCompile.value}"
nativeLinkingOptions += s"-L${glfwCompile.value}"
nativeLinkingOptions ++= Seq(
  "-framework", "OpenGL",
  "-framework", "Cocoa",
  "-framework", "CoreVideo",
  "-framework", "IOKit"
)
