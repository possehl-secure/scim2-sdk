package dev.suvera.scim2.schema.util;

import dev.suvera.scim2.schema.ScimConstant;
import dev.suvera.scim2.schema.data.ExtensionRecord;
import dev.suvera.scim2.schema.data.user.EnterpriseUserRecord;

import java.util.HashMap;
import java.util.Map;

public final class ScimExtensionRegistry {

    private static final Map<String, Class<? extends ExtensionRecord>> EXTENSION_TYPES = new HashMap<>();

    static {
        register(ScimConstant.URN_ENTERPRISE_USER, EnterpriseUserRecord.class);
    }

    private ScimExtensionRegistry() {
        // Utility class
    }

    public static void register(String urn, Class<? extends ExtensionRecord> clazz) {
        EXTENSION_TYPES.put(urn, clazz);
    }

    public static Class<? extends ExtensionRecord> getType(String urn) {
        return EXTENSION_TYPES.get(urn);
    }

    public static boolean isKnownExtension(String urn) {
        return EXTENSION_TYPES.containsKey(urn);
    }

}

