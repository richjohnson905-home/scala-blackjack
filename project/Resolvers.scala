import sbt._

object Resolvers {
  val resolvers = Seq(
    Resolver.sonatypeRepo("public"),
    Resolver.sonatypeRepo("releases"),
    "Sonatype Nexus Repository Manager" at "https://nexus.platforms.engineering/repository/tps/",
    "confluent" at "http://packages.confluent.io/maven/",
    Resolver.bintrayRepo("lonelyplanet", "maven")
  )
}