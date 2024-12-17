package fit.bitjv.client.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class TaskRequest {
    private String name;
    //this is horrible and ugly but webflux apparently doesn't know how to work with LocalDate
    private String deadline;
    private boolean isDone;
    private Long categoryId;
    private Set<Long> tagIds = new HashSet<>();
}
