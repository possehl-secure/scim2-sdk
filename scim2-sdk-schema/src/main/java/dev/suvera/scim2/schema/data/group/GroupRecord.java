package dev.suvera.scim2.schema.data.group;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.suvera.scim2.schema.ScimConstant;
import dev.suvera.scim2.schema.data.BaseRecord;
import dev.suvera.scim2.schema.data.ExtensionRecord;
import dev.suvera.scim2.schema.data.meta.MetaRecord;
import dev.suvera.scim2.schema.util.ScimExtensionRegistry;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * author: suvera
 * date: 10/17/2020 9:56 AM
 */
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
@Data
public class GroupRecord extends BaseRecord {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private String displayName;
    private List<GroupMember> members;

    public GroupRecord() {
        this.meta = new MetaRecord();
        meta.setResourceType(ScimConstant.NAME_GROUP);

        this.schemas = new LinkedHashSet<>();
        this.schemas.add(ScimConstant.URN_GROUP);
    }

    @JsonIgnore
    private Map<String, ExtensionRecord> extensions = new HashMap<>();

    @JsonAnySetter
    public void addExtension(String key, Object value) {
        ExtensionRecord extension;

        if (value instanceof Map) {
            Class<? extends ExtensionRecord> targetType = ScimExtensionRegistry.getType(key);
            if (targetType == null) {
                throw new IllegalArgumentException("Unknown SCIM extension URN: " + key);
            }
            extension = MAPPER.convertValue(value, targetType);
        } else if (value instanceof ExtensionRecord) {
            extension = (ExtensionRecord) value;
        } else {
            throw new IllegalArgumentException("Invalid field provided: " + key);
        }

        extensions.put(key, extension);
        schemas.add(key);
    }

    @JsonAnyGetter
    public Map<String, ExtensionRecord> getExtensions() {
        return extensions;
    }


    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class GroupMember {
        private String type;
        private String display;
        private String value;
        @JsonProperty("$ref")
        private String ref;
    }
}
