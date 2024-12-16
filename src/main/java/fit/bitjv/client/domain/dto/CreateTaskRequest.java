package fit.bitjv.client.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class CreateTaskRequest {
    private String name;
    private Date deadline;
    private Long categoryId;
    private Set<Long> tagIds;
}
