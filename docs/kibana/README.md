# Kibana Exports

This directory contains various exports of [Kibana](https://www.elastic.co/products/kibana) dashboards, searches and
visualizations.

## How to import

You can import each file in the Kibana frontend. Open the settings page, switch to "objects" and click on the "Import"
button. Now select the json document you want to import. After it is uploaded it should be listed under one of the tabs.

## Index

## Dashboards

 * [example_001.json](dashboards/example_001.json) - The dashboard shown in [this image](../img/example_001.png).
   Contains the dashboard and all required visualizations.

## Searches

Currently nothing.

### Visualizations

 * [chunks-per-dimension.json](visualizations/chunks-per-dimension.json) - A pie chart visualizing the loaded chunks of
   each dimension.
 * [tps.json](visualizations/tps.json) - A line chart displaying the ticks per second of the server(s).
 * [tps.json](visualizations/tps-average.json) - A table displaying the ticks per second of the servers, starting with
   lowest.