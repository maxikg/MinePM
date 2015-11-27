package de.maxikg.minepm.reporter.adapter;

import org.aspectj.lang.Signature;

public interface ReportingAdapter {

    void init() throws Throwable;

    void shutdown() throws Throwable;

    void saveEventExecutionReport(String eventClass, Signature signature, long millis, boolean async);

    void saveChunkLoadReport(Object world, int x, int z, long millis);

    void saveTps(double tps);
}
