package cloud.cosmin.checklister.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ListPostDto {
    @JsonProperty
    public UUID uuid;

    @JsonProperty
    public String title;
}
