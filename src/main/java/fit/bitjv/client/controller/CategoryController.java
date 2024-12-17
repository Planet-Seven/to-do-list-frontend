package fit.bitjv.client.controller;

import fit.bitjv.client.business.CategoryService;
import fit.bitjv.client.domain.dto.CategoryResponse;
import fit.bitjv.client.domain.dto.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequestMapping("categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Mono<String> getAllCategories(Model model) {
        Mono<List<CategoryResponse>> categories = categoryService.getAllCategories();
        model.addAttribute("category", new CategoryRequest());
        model.addAttribute("categories", categories.block());
        return Mono.just("categories");
    }

    @PostMapping
    public Mono<String> createCategory(@ModelAttribute CategoryRequest request) {
        return categoryService.createCategory(request)
                .thenReturn("redirect:/categories");
    }
}