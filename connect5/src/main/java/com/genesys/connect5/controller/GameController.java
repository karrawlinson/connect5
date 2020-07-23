package com.genesys.connect5.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.genesys.connect5.domain.Game;
import com.genesys.connect5.domain.Player;
import com.genesys.connect5.domain.Move;
import com.genesys.connect5.service.GameService;

@RestController()
public class GameController {
	
	Logger logger = LoggerFactory.getLogger(GameController.class);

	@Autowired
	GameService gameService;
	
	@GetMapping("/game/{id}")
	public Game getGame(@PathVariable("id") Long id) {
		Game game = gameService.getGame(id);
		if(game != null) {
			return game;
		}else {
			 throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game Not Found");
		}
		
	}
	
	@PostMapping("/game")
	public Player joinGame() {
		
		return gameService.joinPendingGame();
		
	}
	
	@PostMapping("/game/{id}/move")
	public Game move(@PathVariable("id") Long id, @RequestBody Move move) {
		
		Game game = gameService.getGame(id);
		if (game.getCurrentPlayer() != move.getPlayer()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "It is not your move!");
		}else if (!(game.getCurrentStatus().equals(Game.GameStatus.IN_PROGRESS))){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The game is not in progress!");
		}else if(move.getColumn() < 0 || move.getColumn() > 8){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid column!");
		}else {
			game = gameService.playMove(game, move);
		}
		return game;
	}
}
