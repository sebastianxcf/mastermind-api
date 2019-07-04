package com.scastellanos.mastermind;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.scastellanos.mastermind.dto.GameDTO;
import com.scastellanos.mastermind.dto.GameIdResponse;
import com.scastellanos.mastermind.dto.GuessHistoryDTO;
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
	
	@Test(expected = GuessException.class)
	public void testIncorrectGuessSize() throws CreationException, GuessException {
		Color[] guessColors = new Color[3];
		guessColors[0] = Color.WHITE;
		guessColors[1] = Color.ORANGE;
		guessColors[2] = Color.GREEN;
		
		PegDTO [] guessCode = createGuessCode(guessColors);
		
		gameService.processGuess(guessCode,1L);
	}
	
	@Test(expected = GuessException.class)
	public void testGameGuessWithNotColorsInPegs() throws Exception {
		PegDTO[] guess = new PegDTO[4];
		PegDTO p1 = new PegDTO();
		PegDTO p2 = new PegDTO();
		PegDTO p3 = new PegDTO();
		PegDTO p4 = new PegDTO();
		p4.setColor(null);
		guess[0] = p1;
		guess[1] = p2;
		guess[2] = p3;
		guess[3] = p4;
		gameService.processGuess(guess, 1L);
	}
	
	@Test
	public void testGuessGameWithNonCorrectColor() throws CreationException, GuessException {
		
		Color[] guessColors = new Color[4];
		guessColors[0] = Color.WHITE;
		guessColors[1] = Color.ORANGE;
		guessColors[2] = Color.GREEN;
		guessColors[3] = Color.YELLOW;
		
		PegDTO [] guessCode = createGuessCode(guessColors);
		
		ResponseDTO r = gameService.processGuess(guessCode,1L);
		assertEquals(0,r.getOnlyColorGuess().size());
		assertEquals(0,r.getPositionColorGuess().size());
	}
	
	
	
	PegDTO[] createGuessCode(Color[] colors) {
		PegDTO[] PegDTOs = new PegDTO[colors.length];
		for (int i = 0; i < colors.length; i++) {
			PegDTO p = new PegDTO(colors[i]);
			PegDTOs[i] = p;
		}
		return PegDTOs;
	}
	
	@Test
	public void testGameGuessWithOnlyOneColorPositionCorrect() throws CreationException, GuessException {
		
		Color[] guessColors = new Color[4];
		guessColors[0] = Color.PINK;
		guessColors[1] = Color.ORANGE;
		guessColors[2] = Color.GREEN;
		guessColors[3] = Color.YELLOW;
		
		PegDTO [] guessCode = createGuessCode(guessColors);
		
		ResponseDTO r = gameService.processGuess(guessCode,1L);
		assertEquals(1,r.getPositionColorGuess().size());
		assertEquals(0,r.getOnlyColorGuess().size());
	}
	
	@Test
	public void testGameGuessWithOneColorAndPositionsCorrect() throws CreationException, GuessException {
		
		
		Color[] guessColors = new Color[4];
		guessColors[0] = Color.PINK;
		guessColors[1] = Color.PURPLE;
		guessColors[2] = Color.GREEN;
		guessColors[3] = Color.YELLOW;
		
		PegDTO [] guessCode = createGuessCode(guessColors);
		
		ResponseDTO r = gameService.processGuess(guessCode,1L);
		assertEquals(1,r.getPositionColorGuess().size());
		assertEquals(1,r.getOnlyColorGuess().size());
	}
	
	@Test
	public void testGameGuessWinGame() throws CreationException, GuessException {
		
		Color[] guessColors = new Color[4];
		guessColors[0] = Color.PINK;
		guessColors[1] = Color.BLUE;
		guessColors[2] = Color.PURPLE;
		guessColors[3] = Color.SILVER;
		
		PegDTO [] guessCode = createGuessCode(guessColors);
		
		ResponseDTO r = gameService.processGuess(guessCode,1L);
		
		assertEquals(4,r.getPositionColorGuess().size());
		assertEquals(0,r.getOnlyColorGuess().size());
	}
	
	@Test
	public void testGuessGameWithOnlyOneColorCorrect() throws CreationException, GuessException  {
		
		Color[] guessColors = new Color[4];
		guessColors[0] = Color.WHITE;
		guessColors[1] = Color.PINK;
		guessColors[2] = Color.GREEN;
		guessColors[3] = Color.YELLOW;
		
		PegDTO [] guessCode = createGuessCode(guessColors);
		
		ResponseDTO r = gameService.processGuess(guessCode,1L);
		assertEquals(1,r.getOnlyColorGuess().size());
		assertEquals(0,r.getPositionColorGuess().size());
	}
	
	/**
	 * In the scenario that we have more PegDTOs of correct color that the code has,it will count only one for each PegDTO in the code.
	 * @throws Exception
	 */
	@Test
	public void testGuessWhenMoreThanOneSameColorInTheGuessButOnlyOneInTheCode() throws CreationException, GuessException {
		
		Color[] guessColors = new Color[4];
		guessColors[0] = Color.WHITE;
		guessColors[1] = Color.PINK;
		guessColors[2] = Color.PINK;
		guessColors[3] = Color.PINK;
		
		PegDTO [] guessCode = createGuessCode(guessColors);
		
		ResponseDTO r = gameService.processGuess(guessCode,1L);
		assertEquals(1,r.getOnlyColorGuess().size());
		assertEquals(0,r.getPositionColorGuess().size());
	}
	
	/**
	 * In this scenario should return only one Black response even though in the guess there are two same correct colors. 
	 *  
	 * @throws CreationException
	 * @throws GuessException
	 */
	@Test
	public void testGameGuessWhichPrioritizePositionCorrectThanColorCorrect() throws CreationException, GuessException {
		
		Color[] guessColors = new Color[4];
		guessColors[0] = Color.BLUE;
		guessColors[1] = Color.BLUE;
		guessColors[2] = Color.GREEN;
		guessColors[3] = Color.YELLOW;
		
		PegDTO [] guessCode = createGuessCode(guessColors);
		ResponseDTO r = gameService.processGuess(guessCode,1L);
		assertEquals(1,r.getPositionColorGuess().size());
		assertEquals(0,r.getOnlyColorGuess().size());
	}
	
	/**
	 * This scenario test the case in which we have one color in the guess but more than one in the code, in this case will consider only one point. 
	 * @throws CreationException
	 * @throws GuessException
	 */
	@Test
	public void testGameGuessWithLessBallsInGuessThanInCode() throws CreationException, GuessException {
		
		
		Color[] guessColors = new Color[4];
		guessColors[0] = Color.ORANGE;
		guessColors[1] = Color.SILVER;
		guessColors[2] = Color.GREEN;
		guessColors[3] = Color.YELLOW;
		
		PegDTO [] guessCode = createGuessCode(guessColors);
		
		ResponseDTO r = gameService.processGuess(guessCode,2L);
		assertEquals(0,r.getPositionColorGuess().size());
		assertEquals(1,r.getOnlyColorGuess().size());
	}
	
	@Test
	public void testGameGuessOnlyColorsCorrect() throws CreationException, GuessException {
		
		Color[] guessColors = new Color[4];
		guessColors[0] = Color.BLUE;
		guessColors[1] = Color.PINK;
		guessColors[2] = Color.SILVER;
		guessColors[3] = Color.PURPLE;
		
		PegDTO [] guessCode = createGuessCode(guessColors);
		
		ResponseDTO r = gameService.processGuess(guessCode,1L);
		assertEquals(0,r.getPositionColorGuess().size());
		assertEquals(4,r.getOnlyColorGuess().size());
	}
	
	
	@Test
	public void testGameGuessWithGameHistory() throws CreationException, GuessException {
		GameIdResponse gameIdResponse = gameService.createGame(4);
		GameDTO game = gameService.getGame(gameIdResponse.getGameId());
		
		
		Color[] guessColors = new Color[4];
		guessColors[0] = Color.PINK;
		guessColors[1] = Color.BLUE;
		guessColors[2] = Color.PURPLE;
		guessColors[3] = Color.SILVER;
		
		PegDTO [] guessCode = createGuessCode(guessColors);
		gameService.processGuess(guessCode,game.getId());
		
		
		List<GuessHistoryDTO> gameHistory = gameService.getGameHistory(game.getId());
		
		Assert.assertThat(guessCode, is(gameHistory.get(0).getPegs()));
		
	}
	
	@Test
	public void testGameGuessEmptyGameHistory() throws CreationException, GuessException {
		GameIdResponse gameIdResponse = gameService.createGame(4);
		GameDTO game = gameService.getGame(gameIdResponse.getGameId());
		List<GuessHistoryDTO> result = gameService.getGameHistory(game.getId());
		
		assertEquals(result.size(), 0);

		
	}
	
	@Test
	public void testGameGuessWithNonEmptyGameHistory() throws CreationException, GuessException {
		GameIdResponse gameIdResponse = gameService.createGame(4);
		GameDTO game = gameService.getGame(gameIdResponse.getGameId());
		
		Color[] guessColors = new Color[4];
		guessColors[0] = Color.PINK;
		guessColors[1] = Color.BLUE;
		guessColors[2] = Color.PURPLE;
		guessColors[3] = Color.SILVER;
		
		PegDTO [] guessCode = createGuessCode(guessColors);
		
		gameService.processGuess(guessCode,game.getId());
		
		List<GuessHistoryDTO> result = gameService.getGameHistory(game.getId());
		assertTrue(!result.isEmpty());
		
	}
}
