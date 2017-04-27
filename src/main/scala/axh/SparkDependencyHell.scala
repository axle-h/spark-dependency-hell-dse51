package axh

import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.LoggerFactory
import org.apache.avro.specific.SpecificDatumReader
import org.joda.time.DateTime

import scala.reflect._

/**
  * Created by Alex.Haslehurst on 26/04/2017.
  */
object SparkDependencyHell extends App {

  val conf = new SparkConf()
  val sc = new SparkContext(conf)

  def tryRunOnExecutor[A: ClassTag](name: String)(f: Unit => A): Unit ={
    val log = LoggerFactory.getLogger(SparkDependencyHell.getClass)

    // Run on driver.
    log.error(s"[Driver] $name: ${f()}")

    // Run on executor and collect result to log on driver.
    sc.parallelize(List(1)).map { _ =>
      try {
        f()
      } catch {
        case e: Throwable => e
      }
    }.collect.head match {
      case e: Throwable => log.error(s"[Executor] Failed to run $name", e)
      case result: A => log.error(s"[Executor] $name: $result")
    }
  }

  def getVersionString[A: ClassTag]: String ={
    val cls = classTag[A].runtimeClass
    val pkg = cls.getPackage
    val jar = cls.getProtectionDomain.getCodeSource.getLocation
    s"$pkg $jar"
  }

  // Comparison of versions.
  tryRunOnExecutor("Versions") { _ => (getVersionString[DateTime], getVersionString[SpecificDatumReader[_]]) }

  // Try to use joda-time method introduced in 2.0
  tryRunOnExecutor("joda-time method introduced in 2.0") { _ => DateTime.now }

  sc.stop()
}
