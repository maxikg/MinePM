# ElasticSearch Mappings

For best analytics performance you should create mappings. You can use curl for it:

 * `curl -XPUT http://localhost:9200/minepm_chunk_timing -d @chunk_timing.json`
 * `curl -XPUT http://localhost:9200/minepm_event_timing -d @event_timing.json`
 * `curl -XPUT http://localhost:9200/minepm_players -d @players.json`
 * `curl -XPUT http://localhost:9200/minepm_tps -d @tps.json`

Please note: Changing mappings after data is inserted is extremely difficult. See
https://www.elastic.co/blog/changing-mapping-with-zero-downtime for more information about this.
