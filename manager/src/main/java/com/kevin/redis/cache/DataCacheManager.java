package com.kevin.redis.cache;

import com.kevin.redis.utils.AppContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by jinyugai on 2018/8/28.
 */
/*@Component*/
public class DataCacheManager implements CommandLineRunner{//用于预先数据的加载

    private static Set<DataCacheService> dataCacheServiceSet = new HashSet<>();

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public void run(String... args) throws Exception {

        ApplicationContext applicationContext= AppContext.getApplicationContext();
        Map<String, DataCacheService> dataCacheServiceMap = applicationContext.getBeansOfType(DataCacheService.class);
        dataCacheServiceSet.addAll(dataCacheServiceMap.values());
        Thread thread = new Thread(() -> {
            for (DataCacheService dataCacheService : dataCacheServiceSet){
                threadPoolTaskExecutor.execute(dataCacheService::init);
            }
        });
        thread.start();


    }


}
