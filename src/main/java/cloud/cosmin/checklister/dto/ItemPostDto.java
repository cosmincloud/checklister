package cloud.cosmin.checklister.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class ItemPostDto {
    @JsonProperty
    public String content;

    @JsonProperty
    public String contentType;

    @JsonProperty
    public Integer rank;
}
