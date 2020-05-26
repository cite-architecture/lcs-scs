name := "seqcomp library"

crossScalaVersions := Seq("2.12.4") //Seq("2.10.6","2.11.8", "2.12.4")


lazy val root = project.in(file(".")).
    aggregate(crossedJVM, crossedJS).
    settings(
      publish := {},
      publishLocal := {}
    )

lazy val crossed = crossProject(JSPlatform, JVMPlatform).in(file(".")).
    settings(
      name := "seqcomp",
      organization := "edu.holycross.shot",
      version := "2.0.0",
      licenses += ("GPL-3.0",url("https://opensource.org/licenses/gpl-3.0.html")),
      libraryDependencies ++= Seq(
        "org.scalatest" %%% "scalatest" % "3.1.2" % "test",
        "org.wvlet.airframe" %% "airframe-log" % "20.5.2"

      )
    ).
    jvmSettings(
      libraryDependencies += "org.scala-js" %% "scalajs-stubs" % "1.0.0" % "provided"
    ).
    jsSettings(
        scalaJSUseMainModuleInitializer := true,
      //skip in packageJSDependencies := false,
      //persistLauncher in Compile := true,
      //persistLauncher in Test := false
    )

lazy val crossedJVM = crossed.jvm
lazy val crossedJS = crossed.js.enablePlugins(ScalaJSPlugin)

lazy val docs = project       // new documentation project
  .in(file("docs-build")) // important: it must not be docs/
  .dependsOn(crossedJVM)
  .enablePlugins(MdocPlugin)
  .settings(
    mdocIn := file("guide"),
    mdocOut := file("mdocs")
  )
