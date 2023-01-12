package demo.sequenceIterator;

import org.ehcache.Cache;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;

import java.io.File;

/*
*   Implementamos patron Singleton en orden de tener una sola instancia encargada de manipular el acceso a cache
*   manejador = singleton
* */
public final class EHCacheManager{

    private static EHCacheManager manejador; // unica instancia (monocopia)
    public Cache<Integer, CollectionSerializable> ehCache;

    final static String storagePath = "cache";

    private EHCacheManager(){
        try {
            ehCache = CacheManagerBuilder.newCacheManagerBuilder()
                    .with(CacheManagerBuilder.persistence(getStoragePath()
                            + File.separator
                            + "dataStorage"))
                    .withCache("persistent-cache", CacheConfigurationBuilder
                            .newCacheConfigurationBuilder(Integer.class, CollectionSerializable.class,
                                    ResourcePoolsBuilder.newResourcePoolsBuilder()
                                            .heap(10, EntryUnit.ENTRIES) // Cantidad de elementos en memoria
                                            .disk(10, MemoryUnit.MB, true)) // Asignacion de espacio en el disco duro para persistencia (MB)
                    )
                    .build(true).createCache("myCache",
                    CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, CollectionSerializable.class, ResourcePoolsBuilder.heap(10)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getStoragePath() {
        return storagePath;
    }

    public static EHCacheManager getManejador(){
        if(manejador == null){
            manejador = new EHCacheManager();
        }
        return manejador;
    }

}
