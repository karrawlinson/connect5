package com.genesys.connect5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesys.connect5.controller.GameController;
import com.genesys.connect5.domain.Game;
import com.genesys.connect5.domain.Game.GameStatus;
import com.genesys.connect5.domain.Move;
import com.genesys.connect5.domain.Player;
import com.genesys.connect5.service.GameService;

@WebMvcTest(GameController.class)
public class GameControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GameService service;

	@Autowired
	private ObjectMapper objectMapper;
	  
	@Test
	public void joinGameTest() throws Exception {
		when(service.joinPendingGame()).thenReturn(new Player(1, new Long("394808293084")));
		this.mockMvc.perform(post("/game")).andDo(print()).andExpect(status().isOk())
		.andExpect(content().json("{'playerId': 1, 'gameId': 394808293084 }"));
	}
	
	@Test
	public void getGameOkTest() throws Exception {
		when(service.getGame(new Long(1))).thenReturn(new Game());
		this.mockMvc.perform(get("/game/1")).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void getGameNotFoundTest() throws Exception {
		
		this.mockMvc.perform(get("/game/2")).andDo(print()).andExpect(status().isNotFound());
		
	}
	
	@Test
	public void invalidMoveTest1() throws Exception {
		Game game = new Game();
		game.setCurrentStatus(GameStatus.COMPLETE);
		game.setCurrentPlayer(1);
		when(service.getGame(game.getId())).thenReturn(game);
		this.mockMvc.perform(post("/game/" + game.getId() + "/move").contentType("application/json")
				.content(objectMapper.writeValueAsString(new Move(1, 1))))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(result -> assertEquals("The game is not in progress!", result.getResponse().getErrorMessage()));
		
	}
	
	@Test
	public void invalidMoveTest2() throws Exception {
		Game game = new Game();
		game.setCurrentStatus(GameStatus.COMPLETE);
		game.setCurrentPlayer(2);
		when(service.getGame(game.getId())).thenReturn(game);
		this.mockMvc.perform(post("/game/" + game.getId() + "/move").contentType("application/json")
				.content(objectMapper.writeValueAsString(new Move(1, 1))))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(result -> assertEquals("It is not your move!", result.getResponse().getErrorMessage()));
		
	}
	
	@Test
	public void validMoveTest() throws Exception {
		Game game = new Game();
		game.setCurrentStatus(GameStatus.IN_PROGRESS);
		game.setCurrentPlayer(1);
		when(service.getGame(game.getId())).thenReturn(game);
		this.mockMvc.perform(post("/game/" + game.getId() + "/move").contentType("application/json")
				.content(objectMapper.writeValueAsString(new Move(1, 1)))).andDo(print()).andExpect(status().isOk());

	}
}