package axh

import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.LoggerFactory

/**
  * Created by Alex.Haslehurst on 26/04/2017.
  */
object SparkDependencyHell extends App {

  def getAvroVersionString: String ={
    val cls = classOf[org.apache.avro.specific.SpecificDatumReader[_]]
    val pkg = cls.getPackage
    val jar = cls.getProtectionDomain.getCodeSource.getLocation
    s"$pkg $jar"
  }

  val conf = new SparkConf()
  val sc = new SparkContext(conf)

  val log = LoggerFactory.getLogger(SparkDependencyHell.getClass)
  log.error(s"Driver Version: $getAvroVersionString")

  val rdd = sc.parallelize(List(1))
  val executorVersion = rdd.map(_ => getAvroVersionString).collect.head

  log.error(s"Executor Version: $executorVersion")

  sc.stop()
}
