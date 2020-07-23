package com.genesys.connect4.client;

import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.genesys.connect5.client.Connect5Service;
import com.genesys.connect5.domain.Game;
import com.genesys.connect5.domain.Player;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(classes=Connect5Service.class)
public class Connect5ServiceTests {

	
	@Autowired
	Connect5Service gameService;

	@MockBean
	private RestTemplate restTemplate;
	   
	
	@Test
	public void testGetGame() throws JsonProcessingException, URISyntaxException {
		Game testGame = new Game();
		testGame.setId(new Long(1));
		Mockito.when(restTemplate.getForObject("http://localhost:8080/game/1", Game.class)).thenReturn(testGame);
		Game game = gameService.getGame(new Long(1));
		assert(game != null);
		assert(game.getId() == testGame.getId());	 
		
	}
	
	@Test
	public void testJoinGame() throws JsonProcessingException, URISyntaxException {
		Player testPlayer = new Player(1, 10);
		Mockito.when(restTemplate.postForObject("http://localhost:8080/game", null, Player.class)).thenReturn(testPlayer);
		Player player = gameService.joinGame();
		assert(player != null);
		assert(player.getGameId() == testPlayer.getGameId());	 
		assert(player.getPlayerId() == testPlayer.getPlayerId());
		
	}

	
}
