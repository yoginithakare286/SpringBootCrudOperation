package com.yts.revaux.ntquote;

import com.yts.revaux.ntquote.config.AsyncSyncConfiguration;
import com.yts.revaux.ntquote.config.EmbeddedElasticsearch;
import com.yts.revaux.ntquote.config.EmbeddedRedis;
import com.yts.revaux.ntquote.config.EmbeddedSQL;
import com.yts.revaux.ntquote.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { RevAuxApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
@EmbeddedRedis
@EmbeddedElasticsearch
@EmbeddedSQL
public @interface IntegrationTest {
}
