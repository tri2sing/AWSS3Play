package org.sameer.config;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class CassandraConfig {
    @NotNull
    @JsonProperty
    private String clusterNode;

    @JsonProperty
    private int port;

}
