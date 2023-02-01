package org.ujar.jh.petclinic.reactmin.config;

import com.github.benmanes.caffeine.jcache.configuration.CaffeineConfiguration;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Caffeine caffeine = jHipsterProperties.getCache().getCaffeine();

        CaffeineConfiguration<Object, Object> caffeineConfiguration = new CaffeineConfiguration<>();
        caffeineConfiguration.setMaximumSize(OptionalLong.of(caffeine.getMaxEntries()));
        caffeineConfiguration.setExpireAfterWrite(OptionalLong.of(TimeUnit.SECONDS.toNanos(caffeine.getTimeToLiveSeconds())));
        caffeineConfiguration.setStatisticsEnabled(true);
        jcacheConfiguration = caffeineConfiguration;
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, org.ujar.jh.petclinic.reactmin.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, org.ujar.jh.petclinic.reactmin.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, org.ujar.jh.petclinic.reactmin.domain.User.class.getName());
            createCache(cm, org.ujar.jh.petclinic.reactmin.domain.Authority.class.getName());
            createCache(cm, org.ujar.jh.petclinic.reactmin.domain.User.class.getName() + ".authorities");
            createCache(cm, org.ujar.jh.petclinic.reactmin.domain.Vets.class.getName());
            createCache(cm, org.ujar.jh.petclinic.reactmin.domain.Vets.class.getName() + ".specialties");
            createCache(cm, org.ujar.jh.petclinic.reactmin.domain.Specialties.class.getName());
            createCache(cm, org.ujar.jh.petclinic.reactmin.domain.Specialties.class.getName() + ".vets");
            createCache(cm, org.ujar.jh.petclinic.reactmin.domain.Types.class.getName());
            createCache(cm, org.ujar.jh.petclinic.reactmin.domain.Owners.class.getName());
            createCache(cm, org.ujar.jh.petclinic.reactmin.domain.Owners.class.getName() + ".pets");
            createCache(cm, org.ujar.jh.petclinic.reactmin.domain.Pets.class.getName());
            createCache(cm, org.ujar.jh.petclinic.reactmin.domain.Pets.class.getName() + ".visits");
            createCache(cm, org.ujar.jh.petclinic.reactmin.domain.Visits.class.getName());
            // jhipster-needle-caffeine-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
