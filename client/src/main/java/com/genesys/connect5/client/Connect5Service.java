package com.genesys.connect5.client;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.genesys.connect5.domain.Game;
import com.genesys.connect5.domain.Move;
import com.genesys.connect5.domain.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
/**
 * Connect5Service to connect to server over http
 * 
 * @author Karen Rawlinson
 *
 */
@Component
public class Connect5Service {
	
	private String gameServerUrl = "http://localhost:8080/game";
	@Autowired
	RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	public Player joinGame() {
		Player player = restTemplate.postForObject(gameServerUrl, null, Player.class);
		return player;
	}
	
	public Game move(long i, int column, int player) {
		Move move = new Move();
		move.setColumn(column);
		move.setPlayer(player);
		Game game = restTemplate.postForObject(gameServerUrl + "/" + i + "/move", move, Game.class);
		return game;
	}

	public Game getGame(Long gameId) {
		Game game = restTemplate.getForObject(gameServerUrl + "/" + gameId, Game.class);
		return game;
	}
}
