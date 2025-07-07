package com.kumar.springbootlearning.cache;

import lombok.Data;

@Data
public  class CacheManagerProperties {
    static final int UNSET_INT = -1;

    private String[] cacheNames;
    private CacheProvider provider;
    private long maximumSize = UNSET_INT;
    private int initialCapacity = UNSET_INT;
    /**
     * TTL in seconds after read operation
     */
    private long ttlAfterAccess = UNSET_INT;
    /**
     * TTL in seconds after write operation
     */
    private long ttlAfterWrite = UNSET_INT;
    private String remoteHost;
    private int port = UNSET_INT;
}