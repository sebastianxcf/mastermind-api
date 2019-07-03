package com.scastellanos.mastermind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.scastellanos.mastermind.dto.GameDTO;
import com.scastellanos.mastermind.dto.GameIdResponse;
import com.scastellanos.mastermind.dto.PegDTO;
import com.scastellanos.mastermind.dto.ResponseDTO;
import com.scastellanos.mastermind.entity.Color;
import com.scastellanos.mastermind.exceptions.CreationException;
import com.scastellanos.mastermind.exceptions.GuessException;
import com.scastellanos.mastermind.services.GameService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = MastermindApplication.class)
@TestPropertySource(locations="classpath:application-test.properties")
public class MastermindApplicationTests {

	
	@Autowired
	private GameService gameService;
	
	@Test
	public void contextLoads() {
	}
	
	
	@Test
	public void testcreateNewGame() throws CreationException {
		
		GameIdResponse gameIdResponse = gameService.createGame(4);
		assertNotNull(gameIdResponse);
	}
	
	@Test
	public void testNewGameCreationCheckCodeNotNull() throws CreationException{
		GameDTO game = gameService.getGame(2L);
		assertNotNull(game.getCode());
	}
	
	@Test
	public void testCheckCodeSizeIsOk() throws CreationException{
		GameDTO game = gameService.getGame(2L);
		assertEquals(4,game.getCode().getCodeSize());
	}

	
	@Test(expected = CreationException.class)
	public void testNewGameCreationCodeNegativeSize() throws CreationException {
		gameService.createGame(-4);
	}
	
	@Test
	public void testGuessGame() throws CreationException, GuessException {
		PegDTO [] guessCode = new PegDTO[4];
		ResponseDTO r = gameService.processGuess(guessCode,1L);
		assertNotNull(r);
	}
	
	@Test(expected = GuessException.class)
	public void testIncorrectGuessSize() throws CreationException, GuessException {
		Color[] guessColors = new Color[3];
		guessColors[0] = Color.WHITE;
		guessColors[1] = Color.ORANGE;
		guessColors[2] = Color.GREEN;
		
		PegDTO [] guessCode = createGuessCode(guessColors);
		
		gameService.processGuess(guessCode,1L);
	}
	
	
	
	PegDTO[] createGuessCode(Color[] colors) {
		PegDTO[] PegDTOs = new PegDTO[colors.length];
		for (int i = 0; i < colors.length; i++) {
			PegDTO p = new PegDTO(colors[i]);
			PegDTOs[i] = p;
		}
		return PegDTOs;
	}
}
