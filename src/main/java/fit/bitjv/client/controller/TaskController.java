package fit.bitjv.client.controller;
import fit.bitjv.client.business.CategoryService;
import fit.bitjv.client.business.TagService;
import fit.bitjv.client.business.TaskService;
import fit.bitjv.client.domain.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.List;

@Controller
@RequestMapping("tasks")
public class TaskController {

    private final TaskService taskService;
    private final TagService tagService;
    private final CategoryService categoryService;

    @Autowired
    public TaskController(TaskService taskService, TagService tagService, CategoryService categoryService) {
        this.taskService = taskService;
        this.tagService = tagService;
        this.categoryService = categoryService;
    }

    // Fetch all tasks, categories, and tags asynchronously
    @GetMapping
    public Mono<String> getAllTasks(Model model) {
        Mono<List<TaskResponse>> tasks = taskService.getAllTasks();
        Mono<List<CategoryResponse>> categories = categoryService.getAllCategories();
        Mono<List<TagResponse>> tags = tagService.getAllTags();

        model.addAttribute("task", new TaskRequest());
        // Empty CreateTaskRequest for form binding
        return Mono.zip(tasks, categories, tags)
                .doOnNext(data -> {
                    model.addAttribute("tasks", data.getT1());
                    model.addAttribute("categories", data.getT2());
                    model.addAttribute("tags", data.getT3());
                })
                .thenReturn("index");
    }

    @PostMapping
    public Mono<String> createTask(@ModelAttribute TaskRequest request) {
        return taskService.createTask(request)
                .thenReturn("redirect:/tasks");
    }

    @GetMapping("/{id}/toggle_done")
    public Mono<String> editTask(@PathVariable("id") Long id) {
        return taskService.toggleDone(id)
                .thenReturn("redirect:/tasks");
    }

    @GetMapping("/{id}/delete")
    public Mono<String> deleteTask(@PathVariable("id") Long id) {
        return taskService.deleteTask(id)
                .thenReturn("redirect:/tasks");
    }
}
