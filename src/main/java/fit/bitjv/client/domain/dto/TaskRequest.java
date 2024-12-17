package fit.bitjv.client.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class TaskRequest {
    private String name;
    private Date deadline;
    private boolean isDone;
    private Long categoryId;
    private Set<Long> tagIds = new HashSet<>();
}
