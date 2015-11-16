A highly available and fault tolerant framework in Java/Distalgo for systems based on the paper "Replication Techniques for Availability" by Robbert van Renesse and Rachid Guerraou (2010). Language/Technology: Java

Instructions to Compile & Run:
Java:
1. Copy and extract the zipped file to desktop. 
2. We have provided Ant build.xml files for both Async_Server, Async_Client and Async_master in their respective folders as client.xml and server.xml. We have also provided jar files for both client and server in Jars folder.
3. The user needs to install Ant and Java on to system to build the project. The path to these distributions should be on the system classpath.
4. There are two ways this project can be run:
	a.	Ant build: The user can build the project using the Ant build file provided with both Async_Server and Async_Client projects.
	b.	Alternatively user can run the Server.jar and Client.jar files directly by using the following command
			java –jar Server.jar config.properties 
			java –jar Client.jar config.properties
			java –jar Master.jar config.properties
5. Log files will be made at /Logs/Server.log & /Logs/Client.log & /Logs/Master.log

Main Files:

Java:
1. Async_Server\src\com\sbu\async\server\Server.java
2. Async_Server\src\com\sbu\async\server\ProcessSpawn.java
3. Async_Client\src\com\sbu\async\Client.java
4. Async_Client\src\com\sbu\async\ClientSpawn.java
5. Async_Master\src\com\sbu\master\Master.java
6. Async_Master\src\com\sbu\master\MasterSpawn.java

