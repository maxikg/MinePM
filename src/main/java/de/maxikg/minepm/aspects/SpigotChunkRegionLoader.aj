package de.maxikg.minepm.aspects;

import de.maxikg.minepm.reporter.Reporter;

public aspect SpigotChunkRegionLoader {

    pointcut isChunkLoad(Object world, int x, int z): execution(public java.lang.Object[] net.minecraft.server..ChunkRegionLoader.loadChunk(net.minecraft.server..World, int, int)) && args(world, x, z);

    Object around(Object world, int x, int z): isChunkLoad(world, x, z) {
        if (AspectConfiguration.SPIGOT_CHUNK_LOADER_TIMINGS_ENABLED) {
            long start = System.currentTimeMillis();
            Object result = proceed(world, x, z);
            long duration = System.currentTimeMillis() - start;

            Reporter.reportChunkLoad(world, x, z, duration);

            return result;
        } else {
            return proceed(world, x, z);
        }
    }
}
