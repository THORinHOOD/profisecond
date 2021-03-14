package com.profi.second;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes")
public class MainController {

    private final NoteService noteService;

    public MainController(NoteService noteService) {
        this.noteService = noteService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNote(@PathVariable Long id) {
        if (!noteService.deleteNote(id)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Note>> getAllNotes(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(noteService.findAllNotes(query));
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody RawNote note) {
        return ResponseEntity.ok(noteService.createNote(note));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNote(@PathVariable Long id) {
        Note note = noteService.findNote(id);
        if (note == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(note);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> edit(@RequestBody RawNote note, @PathVariable Long id) {
        Note noteEdited = noteService.editNote(note.getTitle(), note.getContent(), id);
        if (noteEdited == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(noteEdited);
    }

}
