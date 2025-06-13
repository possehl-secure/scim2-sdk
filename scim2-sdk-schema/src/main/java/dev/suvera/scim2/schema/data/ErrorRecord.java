package dev.suvera.scim2.schema.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableSet;
import dev.suvera.scim2.schema.ScimConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * author: suvera
 * date: 10/17/2020 10:37 AM
 */
@Data
@NoArgsConstructor
public class ErrorRecord {
    private Set<String> schemas = ImmutableSet.of(ScimConstant.URN_ERROR);
    private String scimType;
    private String detail;
    private int status;

    public ErrorRecord(int status, String detail) {
        this.detail = detail;
        this.status = status;
    }

    @Override
    @JsonIgnore
    public String toString() {
        StringBuilder sb = new StringBuilder("ScimError{");
        if (scimType != null) sb.append(String.format("scimType='%s', ", scimType));
        sb.append(String.format("detail='%s', ", detail));
        sb.append(String.format("status=%d", status));
        sb.append("}");
        return sb.toString();
    }
}
