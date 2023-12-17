package com.dkserver.danielServer.interceptor;

//set the tenantId from requestHeader
public class TenantContext {
    private static ThreadLocal<String> currentTenant = new InheritableThreadLocal<>();

    public static String getCurrentTenant() {
        System.out.println("GET_CURRENT_TENANT " + currentTenant);
        return currentTenant.get();
    }

    public static void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }

    public static void clear() {
        currentTenant.set(null);
    }
}