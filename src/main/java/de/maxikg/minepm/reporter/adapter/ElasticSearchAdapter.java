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

    private static final String MAPPING = "{" +
            "  \"event_timings\": {" +
            "    \"properties\": {" +
            "      \"date\": {" +
            "        \"type\": \"date\"," +
            "        \"format\": \"epoch_millis\"" +
            "      }," +
            "      \"event_class\": {" +
            "        \"type\": \"string\"" +
            "      }," +
            "      \"listener_signature\": {" +
            "        \"type\": \"string\"" +
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

        client.execute(new PutMapping.Builder("minepm", "event_timings", MAPPING).build());
    }

    @Override
    public void shutdown() {
        client.shutdownClient();
    }

    @Override
    public void saveEventExecutionReport(long date, String eventClass, Signature signature, long millis) {
        Map<String, Object> document = new HashMap<>();
        document.put("date", date);
        document.put("event_class", eventClass);
        document.put("listener_signature", signature.toString());
        document.put("duration", millis);

        try {
            client.execute(new Index.Builder(document).index("minepm").type("event_timings").build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
