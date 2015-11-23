# MinePM [![Build Status](https://travis-ci.org/maxikg/MinePM.svg)](https://travis-ci.org/maxikg/MinePM)

Application Performance Management (APM) for Minecraft servers.

The most server operators who have worked for a server with ~50 or more concurrent players know the pain of performance
issues. They are hard to reproduce and harder to fix. This software tries to address this issue. It continually saves
performance metrics and helps to isolate errors to its root cause.

It uses [ElasticSearch](https://www.elastic.co/products/elasticsearch) for saving data. To visualize the collected data
you should take a look at [Kibana](https://www.elastic.co/products/kibana).

## Example

ToDo: Include some screenshots

## Usage

To use this software you need to run Java 8.

 1. Download the archive or build the project
 2. Extract the `minepm` directory inside the archive into your Minecraft server root directory
 3. Configure `minepm/config.properties`
 4. Start Minecraft Vanilla/Bukkit/Spigot: `java -javaagent:minepm/minepm.jar -jar <your-server-jar>`

Make sure that you include `-javaagent:minepm/minepm.jar` in command line arguments.
