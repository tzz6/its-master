package com.its.framework.cacheproxy.redis;

public class RedisConfig {
	private String servers;
	private String masters;
	private String password;
	
	private boolean sentinel = true;
	private int poolSize = 50;
	private int ttl = 86400;
	private boolean ehSerialize = false;

	public RedisConfig() {
	}

	public RedisConfig(String sentinels, String masters, int poolSize, int ttl) {
		this.servers = sentinels;
		this.masters = masters;
		this.poolSize = poolSize;
		this.ttl = ttl;
		this.sentinel = true;
	}

	public RedisConfig(String servers, int poolSize, int ttl) {
		this.servers = servers;
		this.poolSize = poolSize;
		this.ttl = ttl;
		this.sentinel = false;
	}

	public String getServers() {
		return this.servers;
	}

	public RedisConfig setServers(String servers) {
		this.servers = servers;
		return this;
	}

	public String getMasters() {
		return this.masters;
	}

	public RedisConfig setMasters(String masters) {
		this.masters = masters;
		this.sentinel = ((masters != null) && (masters.length() > 0));
		return this;
	}

	public int getPoolSize() {
		return this.poolSize;
	}

	public RedisConfig setPoolSize(int poolSize) {
		this.poolSize = poolSize;
		return this;
	}

	public int getTtl() {
		return this.ttl;
	}

	public RedisConfig setTtl(int ttl) {
		this.ttl = ttl;
		return this;
	}

	public boolean isEhSerialize() {
		return this.ehSerialize;
	}

	public RedisConfig setEhSerialize(boolean ehSerialize) {
		this.ehSerialize = ehSerialize;
		return this;
	}

	public boolean isSentinel() {
		return this.sentinel;
	}

	public RedisConfig setSentinel(boolean sentinel) {
		this.sentinel = sentinel;
		return this;
	}

	public String getPassword() {
		return this.password;
	}

	public RedisConfig setPassword(String password) {
		if (password != null) {
			password = password.trim();
			if ((password.length() == 0) || ("null".equalsIgnoreCase(password))) {
				password = null;
			}
		}
		this.password = password;
		return this;
	}

	public RedisCache build() {
		return new RedisCache(this);
	}
}
