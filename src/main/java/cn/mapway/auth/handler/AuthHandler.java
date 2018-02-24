package cn.mapway.auth.handler;

import cn.mapway.auth.AuthProviderType;

import java.util.Map;

public interface AuthHandler {

    AuthData onLogin(String name, AuthProviderType authProviderType, Map<String, String> data);
}
