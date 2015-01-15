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

DistAlgo:
1. Copy and extract the zipped file to desktop.
2. Go to DistAlgo src folder and copy the config files from config folder and rename the config file to “config.ini”
3. Execute the following command to compile the chainreplication.da source code:
			python3.4 -m da.compiler chainreplication.da 
4. Run the diet algo code using the following command.
			python3.4 -m da -f chainreplication.da
5. Logs will be generated in the same path with chainreplication.da.log

Main Files:

Java:
1. Async_Server\src\com\sbu\async\server\Server.java
2. Async_Server\src\com\sbu\async\server\ProcessSpawn.java
3. Async_Client\src\com\sbu\async\Client.java
4. Async_Client\src\com\sbu\async\ClientSpawn.java
5. Async_Master\src\com\sbu\master\Master.java
6. Async_Master\src\com\sbu\master\MasterSpawn.java

DistAlgo:
1. src/chainreplication.da
2. config/config.ini

Limitations

DisAlgo
	1. config file should be in same folder where chainreplication.da is copied and with the filename as “config.ini”

Contributions:
Mehak Mehta:
Java implementation

Shashi Ranjan
Distalgo Implementation
