package dev.suvera.scim2.schema.data.user;

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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserRecord extends BaseRecord {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private String userName;
    private Name name;
    private String displayName;
    private String nickName;
    private String profileUrl;
    private String title;
    private String userType;
    private String preferredLanguage;
    private String locale;
    private String timezone;
    private boolean active;
    private String password;
    private List<MultiValuedAttribute> emails;
    private List<MultiValuedAttribute> phoneNumbers;
    private List<MultiValuedAttribute> ims;
    private List<MultiValuedAttribute> photos;
    private List<Address> addresses;
    private List<GroupRef> groups;
    private List<MultiValuedAttribute> entitlements;
    private List<MultiValuedAttribute> roles;
    private List<MultiValuedAttribute> x509Certificates;

    public UserRecord() {
        this.meta = new MetaRecord();
        meta.setResourceType(ScimConstant.NAME_USER);

        this.schemas = new LinkedHashSet<>();
        this.schemas.add(ScimConstant.URN_USER);
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
    public static class Name {
        private String formatted;
        private String familyName;
        private String givenName;
        private String middleName;
        private String honorificPrefix;
        private String honorificSuffix;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MultiValuedAttribute {
        private String value;
        private String display;
        private String type;
        private boolean primary;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Address {
        private String formatted;
        private String streetAddress;
        private String locality;
        private String region;
        private String postalCode;
        private String country;
        private String type;
        private boolean primary;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class GroupRef {
        private String value;
        @JsonProperty("$ref")
        private String ref;
        private String display;
        private String type;
    }
}
