package dev.suvera.scim2.schema.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public abstract class ExtensionRecord {

    @JsonIgnore
    public abstract String getUrn();

}
