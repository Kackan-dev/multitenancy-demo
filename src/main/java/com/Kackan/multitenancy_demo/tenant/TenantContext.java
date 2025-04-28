package com.Kackan.multitenancy_demo.tenant;

public class TenantContext {
    public static final String PUBLIC_TENANT = "PUBLIC";

    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    public static void clearTenantContext() {
        currentTenant.remove();
    }

    public static void setCurrentTenant(String tenantId) {
        currentTenant.set(tenantId);
    }

    public static String getCurrentTenantId() {
        return currentTenant.get();
    }
}
