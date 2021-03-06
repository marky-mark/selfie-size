name := "selfie-size"

version := "1.0"

scalaVersion := "2.11.7"

scalacOptions ++= Seq(
  "-deprecation",     // Emit warning and location for usages of deprecated APIs.
  "-feature",         // Emit warning and location for usages of features that should be imported explicitly.
  "-unchecked",       // Enable additional warnings where generated code depends on assumptions.
  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xlint",           // Enable recommended additional warnings.
  "-Xcheckinit",
  "-Ywarn-dead-code"  // Warn when dead code is identified.
)

lazy val root = (project in file(".")).enablePlugins(PlayScala,DockerPlugin)

val javacvVersion = "0.11"

val javacppVersion = "0.11"

classpathTypes += "maven-plugin"

val platform = {
  // Determine platform name using code similar to javacpp
  // com.googlecode.javacpp.Loader.java line 60-84
  val jvmName = System.getProperty("java.vm.name").toLowerCase
  var osName = System.getProperty("os.name").toLowerCase
  var osArch = System.getProperty("os.arch").toLowerCase
  if (jvmName.startsWith("dalvik") && osName.startsWith("linux")) {
    osName = "android"
  } else if (jvmName.startsWith("robovm") && osName.startsWith("darwin")) {
    osName = "ios"
    osArch = "arm"
  } else if (osName.startsWith("mac os x")) {
    osName = "macosx"
  } else {
    val spaceIndex = osName.indexOf(' ')
    if (spaceIndex > 0) {
      osName = osName.substring(0, spaceIndex)
    }
  }
  if (osArch.equals("i386") || osArch.equals("i486") || osArch.equals("i586") || osArch.equals("i686")) {
    osArch = "x86"
  } else if (osArch.equals("amd64") || osArch.equals("x86-64") || osArch.equals("x64")) {
    osArch = "x86_64"
  } else if (osArch.startsWith("arm")) {
    osArch = "arm"
  }
  val platformName = osName + "-" + osArch
  println("platform: " + platformName)
  platformName
}

libraryDependencies ++= Seq(
  "org.bytedeco" % "javacv" % javacvVersion excludeAll(
    ExclusionRule(organization = "org.bytedeco.javacpp-presets"),
    ExclusionRule(organization = "org.bytedeco.javacpp")
    ),
  "org.bytedeco.javacpp-presets" % "opencv"  % ("2.4.11-" + javacppVersion) classifier "",
  "org.bytedeco.javacpp-presets" % "opencv"  % ("2.4.11-" + javacppVersion) classifier platform,
  "org.bytedeco"                 % "javacpp" % javacppVersion,
  jdbc,
  cache,
  ws,
  "org.scalaz" %% "scalaz-core" % "7.1.3",
  "org.webjars" % "swagger-ui" % "2.1.8-M1",
  "org.scalatest" %% "scalatest" % "2.2.4" % Test,
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % Test,
  "org.scalatestplus" %% "play" % "1.4.0-M3" % Test
)

javaOptions += "-Xmx1G"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

routesImport := Seq(
  "binders._"
)