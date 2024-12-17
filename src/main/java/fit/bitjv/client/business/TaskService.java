package fit.bitjv.client.business;
import fit.bitjv.client.domain.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;

@Service
public class TaskService {

    private final WebClient.Builder webClientBuilder;
    private final String apiUrl;
    private final ModelMapper mapper;

    public TaskService(WebClient.Builder webClientBuilder, @Value("${api.url}") String apiUrl, ModelMapper mapper) {
        this.webClientBuilder = webClientBuilder;
        this.apiUrl = apiUrl;
        this.mapper = mapper;
    }

    // Fetch all tasks
    public Mono<List<TaskResponse>> getAllTasks() {
        return webClientBuilder.baseUrl(apiUrl)
                .build()
                .get()
                .uri("/tasks")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TaskResponse>>() {});
    }

    public Mono<TaskResponse> getTaskById(Long id) {
        return webClientBuilder.baseUrl(apiUrl)
                .build()
                .get()
                .uri("/tasks/{id}", id)
                .retrieve()
                .bodyToMono(TaskResponse.class);
    }

    public Mono<TaskResponse> createTask(TaskRequest request) {
        request.setDone(false);

        return webClientBuilder.baseUrl(apiUrl)
                .build()
                .post()
                .uri("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TaskResponse.class);
    }

    public Mono<TaskResponse> updateTask(TaskRequest request, Long id) {
        return webClientBuilder.baseUrl(apiUrl)
                .build()
                .put()  // Use PUT for update
                .uri("/tasks/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TaskResponse.class);
    }

    public Mono<TaskResponse> toggleDone(Long id) {
        TaskResponse task = getTaskById(id).block();
        TaskRequest request = mapper.map(task, TaskRequest.class);

        request.setTagIds(new HashSet<>());
        //this is horrible and ugly but webflux apparently doesn't know how to work with LocalDate
        if (task.getDeadline() != null)
            request.setDeadline(task.getDeadline().toInstant().toString());

        request.setDone(!task.isDone());
        for (TagResponse tag : task.getTags()) {
            request.getTagIds().add(tag.getTagId());
        }

        return updateTask(request, id);
    }

    // Delete a task
    public Mono<Void> deleteTask(Long id) {
        return webClientBuilder.baseUrl(apiUrl)
                .build()
                .delete()
                .uri("/tasks/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}