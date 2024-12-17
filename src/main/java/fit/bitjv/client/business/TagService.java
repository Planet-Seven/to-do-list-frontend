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
public class TagService {

    private final WebClient.Builder webClientBuilder;
    private final String apiUrl;

    public TagService(WebClient.Builder webClientBuilder, @Value("${api.url}") String apiUrl) {
        this.webClientBuilder = webClientBuilder;
        this.apiUrl = apiUrl;
    }

    // Fetch all tags
    public Mono<List<TagResponse>> getAllTags() {
        return webClientBuilder.baseUrl(apiUrl)
                .build()
                .get()
                .uri("/tags")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TagResponse>>() {});
    }

    public Mono<TagResponse> createTag(CreateTagRequest request) {
        return webClientBuilder.baseUrl(apiUrl)
                .build()
                .post()
                .uri("/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TagResponse.class);
    }

    // Delete a task
    public Mono<Void> deleteTag(Long id) {
        return webClientBuilder.baseUrl(apiUrl)
                .build()
                .delete()
                .uri("/tags/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
