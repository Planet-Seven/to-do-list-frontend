package fit.bitjv.client.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class TaskResponse {
    private Long taskId;
    private String name;
    //this is horrible and ugly but webflux apparently doesn't know how to work with LocalDate
    private Date deadline;
    private boolean isDone;
    private CategoryResponse category;
    private Set<TagResponse> tags;
}
