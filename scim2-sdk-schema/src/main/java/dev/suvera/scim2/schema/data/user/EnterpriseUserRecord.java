package dev.suvera.scim2.schema.data.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.suvera.scim2.schema.ScimConstant;
import dev.suvera.scim2.schema.data.ExtensionRecord;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EnterpriseUserRecord extends ExtensionRecord {
    private String employeeNumber;
    private String costCenter;
    private String organization;
    private String division;
    private String department;
    private ManagerRef manager;

    @Data
    public static class ManagerRef {
        private String value;
        private String display;
        @JsonProperty("$ref")
        private String ref;
    }

    @Override
    public String getUrn() {
        return ScimConstant.URN_ENTERPRISE_USER;
    }
}

