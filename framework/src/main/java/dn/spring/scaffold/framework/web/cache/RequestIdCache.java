package dn.spring.scaffold.framework.web.cache;

public interface RequestIdCache {

    boolean saveRequestId(String requestId, long expireSeconds);
}
