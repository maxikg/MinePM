package de.maxikg.minepm.reporter.adapter;

import com.google.gson.stream.JsonWriter;
import org.aspectj.lang.Signature;
import org.zeromq.ZMQ;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ZeroMQAdapter implements ReportingAdapter {

    private static final Logger LOGGER = Logger.getLogger(ZeroMQAdapter.class.getName());

    private final String uri;
    private final String serverId;
    private ZMQ.Context context;
    private ZMQ.Socket socket;

    public ZeroMQAdapter(String uri, String serverId) {
        this.uri = Objects.requireNonNull(uri, "uri must be not null.");
        this.serverId = Objects.requireNonNull(serverId, "serverId must be not null.");
    }

    @Override
    public void init() throws Throwable {
        context = ZMQ.context(1);
        socket = context.socket(ZMQ.PAIR);
        socket.connect(uri);
    }

    @Override
    public void shutdown() throws Throwable {
        socket.close();
        context.close();
    }

    @Override
    public void saveEventExecutionReport(String eventClass, Signature signature, long millis, boolean async) {
        String value;
        try (StringWriter sw = new StringWriter(); JsonWriter jw = new JsonWriter(sw)) {
            jw.beginObject();
            basicWrite(jw, "event_timing");
            jw.name("message");
            jw.beginObject();
            jw.name("event_class").value(eventClass);
            jw.name("listener_signature").value(signature.toString());
            jw.name("duration").value(millis);
            jw.name("async").value(async);
            jw.endObject();
            jw.endObject();
            value = sw.toString();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "An exception occurred while persisting message.", e);
            return;
        }

        socket.send(value);
    }

    @Override
    public void saveChunkLoadReport(Object world, int x, int z, long millis) {
        String worldIdentifier;
        try {
            worldIdentifier = String.valueOf((int) world.getClass().getField("dimension").get(world));
        } catch (ReflectiveOperationException e) {
            LOGGER.log(Level.SEVERE, "An exception occurred while accessing field \"dimension\" on " + Objects.toString(world.getClass().getName(), "null") + ".", e);
            return;
        }

        String value;
        try (StringWriter sw = new StringWriter(); JsonWriter jw = new JsonWriter(sw)) {
            jw.beginObject();
            basicWrite(jw, "chunk_timing");
            jw.name("message");
            jw.beginObject();
            jw.name("world").value(worldIdentifier);
            jw.name("x").value(x);
            jw.name("z").value(z);
            jw.name("duration").value(millis);
            jw.endObject();
            jw.endObject();
            value = sw.toString();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "An exception occurred while persisting message.", e);
            return;
        }

        socket.send(value);
    }

    @Override
    public void saveTps(double tps) {
        String value;
        try (StringWriter sw = new StringWriter(); JsonWriter jw = new JsonWriter(sw)) {
            jw.beginObject();
            basicWrite(jw, "tps");
            jw.name("message");
            jw.beginObject();
            jw.name("tps").value(tps);
            jw.endObject();
            jw.endObject();
            value = sw.toString();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "An exception occurred while persisting message.", e);
            return;
        }

        socket.send(value);
    }

    @Override
    public void savePlayers(int players, int maxPlayers) {
        String value;
        try (StringWriter sw = new StringWriter(); JsonWriter jw = new JsonWriter(sw)) {
            jw.beginObject();
            basicWrite(jw, "players");
            jw.name("message");
            jw.beginObject();
            jw.name("players").value(players);
            jw.name("max_players").value(maxPlayers);
            jw.endObject();
            jw.endObject();
            value = sw.toString();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "An exception occurred while persisting message.", e);
            return;
        }

        socket.send(value);
    }

    private void basicWrite(JsonWriter jsonWriter, String type) throws IOException {
        jsonWriter.name("type").value(type);
        jsonWriter.name("server_id").value(serverId);
    }
}
