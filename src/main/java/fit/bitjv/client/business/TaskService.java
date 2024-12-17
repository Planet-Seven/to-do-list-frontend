package fit.bitjv.client.business;
import fit.bitjv.client.domain.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TaskService {

    private final WebClient.Builder webClientBuilder;
    private final String apiUrl;

    public TaskService(WebClient.Builder webClientBuilder, @Value("${api.url}") String apiUrl) {
        this.webClientBuilder = webClientBuilder;
        this.apiUrl = apiUrl;
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

    public Mono<TaskResponse> createTask(CreateTaskRequest request) {
        return webClientBuilder.baseUrl(apiUrl)
                .build()
                .post()
                .uri("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TaskResponse.class);
    }

    public Mono<TaskResponse> updateTask(UpdateTaskRequest request) {
        return webClientBuilder.baseUrl(apiUrl)
                .build()
                .put()  // Use PUT for update
                .uri("/tasks/{id}", request.getTaskId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TaskResponse.class);
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