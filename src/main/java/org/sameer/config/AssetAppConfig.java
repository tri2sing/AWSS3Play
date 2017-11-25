package org.sameer.config;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetAppConfig extends Configuration {
    @NotNull
    @JsonProperty
    private CassandraConfig cassandraConfig;
}
