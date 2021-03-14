package com.profi.second;

import java.util.HashSet;
import java.util.Set;

public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public boolean deleteNote(Long id) {
        if (!noteRepository.findById(id).isPresent()) {
            return false;
        }
        noteRepository.deleteById(id);
        return true;
    }

    public Iterable<Note> findAllNotes(String query) {
        if (query != null) {
            Set<Note> result = new HashSet<>();
            result.addAll(noteRepository.findByContentContaining(query));
            result.addAll(noteRepository.findByTitleContaining(query));
            return result;
        } else {
            return findAllNotes();
        }
    }

    public Iterable<Note> findAllNotes() {
        return noteRepository.findAll();
    }

    public Note editNote(String title, String content, Long id) {
        Note note = findNote(id);
        if (note == null) {
            return null;
        }
        note.setTitle(title);
        note.setContent(content);
        return noteRepository.save(note);
    }

    public Note createNote(RawNote note) {
        return noteRepository.save(new Note(note.getTitle(), note.getContent()));
    }

    public Note findNote(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

}
