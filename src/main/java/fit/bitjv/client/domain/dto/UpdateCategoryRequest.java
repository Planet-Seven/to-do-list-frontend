package fit.bitjv.client.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCategoryRequest {
    private long categoryId;
    private String name;
}
