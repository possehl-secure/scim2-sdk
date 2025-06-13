package dev.suvera.scim2.schema.builder;

import dev.suvera.scim2.schema.data.meta.MetaRecord;
import dev.suvera.scim2.schema.data.resource.ResourceType;
import dev.suvera.scim2.schema.data.resource.SchemaExt;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static dev.suvera.scim2.schema.ScimConstant.*;

/**
 * Default ResourceTypes
 */
public class DefaultResourceTypes {

    private final String baseUrl;
    private final Map<String, ResourceType> typeMap;

    public DefaultResourceTypes(String baseUrl) {
        this.baseUrl = baseUrl;
        this.typeMap = new LinkedHashMap<>();

        addDefaultResourceTypes();
    }


    private void addDefaultResourceTypes() {

        addResourceType(buildResourceType(
                NAME_SP_CONFIG,
                "Typedefinition for ServiceProviderConfig",
                PATH_SP,
                URN_SP_CONFIG,
                null));

        addResourceType(buildResourceType(
                NAME_RESOURCETYPE,
                "Typedefinition for ResourceType",
                PATH_RESOURCETYPES,
                URN_RESOURCE_TYPE,
                null));

        addResourceType(buildResourceType(
                NAME_SCHEMA,
                "Typedefinition for Schema",
                PATH_SCHEMAS,
                URN_SCHEMA,
                null));

        addResourceType(buildResourceType(
                NAME_USER,
                "Typedefinition for User",
                PATH_USERS,
                URN_USER,
                Set.of(
                        SchemaExt.builder()
                                .schema(URN_ENTERPRISE_USER)
                                .required(false)
                                .build()
                )));

        addResourceType(buildResourceType(
                NAME_GROUP,
                "Typedefinition for Group",
                PATH_GROUPS,
                URN_GROUP,
                null
        ));
    }

    public List<ResourceType> getResourceTypes() {
        return typeMap.values().stream().collect(Collectors.toList());
    }

    public ResourceType getResourceType(String id) {
        return typeMap.get(id);
    }

    protected void addResourceType(ResourceType type) {
        typeMap.put(type.getId(), type);
    }


    protected final ResourceType buildResourceType(
            String name, String description, String endpoint,
            String coreSchema, Set<SchemaExt> extSchemas) {

        ResourceType rt = new ResourceType();
        rt.setId(name);
        rt.setName(name);
        rt.setDescription(description);
        rt.setEndPoint(endpoint);
        rt.setSchema(coreSchema);
        rt.setSchemaExtensions(extSchemas);
        rt.setSchemas(Set.of(URN_RESOURCE_TYPE));
        rt.setMeta(new MetaRecord(NAME_RESOURCETYPE, baseUrl + PATH_RESOURCETYPES + "/" + name));
        return rt;
    }
}
