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
public class CategoryService {
    private final WebClient.Builder webClientBuilder;
    private final String apiUrl;

    public CategoryService(WebClient.Builder webClientBuilder, @Value("${api.url}") String apiUrl) {
        this.webClientBuilder = webClientBuilder;
        this.apiUrl = apiUrl;
    }

    // Fetch all categories
    public Mono<List<CategoryResponse>> getAllCategories() {
        return webClientBuilder.baseUrl(apiUrl)
                .build()
                .get()
                .uri("/categories")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CategoryResponse>>() {});
    }

    public Mono<CategoryResponse> createCategory(CategoryRequest request) {
        return webClientBuilder.baseUrl(apiUrl)
                .build()
                .post()
                .uri("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(CategoryResponse.class);
    }

    public Mono<Void> deleteCategory(Long id) {
        return webClientBuilder.baseUrl(apiUrl)
                .build()
                .delete()
                .uri("/categories/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

}
