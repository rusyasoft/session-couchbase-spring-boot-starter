package com.github.mkopylec.sessioncouchbase.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mkopylec.sessioncouchbase.core.CouchbaseSessionRepository;
import com.github.mkopylec.sessioncouchbase.core.Serializer;
import com.github.mkopylec.sessioncouchbase.data.SessionDao;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

@Configuration
@EnableSpringHttpSession
@EnableConfigurationProperties(SessionCouchbaseProperties.class)
public class SessionCouchbaseAutoConfiguration {

    protected SessionCouchbaseProperties sessionCouchbase;

    public SessionCouchbaseAutoConfiguration(SessionCouchbaseProperties sessionCouchbase) {
        this.sessionCouchbase = sessionCouchbase;
    }

    @Bean
    @ConditionalOnMissingBean
    public Serializer serializer() {
        return new Serializer();
    }

    @Bean
    @ConditionalOnMissingBean
    public SessionRepository sessionRepository(SessionDao dao, ObjectMapper mapper, Serializer serializer, ApplicationEventPublisher eventPublisher) {
        return new CouchbaseSessionRepository(sessionCouchbase, dao, mapper, serializer, eventPublisher);
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
