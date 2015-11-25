package de.maxikg.minepm.reporter.adapter;

import com.google.gson.stream.JsonWriter;
import org.aspectj.lang.Signature;
import org.zeromq.ZMQ;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Objects;

public class ZeroMQAdapter implements ReportingAdapter {

    private final String uri;
    private ZMQ.Context context;
    private ZMQ.Socket socket;

    public ZeroMQAdapter(String uri) {
        this.uri = Objects.requireNonNull(uri, "uri must be not null.");
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
            jw.name("type");
            jw.value("event_timing");
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
            // ToDo: Exception handling
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
            // ToDo: Exception handling
            return;
        }

        String value;
        try (StringWriter sw = new StringWriter(); JsonWriter jw = new JsonWriter(sw)) {
            jw.beginObject();
            jw.name("type");
            jw.value("chunk_timing");
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
            // ToDo: Exception handling
            return;
        }

        socket.send(value);
    }
}
