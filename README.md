# MinePM [![Build Status](https://travis-ci.org/maxikg/MinePM.svg)](https://travis-ci.org/maxikg/MinePM)

Application Performance Management (APM) for Minecraft servers.

The most server operators who have worked for a server with ~50 or more concurrent players know the pain of performance
issues. They are hard to reproduce and harder to fix. This software tries to address this issue. It continually saves
performance metrics and helps to isolate errors to its root cause.

It uses [ZeroMQ](http://zeromq.org/) for distributing data. You can use it for transporting it to a
[Logstash](https://www.elastic.co/products/elasticsearch) server which does insert it into
[ElasticSearch](https://www.elastic.co/products/elasticsearch). From there you can use
[Kibana](https://www.elastic.co/products/kibana) to visualize/analyze collected data. For an example configuration
file see: [docs/logstash/config.conf](/docs/logstash/config.conf).

## Example

![Kibana example #001](/docs/img/example_001.png "Kibana example #001")

This screenshot show ~15 minutes gameplay. You can [import](/docs/kibana/README.md) this dashboard from the
[json file](/docs/kibana/dashboards/example_001.json).

## Usage

To use this software you need to run Java 8.

 1. Download the archive or build the project
 2. Extract the `minepm` directory inside the archive into your Minecraft server root directory
 3. Configure `minepm/config.properties`
 4. Create mappings (see [here](/docs/elasticsearch/mappings/README.md))
 5. Start Minecraft Vanilla/Bukkit/Spigot: `java -javaagent:minepm/minepm.jar -jar <your-server-jar>`

Make sure that you include `-javaagent:minepm/minepm.jar` in command line arguments.
