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

  def getVersionString[A: ClassTag]: String ={
    val cls = classTag[A].runtimeClass
    val pkg = cls.getPackage
    val jar = cls.getProtectionDomain.getCodeSource.getLocation
    s"$pkg $jar"
  }

  def getVersionStrings: (String, String) = (getVersionString[DateTime], getVersionString[SpecificDatumReader[_]])

  val conf = new SparkConf()
  val sc = new SparkContext(conf)

  val log = LoggerFactory.getLogger(SparkDependencyHell.getClass)
  log.error(s"Driver Version: $getVersionStrings")

  val rdd = sc.parallelize(List(1))
  val executorVersion = rdd.map(_ => getVersionStrings).collect.head

  log.error(s"Executor Version: $executorVersion")

  sc.stop()
}
