package com.beatrizsantos.adventurebook.backend.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beatrizsantos.adventurebook.backend.model.Book;
import com.beatrizsantos.adventurebook.backend.model.GameSession;
import com.beatrizsantos.adventurebook.backend.model.GameSessionDTO;
import com.beatrizsantos.adventurebook.backend.service.BookService;
import com.beatrizsantos.adventurebook.backend.service.GameSessionService;
import com.beatrizsantos.mapper.GameSessionMapper;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:4200")
public class GameSessionController {

    private GameSessionService gameSessionService;

    private BookService bookService;

    public GameSessionController(GameSessionService gameSessionService, BookService bookService){
        this.gameSessionService = gameSessionService;
        this.bookService = bookService;
    }

    @PostMapping("/{bookId}")
    public ResponseEntity<?> startGame(@PathVariable Long bookId){
        if (bookId <= 0) {
            return ResponseEntity.badRequest().body("Book ID must be greater than 0.");
        }

        Optional<Book> book = bookService.getAllBooks().stream()
            .filter(b -> b.id().equals(bookId))
            .findFirst();
        
        if(book.isEmpty()){
            return ResponseEntity.badRequest().body("Book does not exist.");
        }

        GameSession game = gameSessionService.createGame(book.get());
        if(game == null){
            return ResponseEntity.badRequest().body("Book does not have a beggining.");
        }

        return ResponseEntity.ok(GameSessionMapper.toDTO(game));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getGame(@PathVariable Long bookId){
        if (bookId <= 0) {
            return ResponseEntity.badRequest().body("Book ID must be greater than 0.");
        }

        GameSession game = gameSessionService.getGameByBookID(bookId);
        if(game == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doesn't exist a game for this book.");
        }

        return ResponseEntity.ok(GameSessionMapper.toDTO(game));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGame(@PathVariable Long id, @Valid @RequestBody GameSessionDTO body){
        if (id <= 0) {
            return ResponseEntity.badRequest().body("ID must be greater than 0.");
        }

        GameSession game = gameSessionService.updateGame(id, GameSessionMapper.toEntity(body));
        if(game == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doesn't exist a game for this ID.");
        }

        return ResponseEntity.ok(GameSessionMapper.toDTO(game));
    }
    
}
