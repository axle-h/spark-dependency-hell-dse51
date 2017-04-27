# spark-dependency-hell-dse51
To demonstrate the dependency hell issues we've had with DSE 5.1.
To run:

    mvn package
    dse spark-submit --deploy-mode client --class axh.SparkDependencyHell --name spark-dependency-hell spark-dependency-hell-dse51-1.0-SNAPSHOT.jar
	
I would expect to see:

    ERROR 2017-04-27 08:39:49 axh.SparkDependencyHell$: Driver Version: (package org.joda.time, Joda-Time, version 2.9 file:/usr/share/dse/spark/lib/joda-time-2.9.3.jar,package org.apache.avro.specific, Apache Avro, version 1.7.7 file:/usr/share/dse/spark/lib/avro-1.7.7.jar)
    ERROR 2017-04-27 08:39:52 axh.SparkDependencyHell$: Executor Version: (package org.joda.time, Joda-Time, version 2.9 file:/usr/share/dse/spark/lib/joda-time-2.9.3.jar,package org.apache.avro.specific, Apache Avro, version 1.7.7 file:/usr/share/dse/spark/lib/avro-1.7.7.jar)

But instead I see:

    ERROR 2017-04-27 08:39:49 axh.SparkDependencyHell$: Driver Version: (package org.joda.time, Joda-Time, version 2.9 file:/usr/share/dse/spark/lib/joda-time-2.9.3.jar,package org.apache.avro.specific, Apache Avro, version 1.7.7 file:/usr/share/dse/spark/lib/avro-1.7.7.jar)
    ERROR 2017-04-27 08:39:52 axh.SparkDependencyHell$: Executor Version: (package org.joda.time, Joda-Time, version 1.6 file:/usr/share/dse/cassandra/lib/joda-time-1.6.2.jar,package org.apache.avro.specific, Apache Avro, version 1.7.4 file:/usr/share/dse/hadoop2-client/lib/avro-1.7.4.jar)
