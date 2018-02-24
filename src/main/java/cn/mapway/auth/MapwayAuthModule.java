package cn.mapway.auth;

import cn.mapway.auth.handler.AuthHandler;
import cn.mapway.auth.module.Resource;
import cn.mapway.auth.provider.IAuthProvider;
import cn.mapway.auth.provider.ILDAPProvider;
import cn.mapway.auth.provider.IOAuthProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * 认证模块.
 */
public class MapwayAuthModule {

    private static Logger LOGGER = java.util.logging.Logger.getLogger(MapwayAuthModule.class.getName());

    public class ProviderData {
        public AuthProviderType authProviderType;
        public IAuthProvider provider;

        ProviderData(AuthProviderType providerType, IAuthProvider provider) {
            authProviderType = providerType;
            this.provider = provider;
        }
    }

    private Map<String, ProviderData> providers;
    private List<AuthHandler> handlers;

    public MapwayAuthModule() {
        providers = new ConcurrentHashMap<String, ProviderData>(4);
        handlers = new ArrayList<AuthHandler>(5);
    }

    /**
     * 添加认证处理函数
     *
     * @param authHandler
     * @return
     */
    public MapwayAuthModule addAuthHandler(AuthHandler authHandler) {
        handlers.add(authHandler);
        return this;
    }

    public MapwayAuthModule addOAuthProvider(String name, IOAuthProvider provider) {
        return addProvider(name, AuthProviderType.AUTH_OAUTH2, provider);
    }

    public MapwayAuthModule addLDAPProvider(String name, ILDAPProvider provider) {
        return addProvider(name, AuthProviderType.AUTH_LDAP, provider);
    }

    private MapwayAuthModule addProvider(String name, AuthProviderType providerType, IAuthProvider provider) {
        if (name == null || name.length() == 0) {
            LOGGER.warning("configure auth info with null");
            return this;
        }
        if (providers.get(name) != null) {
            LOGGER.warning("duplicate configure auth info with " + name + ", ignore this configure");
            return this;
        }
        providers.put(name, new ProviderData(providerType, provider));
        return this;
    }


    /**
     * 查询用户角色
     *
     * @param userId
     * @return
     */
    public List<String> userRoles(String userId) {
        return new ArrayList<String>();
    }

    /**
     * 查询用户是否拥有资源
     *
     * @param userId       用户ID
     * @param resourcePath 用户资源
     * @return
     */
    public boolean userResource(String userId, String resourcePath) {
        return false;
    }

    /**
     * 查询用户拥有的资源列表
     *
     * @param userId
     * @return
     */
    public List<Resource> userResources(String userId) {
        return new ArrayList<Resource>(100);
    }


}
