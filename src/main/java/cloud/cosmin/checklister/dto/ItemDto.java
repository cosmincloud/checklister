package cloud.cosmin.checklister.dto;

import java.time.ZonedDateTime;

public class ItemDto {
    public Long id;
    public String content;
    public Long rank;
    public ZonedDateTime createdAt;
    public ZonedDateTime lastModified;
}
