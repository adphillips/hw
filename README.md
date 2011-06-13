First thing before you can do anything
======================================

Setup a new database called 'hw' on your mysql (or other) RDBMS and modify
src/main/resources/applicationContext.xml accordingly.  Next create
the USERS table like so:

    CREATE TABLE `USERS` (
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `dob` date DEFAULT NULL,
      `email` varchar(50) DEFAULT NULL,
      `firstName` varchar(20) DEFAULT NULL,
      `lastName` varchar(20) DEFAULT NULL,
      `phone` varchar(12) DEFAULT NULL,
      PRIMARY KEY (`id`)
    );

To test this app in a browser
=============================

mvn jetty:run which will run the webapp on http://localhost:8080/hw

To create a user
================

curl -X POST -H "Content-Type: application/xml" -d '{validUserXmlGoesHere}' http://localhost:8080/hw/api/users

Where you supply some valid user XML like:

    <user>
      <firstName>Bill</firstName>
      <lastName>Lumberg</lastName>
      <phone>555-444-4321</phone>
      <dob>1965-05-01</dob>
      <email>blumberg@innitek.com</email>
    </user>


To retrieve data on a user
==========================
curl http://localhost:8080/hw/api/users/{id} | xmllint --format -

where {id} is the numeric unique ID of the user in the system, e.g. 2


To debug this application
=========================
run debug.sh or..

1. MAVEN_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=y"
2. mvn jetty:run

then attach a remote debugger such as with a remote debugging profile (in Eclipse)

To build and test
=================
This is a typical war maven project, so mvn package and mvn test