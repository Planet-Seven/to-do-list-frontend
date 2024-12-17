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
import java.util.stream.Collectors;

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

        model.addAttribute("task", new CreateTaskRequest()); // Empty CreateTaskRequest for form binding
        return Mono.zip(tasks, categories, tags)
                .doOnNext(data -> {
                    model.addAttribute("tasks", data.getT1());
                    model.addAttribute("categories", data.getT2());
                    model.addAttribute("tags", data.getT3());
                })
                .thenReturn("index");
    }

    // Create or update task
    @PostMapping
    public Mono<String> createTask(@ModelAttribute CreateTaskRequest request) {
        return taskService.createTask(request)
                .thenReturn("redirect:/tasks");
    }

    @PutMapping
    public Mono<String> updateTask(@ModelAttribute UpdateTaskRequest request) {
        return taskService.updateTask(request)
                .thenReturn("redirect:/tasks");
    }

    @GetMapping("/{id}/edit")
    public Mono<String> editTask(@PathVariable("id") Long id, Model model) {
        Mono<TaskResponse> task = taskService.getTaskById(id);
        Mono<List<CategoryResponse>> categories = categoryService.getAllCategories();
        Mono<List<TagResponse>> tags = tagService.getAllTags();

        return Mono.zip(task, categories, tags)
                .doOnNext(data -> {
                    model.addAttribute("task", new UpdateTaskRequest(
                            data.getT1().getTaskId(),
                            data.getT1().getName(),
                            data.getT1().getDeadline(),
                            data.getT1().getCategory().getCategoryId(),
                            data.getT1().getIsDone(),
                            data.getT1().getTags().stream().map(TagResponse::getTagId).collect(Collectors.toSet())
                    ));
                    model.addAttribute("categories", data.getT2());
                    model.addAttribute("tags", data.getT3());
                })
                .thenReturn("index");
    }

    // Delete task
    @GetMapping("/{id}/delete")
    public Mono<String> deleteTask(@PathVariable("id") Long id) {
        return taskService.deleteTask(id)
                .thenReturn("redirect:/tasks");
    }
}
