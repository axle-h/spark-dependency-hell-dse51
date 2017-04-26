# spark-dependency-hell-dse51
To demonstrate the dependency hell issues we've had with DSE 5.1.
To run:

    mvn package
    dse spark-submit --deploy-mode client --class axh.SparkDependencyHell --name spark-dependency-hell spark-dependency-hell-dse51-1.0-SNAPSHOT.jar
	
I would expect to see:

    ERROR 2017-04-26 11:39:51 axh.SparkDependencyHell$: Driver Version: package org.apache.avro.specific, Apache Avro, version 1.7.7 file:/usr/share/dse/spark/lib/avro-1.7.7.jar
    ERROR 2017-04-26 11:39:54 axh.SparkDependencyHell$: Executor Version: package org.apache.avro.specific, Apache Avro, version 1.7.7 file:/usr/share/dse/spark/lib/avro-1.7.7.jar

But instead I see:

    ERROR 2017-04-26 11:39:51 axh.SparkDependencyHell$: Driver Version: package org.apache.avro.specific, Apache Avro, version 1.7.7 file:/usr/share/dse/spark/lib/avro-1.7.7.jar
    ERROR 2017-04-26 11:39:54 axh.SparkDependencyHell$: Executor Version: package org.apache.avro.specific, Apache Avro, version 1.7.4 file:/usr/share/dse/hadoop2-client/lib/avro-1.7.4.jar
