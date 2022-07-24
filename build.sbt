ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.11.12"
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "org.postgresql" % "postgresql" % "42.3.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "com.github.tminglei" %% "slick-pg" % "0.20.3",
  "com.github.tminglei" %% "slick-pg_play-json" % "0.20.3",

  "org.apache.spark" %% "spark-core" % "2.4.8",
  "org.apache.spark" %% "spark-sql" % "2.4.8",
  "org.apache.spark" %% "spark-hive" % "2.4.8",
  "org.apache.poi" % "poi" % "5.2.0",
  "org.apache.poi" % "poi-ooxml" % "5.2.0",
  "org.apache.poi" % "poi-ooxml-lite" % "5.2.0",
  "com.github.mrpowers" %% "spark-daria" % "0.39.0",
  "org.vegas-viz" %% "vegas" % "0.3.11",
  "org.vegas-viz" %% "vegas-spark" % "0.3.11"

)


lazy val root = (project in file("."))
  .settings(
    name := "Scala_Sbt_Test"
  )

// https://mvnrepository.com/artifact/org.scala-lang.modules/scala-java8-compat
libraryDependencies += "org.scala-lang.modules" %% "scala-java8-compat" % "1.0.2"

// https://mvnrepository.com/artifact/org.scala-lang.modules/scala-collection-compat
libraryDependencies += "org.scala-lang.modules" %% "scala-collection-compat" % "2.6.0"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.7"

// https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-scala
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.13.2"













