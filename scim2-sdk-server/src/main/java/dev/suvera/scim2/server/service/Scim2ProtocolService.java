package dev.suvera.scim2.server.service;

import com.google.common.collect.ImmutableSet;
import dev.suvera.scim2.schema.ScimConstant;
import dev.suvera.scim2.schema.builder.DefaultResourceTypes;
import dev.suvera.scim2.schema.builder.DefaultSchemas;
import dev.suvera.scim2.schema.builder.DefaultSpConfig;
import dev.suvera.scim2.schema.data.misc.ListResponse;
import dev.suvera.scim2.schema.data.resource.ResourceType;
import dev.suvera.scim2.schema.data.schema.Schema;
import dev.suvera.scim2.schema.data.sp.SpConfig;

import java.util.List;
import java.util.Set;

/**
 * author: suvera
 * date: 10/19/2020 12:21 PM
 */
public interface Scim2ProtocolService {

    default SpConfig buildServiceProviderConfig() {
        DefaultSpConfig defaultImpl = new DefaultSpConfig();
        return defaultImpl.serviceProviderConfig();
    }

    default ListResponse<ResourceType> buildResourceTypes() {
        DefaultResourceTypes defaultResourceTypes = new DefaultResourceTypes(ScimConstant.DEFAULT_SERVER);
        List<ResourceType> typeList = defaultResourceTypes.getResourceTypes();

        ListResponse<ResourceType> list = new ListResponse<>();
        list.setSchemas(Set.of(ScimConstant.URN_LIST_RESPONSE));
        list.setResources(typeList);
        list.setTotalResults(typeList.size());
        list.setItemsPerPage(typeList.size());
        list.setStartIndex(1);

        return list;
    }

    default ResourceType buildResourceType(String id) {
        DefaultResourceTypes defaultResourceTypes = new DefaultResourceTypes(ScimConstant.DEFAULT_SERVER);
        ResourceType type = defaultResourceTypes.getResourceType(id);
        if (type == null) {
            throw new RuntimeException("Could not find ResourceType with id " + id);
        }

        return type;
    }


    default ListResponse<Schema> buildSchemas() {
        DefaultSchemas defaultSchemas = new DefaultSchemas(ScimConstant.DEFAULT_SERVER);
        List<Schema> schemaList = defaultSchemas.getSchemas();

        ListResponse<Schema> list = new ListResponse<>();
        list.setSchemas(ImmutableSet.of(ScimConstant.URN_LIST_RESPONSE));
        list.setResources(schemaList);
        list.setTotalResults(schemaList.size());
        list.setItemsPerPage(schemaList.size());
        list.setStartIndex(1);

        return list;
    }

    default Schema buildSchema(String id) {
        DefaultSchemas defaultSchemas = new DefaultSchemas(ScimConstant.DEFAULT_SERVER);
        Schema schema = defaultSchemas.getSchema(id);
        if (schema == null) {
            throw new RuntimeException("Could not find Schema for id " + id);
        }

        return schema;
    }
}
