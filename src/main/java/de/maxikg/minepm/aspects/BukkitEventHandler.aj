package de.maxikg.minepm.aspects;

import de.maxikg.minepm.reporter.Reporter;

public aspect BukkitEventHandler {

    pointcut isEventExecutor(org.bukkit.event.Event event): execution(@org.bukkit.event.EventHandler public * *(..)) && args(event);

    void around(org.bukkit.event.Event event): isEventExecutor(event) {
        long start = System.currentTimeMillis();
        proceed(event);
        long duration = System.currentTimeMillis() - start;

        if (duration > AspectConfiguration.BUKKIT_EVENT_HANDLER_THRESHOLD)
            Reporter.reportEventExecution(event.getClass().getName(), thisJoinPoint.getSignature(), duration, event.isAsynchronous());
    }
}
