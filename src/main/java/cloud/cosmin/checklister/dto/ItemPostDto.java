package cloud.cosmin.checklister.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemPostDto {
    @JsonProperty
    public String content;

    @JsonProperty
    public String contentType;
}
