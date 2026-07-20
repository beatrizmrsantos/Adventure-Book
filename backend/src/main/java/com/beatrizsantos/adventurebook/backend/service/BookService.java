package com.beatrizsantos.adventurebook.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.beatrizsantos.adventurebook.backend.model.Book;
import com.beatrizsantos.adventurebook.backend.model.Consequence;
import com.beatrizsantos.adventurebook.backend.model.ConsequenceType;
import com.beatrizsantos.adventurebook.backend.model.Option;
import com.beatrizsantos.adventurebook.backend.model.Section;
import com.beatrizsantos.adventurebook.backend.model.SectionType;

@Service
public class BookService {

    private final List<Book> books = List.of(
        new Book(1L, "The Prisoner", "Daniel El Fuego", "A book about the history of a prisoner and their adevnture", List.of(
            new Section(1L,
                "You wake up in what seems to be a dark prison cell, on an old wooden bed. Metal bars are preventing you to escape from the room. There is no window.",
                SectionType.BEGIN,
                List.of(
                    new Option("You try to open the door", 500L),
                    new Option("You look under the bed", 20L)
                )
            ),
            new Section(2L,
                "You don't see anything, it's too dark.",
                SectionType.NODE,
                List.of(
                    new Option("Try to scan the area with your hands", 30L,
                        new Consequence(ConsequenceType.LOSE_HEALTH, 6,
                            "As you move your hands left and right under the bed, you cut yourself on a rusty nail."))
                )
            )
        ))
    );

    public List<Book> getAllBooks(){
        return books;
    }

    
}
