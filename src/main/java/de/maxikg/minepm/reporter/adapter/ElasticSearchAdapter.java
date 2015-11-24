package de.maxikg.minepm.reporter.adapter;

import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.PutMapping;
import org.aspectj.lang.Signature;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ElasticSearchAdapter implements ReportingAdapter {

    private static final String EVENT_TIMINGS_MAPPING =
            "{" +
            "  \"event_timings\": {" +
            "    \"properties\": {" +
            "      \"date\": {" +
            "        \"type\": \"date\"," +
            "        \"format\": \"epoch_millis\"" +
            "      }," +
            "      \"event_class\": {" +
            "        \"type\": \"string\"," +
            "        \"index\": \"not_analyzed\"" +
            "      }," +
            "      \"listener_signature\": {" +
            "        \"type\": \"string\"," +
            "        \"index\": \"not_analyzed\"" +
            "      }," +
            "      \"duration\": {" +
            "        \"type\": \"long\"" +
            "      }," +
            "      \"async\": {" +
            "        \"type\": \"boolean\"" +
            "      }" +
            "    }" +
            "  }" +
            "}";
    private static final String CHUNK_LOAD_TIMINGS_MAPPING =
            "{" +
                    "  \"chunk_load_timings\": {" +
                    "    \"properties\": {" +
                    "      \"date\": {" +
                    "        \"type\": \"date\"," +
                    "        \"format\": \"epoch_millis\"" +
                    "      }," +
                    "      \"world\": {" +
                    "        \"type\": \"string\"," +
                    "        \"index\": \"not_analyzed\"" +
                    "      }," +
                    "      \"x\": {" +
                    "        \"type\": \"integer\""+
                    "      }," +
                    "      \"z\": {" +
                    "        \"type\": \"integer\"" +
                    "      }," +
                    "      \"duration\": {" +
                    "        \"type\": \"long\"" +
                    "      }" +
                    "    }" +
                    "  }" +
                    "}";

    private final JestClient client;

    public ElasticSearchAdapter(JestClient client) {
        this.client = Objects.requireNonNull(client, "client must be not null.");
    }

    @Override
    public void init() throws Throwable {
        Map<String, Object> createIndexSettings = new HashMap<>();
        createIndexSettings.put("number_of_shards", 1);
        createIndexSettings.put("number_of_replicas", 0);
        client.execute(new CreateIndex.Builder("minepm").settings(createIndexSettings).build());

        client.execute(new PutMapping.Builder("minepm", "event_timings", EVENT_TIMINGS_MAPPING).build());
        client.execute(new PutMapping.Builder("minepm", "chunk_load_timings", CHUNK_LOAD_TIMINGS_MAPPING).build());
    }

    @Override
    public void shutdown() {
        client.shutdownClient();
    }

    @Override
    public void saveEventExecutionReport(long date, String eventClass, Signature signature, long millis, boolean async) {
        Map<String, Object> document = new HashMap<>();
        document.put("date", date);
        document.put("event_class", eventClass);
        document.put("listener_signature", signature.toString());
        document.put("duration", millis);
        document.put("async", async);

        try {
            client.execute(new Index.Builder(document).index("minepm").type("event_timings").build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveChunkLoadReport(long date, Object world, int x, int z, long millis) {
        String worldIdentifier;
        try {
            worldIdentifier = String.valueOf((int) world.getClass().getField("dimension").get(world));
        } catch (ReflectiveOperationException e) {
            return;
        }

        Map<String, Object> document = new HashMap<>();
        document.put("date", date);
        document.put("world", worldIdentifier);
        document.put("x", x);
        document.put("z", z);
        document.put("duration", millis);

        try {
            client.execute(new Index.Builder(document).index("minepm").type("chunk_load_timings").build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
