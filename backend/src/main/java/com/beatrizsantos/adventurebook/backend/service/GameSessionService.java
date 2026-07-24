package com.beatrizsantos.adventurebook.backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.beatrizsantos.adventurebook.backend.model.Book;
import com.beatrizsantos.adventurebook.backend.model.GameSession;
import com.beatrizsantos.adventurebook.backend.model.Section;
import com.beatrizsantos.adventurebook.backend.model.SectionType;
import com.beatrizsantos.adventurebook.backend.repository.GameSessionRepository;

@Service
public class GameSessionService {
    
    private GameSessionRepository gameSessionRepository;

    public GameSessionService(GameSessionRepository gameSessionRepository){
        this.gameSessionRepository = gameSessionRepository;
    }

    public GameSession getGameByBookID(Long bookId){
        Optional<GameSession> game = gameSessionRepository.findByBookId(bookId);
        return game.orElse(null);
    }

    public GameSession getGame(Long id){
        Optional<GameSession> game = gameSessionRepository.findById(id);
        return game.orElse(null);
    }

    public GameSession updateGame(Long id, GameSession body){
        Optional<GameSession> game = gameSessionRepository.findById(id);
        if(game.isPresent()){
            GameSession gameRes = game.get();
            gameRes.setPoints(body.getPoints());
            gameRes.setCurrentSectionId(body.getCurrentSectionId());

            GameSession save = gameSessionRepository.save(gameRes);

            return save;
        }
         return null;
    }

    public GameSession createGame(Book book){
        GameSession game = new GameSession();
        
        Optional<Section> section = book.sections().stream().filter(s -> s.type() == SectionType.BEGIN).findFirst();
        
        if(section.isPresent()){
            game.setBookId(book.id());
            game.setCurrentSectionId(section.get().id());
            game.setPoints(10);

            return gameSessionRepository.save(game);
        } 

        return null;  
    }


}
