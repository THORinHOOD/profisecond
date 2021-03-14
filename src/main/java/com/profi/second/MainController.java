package com.profi.second;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes")
public class MainController {

    private final NoteService noteService;

    public MainController(NoteService noteService) {
        this.noteService = noteService;
    }

    @ApiOperation(value = "Удаление записи по его id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Запись удалена удачно"),
            @ApiResponse(code = 404, message = "Запись не найдена")
    }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity deleteNote(@PathVariable Long id) {
        if (!noteService.deleteNote(id)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @ApiOperation(value = "Получение всех записей по query (если есть), если его нет, тогда все")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Все записи получены")
    }
    )
    @GetMapping
    public ResponseEntity<Iterable<Note>> getAllNotes(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(noteService.findAllNotes(query));
    }


    @ApiOperation(value = "Создание записи по title (не обязателен) и по content", response = Note.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Запись создана")
    }
    )
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody RawNote note) {
        return ResponseEntity.ok(noteService.createNote(note));
    }

    @ApiOperation(value = "Получение записи по id", response = Note.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Запись найдена"),
            @ApiResponse(code = 404, message = "Запись не найдена")
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNote(@PathVariable Long id) {
        Note note = noteService.findNote(id);
        if (note == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(note);
    }

    @ApiOperation(value = "Обновление записи, его title (не обязателен) и content", response = Note.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Запись обновлена"),
            @ApiResponse(code = 404, message = "Запись не найдена")
    }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Note> edit(@RequestBody RawNote note, @PathVariable Long id) {
        Note noteEdited = noteService.editNote(note.getTitle(), note.getContent(), id);
        if (noteEdited == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(noteEdited);
    }

}
