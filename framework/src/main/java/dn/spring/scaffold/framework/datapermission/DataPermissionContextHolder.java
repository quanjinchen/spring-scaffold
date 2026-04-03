package dn.spring.scaffold.framework.datapermission;

public final class DataPermissionContextHolder {

    private static final ThreadLocal<String> POLICY_HOLDER = new ThreadLocal<String>();

    private DataPermissionContextHolder() {
    }

    public static void setPolicy(String policy) {
        POLICY_HOLDER.set(policy);
    }

    public static String getPolicy() {
        return POLICY_HOLDER.get();
    }

    public static void clear() {
        POLICY_HOLDER.remove();
    }
}
