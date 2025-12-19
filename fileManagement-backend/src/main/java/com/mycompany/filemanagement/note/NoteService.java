package com.mycompany.filemanagement.note;

import com.mycompany.filemanagement.common.NotFoundException;
import com.mycompany.filemanagement.note.dto.NoteRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository repo;

    public NoteService(NoteRepository repo) {
        this.repo = repo;
    }

    public List<Note> list() {
        return repo.findAll().stream()
                .sorted((a, b) -> b.getUpdatedAt().compareTo(a.getUpdatedAt()))
                .toList();
    }

    public Note get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Note not found: " + id));
    }

    @Transactional
    public Note create(NoteRequest req) {
        Note n = new Note();
        n.setTitle(req.getTitle().trim());
        n.setContent(req.getContent().trim());
        return repo.save(n);
    }

    @Transactional
    public Note update(Long id, NoteRequest req) {
        Note n = get(id);
        n.setTitle(req.getTitle().trim());
        n.setContent(req.getContent().trim());
        return repo.save(n);
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Note not found: " + id);
        }
        repo.deleteById(id);
    }
}
