package fit.bitjv.client.controller;

import fit.bitjv.client.business.TagService;
import fit.bitjv.client.domain.dto.TagResponse;
import fit.bitjv.client.domain.dto.TagRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequestMapping("tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public Mono<String> getAllTags(Model model) {
        Mono<List<TagResponse>> tags = tagService.getAllTags();
        model.addAttribute("tag", new TagRequest());
        model.addAttribute("tags", tags.block());
        return Mono.just("tags");
    }

    @PostMapping
    public Mono<String> createTag(@ModelAttribute TagRequest request) {
        return tagService.createTag(request)
                .thenReturn("redirect:/tags");
    }
}