

To test this app in a browser
=============================

mvn jetty:run which will run the webapp on http://localhost:8080/hw

To create a user
================

curl -X POST -H "Content-Type: application/xml" -d '{validUserXmlGoesHere}' http://localhost:8080/hw/api/users

Where you supply some valid user XML like:
    <user>
      <dob>1965-05-01T00:00:00-04:00</dob>
      <email>blumberg@innitek.com</email>
      <firstName>Bill</firstName>
      <id>2</id>
      <lastName>Lumberg</lastName>
      <phone>555-444-4321</phone>
    </user>


To retrieve data on a user
==========================
curl http://localhost:8080/hw/api/users/{id} | xmllint --format -
where {id} is the numeric unique ID of the user in the system, e.g. 2


To debug this application
=========================
run debug.sh or:
1. MAVEN_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=y"
2. mvn jetty:run
then attache a remote debugger such as with a remote debugging profile (in Eclipse)