#!/bin/bash
JAVA=java
BASEDIR="$( cd -P "$( dirname "$0" )/.." && pwd )"
if [ -f $BASEDIR/config/logback.xml ]; then
	LOGCONFIG=$BASEDIR/config/logback.xml
else
	LOGCONFIG=$BASEDIR/config/logback.default.xml
fi
if [ -f $BASEDIR/config/logback-console.xml ]; then
	LOGCONFIG_CONSOLE=$BASEDIR/config/logback-console.xml
else
	LOGCONFIG_CONSOLE=$BASEDIR/config/logback-console.default.xml
fi
CONTROL_PORT=28111
CONTROL_HOST=localhost
VERSION="${project.version}"
CLASSPATH="lib/*:lib.local/*"
JAVA_OPTS="-Dlogback.configurationFile=$LOGCONFIG -Dcontrol.host=$CONTROL_HOST -Dcontrol.port=$CONTROL_PORT"

if [ -x $BASEDIR/bin/local.sh ]; then
	. $BASEDIR/bin/local.sh
fi
if [ "$2" == "" ]; then
	CONFIG="$BASEDIR/config/config.xml"
else
	CONFIG="$BASEDIR/config/$2"
fi

pushd $BASEDIR > /dev/null

if [ ! -e logs ]; then
        mkdir logs
fi

case "$1" in
	start)
		nohup $JAVA -cp "$CLASSPATH" $JAVA_OPTS org.metricssampler.Start $CONFIG  > logs/console.out 2>&1 &
		echo "Started with pid $!"
		;;
	stop)
		$JAVA -cp "$CLASSPATH" $JAVA_OPTS -Dlogback.configurationFile=$LOGCONFIG_CONSOLE org.metricssampler.Stop
		echo "Stopped"
		;;
	restart)
		popd > /dev/null
		$0 stop
		sleep 2
		$0 start $2
		exit
		;;
	status)
		$JAVA -cp "$CLASSPATH" $JAVA_OPTS -Dlogback.configurationFile=$LOGCONFIG_CONSOLE org.metricssampler.Status
		echo $MSG
		RETURN_CODE=$(echo "$MSG" | grep 'Stopped' | wc -l)
		exit $RETURN_CODE
 		;;
	check)
		$JAVA -cp "$CLASSPATH" $JAVA_OPTS -Dlogback.configurationFile=$LOGCONFIG_CONSOLE org.metricssampler.Check $CONFIG
		;;
	test)
		$JAVA -cp "$CLASSPATH" $JAVA_OPTS -Dlogback.configurationFile=$LOGCONFIG_CONSOLE org.metricssampler.Test $CONFIG
		;;
	metadata)
		$JAVA -cp "$CLASSPATH" $JAVA_OPTS -Dlogback.configurationFile=$LOGCONFIG_CONSOLE org.metricssampler.Metadata $CONFIG
		;;
	*)
		cat <<EOF
metrics-sampler version $VERSION
Usage: `basename $0` [start|stop|restart|status|check|test|metadata] [<config.xml>]

start     Starts the application as a daemon in the background.
stop      Stops a running daemon (if any).
status    Checks whether the daemon is running or no.
restart   Stops the running daemon (if any) and then starts it.
check     Goes through all samplers and checks whether each rule matches at least one metric. Everything is logged to STDOUT.
test      Calls all enabled samplers once and exits.
metadata  Goes through all samplers and outputs the metadata of their readers. Use it to see what metrics are available and build
          your rules based on that.
EOF
		;;
esac
popd > /dev/null
