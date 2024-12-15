package fit.bitjv.client.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
public class TaskResponse {
    private Long taskId;
    private String name;
    private Instant deadline;
    private Boolean isDone = false;
    private CategoryResponse category;
    private Set<TagResponse> tags;
}
