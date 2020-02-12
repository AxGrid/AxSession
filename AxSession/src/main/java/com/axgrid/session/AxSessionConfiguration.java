package com.axgrid.session;

import com.axgrid.cache.AxCacheObject;
import com.axgrid.cache.EnableAxCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.axgrid.session")
@EnableAxCache
public class AxSessionConfiguration {


    public static final String SESSION_CACHE ="session-cache";

    @Bean
    public AxCacheObject getAxSpinCache(@Value("${axgrid.session.expire:360}") int dataExpire,
                                        @Value("${axgrid.session.size:7000}") int dataSize
    )
    {
        return AxCacheObject
                .builder()
                .configuration(new AxCacheObject.CacheObjectConfiguration(SESSION_CACHE,
                        AxCacheObject.ExpireType.Access,
                        dataExpire,
                        dataSize))
                .build();
    }

}
