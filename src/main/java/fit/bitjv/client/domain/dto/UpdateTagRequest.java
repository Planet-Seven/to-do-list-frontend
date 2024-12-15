package fit.bitjv.client.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTagRequest {
    private long tagId;
    private String name;
}
