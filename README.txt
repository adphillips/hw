To debug this application: 
1. export MAVEN_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=y"
2. mvn jetty:run
3. then debug with a remote debugging profile (in Eclipse)
