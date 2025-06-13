package dev.suvera.scim2.schema.builder;

import dev.suvera.scim2.schema.data.Attribute;
import dev.suvera.scim2.schema.data.group.GroupDefinition;
import dev.suvera.scim2.schema.data.meta.MetaRecord;
import dev.suvera.scim2.schema.data.resource.ResourceTypeDefinition;
import dev.suvera.scim2.schema.data.schema.Schema;
import dev.suvera.scim2.schema.data.schema.SchemaDefinition;
import dev.suvera.scim2.schema.data.sp.SpConfigDefinition;
import dev.suvera.scim2.schema.data.user.EnterpriseUserDefinition;
import dev.suvera.scim2.schema.data.user.UserDefinition;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static dev.suvera.scim2.schema.ScimConstant.*;

/**
 * Default Schemas
 */
public class DefaultSchemas {

    private final String baseUrl;
    private final Map<String, Schema> schemaMap;

    public DefaultSchemas(String baseUrl) {
        this.baseUrl = baseUrl;
        this.schemaMap = new LinkedHashMap<>();

        addDefaultSchemas();
    }

    private void addDefaultSchemas() {
        addSchema(buildSchema(
                URN_SP_CONFIG, NAME_SP_CONFIG,
                "Service Provider Configurations",
                SpConfigDefinition.getInstance().getAttributes()));

        addSchema(buildSchema(
                URN_RESOURCE_TYPE, NAME_RESOURCETYPE,
                "Schema definition for Resource Types",
                ResourceTypeDefinition.getInstance().getAttributes()));

        addSchema(buildSchema(
                URN_SCHEMA, NAME_SCHEMA,
                "Schema definition for Schema",
                SchemaDefinition.getInstance().getAttributes()));

        addSchema(buildSchema(
                URN_USER, NAME_USER,
                "Schema definition for User",
                UserDefinition.getInstance().getAttributes()));

        addSchema(buildSchema(
                URN_ENTERPRISE_USER, NAME_ENTERPRISE_USER,
                "Schema definition for Enterprise User Extension",
                EnterpriseUserDefinition.getInstance().getAttributes()));

        addSchema(buildSchema(
                URN_GROUP, NAME_GROUP,
                "Schema definition for Group",
                GroupDefinition.getInstance().getAttributes()));

    }

    public List<Schema> getSchemas() {
        return schemaMap.values().stream().toList();
    }

    public Schema getSchema(String id) {
        return schemaMap.get(id);
    }

    protected final void addSchema(Schema schema) {
        schemaMap.put(schema.getId(), schema);
    }

    protected final Schema buildSchema(String id, String name, String description, Set<Attribute> attrs) {

        Schema rt = new Schema();
        rt.setId(id);
        rt.setName(name);
        rt.setDescription(description);
        rt.setAttributes(attrs);
        rt.setSchemas(Set.of(URN_SCHEMA));
        rt.setMeta(new MetaRecord(
                NAME_SCHEMA,
                baseUrl + PATH_SCHEMAS + "/" + id
        ));
        return rt;
    }

}
