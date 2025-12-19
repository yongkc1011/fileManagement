package com.mycompany.filemanagement.note;

import com.mycompany.filemanagement.note.dto.NoteRequest;
import com.mycompany.filemanagement.note.dto.NoteResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService service;

    public NoteController(NoteService service) {
        this.service = service;
    }

    @GetMapping
    public List<NoteResponse> list() {
        return service.list().stream()
                .map(n -> new NoteResponse(
                n.getId(),
                n.getTitle(),
                n.getContent(),
                n.getCreatedAt(),
                n.getUpdatedAt()
        ))
                .toList();
    }

    @GetMapping("/{id}")
    public NoteResponse get(@PathVariable Long id) {
        Note n = service.get(id);
        return new NoteResponse(n.getId(), n.getTitle(), n.getContent(), n.getCreatedAt(), n.getUpdatedAt());
    }

    @PostMapping
    public NoteResponse create(@Valid @RequestBody NoteRequest req) {
        Note n = service.create(req);
        return new NoteResponse(n.getId(), n.getTitle(), n.getContent(), n.getCreatedAt(), n.getUpdatedAt());
    }

    @PutMapping("/{id}")
    public NoteResponse update(@PathVariable Long id, @Valid @RequestBody NoteRequest req) {
        Note n = service.update(id, req);
        return new NoteResponse(n.getId(), n.getTitle(), n.getContent(), n.getCreatedAt(), n.getUpdatedAt());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
