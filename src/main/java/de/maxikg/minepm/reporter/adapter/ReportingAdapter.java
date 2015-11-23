package de.maxikg.minepm.reporter.adapter;

import org.aspectj.lang.Signature;

public interface ReportingAdapter {

    void init() throws Throwable;

    void shutdown() throws Throwable;

    void saveEventExecutionReport(long date, String eventClass, Signature signature, long millis, boolean async);
}
