package com.beatrizsantos.mapper;

import com.beatrizsantos.adventurebook.backend.model.GameSession;
import com.beatrizsantos.adventurebook.backend.model.GameSessionDTO;

public class GameSessionMapper {

    public static GameSessionDTO toDTO(GameSession game){
        return new GameSessionDTO(game.getId(), game.getBookId(), game.getCurrentSectionId(), game.getPoints());
    }

    public static GameSession toEntity(GameSessionDTO game){
        return new GameSession(game.getId(), game.getBookId(), game.getCurrentSectionId(), game.getPoints());
    }
    
}
