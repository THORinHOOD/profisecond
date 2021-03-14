package com.profi.second;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

    List<Note> findByTitleContaining(String title);
    List<Note> findByContentContaining(String content);

}
