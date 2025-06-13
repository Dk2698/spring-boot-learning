package com.kumar.springbootlearning.mvc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;


@ConfigurationProperties(
        prefix = "com.kumar.web",
        ignoreUnknownFields = false
)
public class ComWebProperties {
    private final CorsConfiguration cors = new CorsConfiguration();
    private final Http http = new Http();

    public ComWebProperties() {
    }

    public CorsConfiguration getCors() {
        return this.cors;
    }

    public Http getHttp() {
        return this.http;
    }

    public static class Http {
        private final Cache cache = new Cache();

        public Http() {
        }

        public Cache getCache() {
            return this.cache;
        }

        public static class Cache {
            private int timeToLiveInDays = 1461;

            public Cache() {
            }

            public int getTimeToLiveInDays() {
                return this.timeToLiveInDays;
            }

            public void setTimeToLiveInDays(int timeToLiveInDays) {
                this.timeToLiveInDays = timeToLiveInDays;
            }
        }
    }
}