package fit.bitjv.client.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskRequest {
    private Long taskId;
    private String name;
    private Instant deadline;
    private Long categoryId;
    private Boolean isDone;
    private Set<Long> tagIds;
}
