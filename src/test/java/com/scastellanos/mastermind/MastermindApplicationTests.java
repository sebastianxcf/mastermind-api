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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
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
import com.scastellanos.mastermind.util.ErrorCodes;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = MastermindApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class MastermindApplicationTests {

	@Autowired
	private GameService gameService;


	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:seed.sql"),
			@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql") })
	public void testNewGameCreationCheckCodeNotNull() throws CreationException, GuessException {
		GameDTO game = gameService.getGame(1L);
		assertNotNull(game.getCode());
	}

	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql") })
	public void testCheckCodeSizeIsOk() throws CreationException, GuessException {
		GameDTO game = gameService.getGame(1L);
		assertEquals(4, game.getCode().getCodeSize());
	}

	@Test(expected = GuessException.class)
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql") })
	public void testGetGameWithWrongId() throws CreationException, GuessException {
		try {
			GameDTO game = gameService.getGame(60L);
		} catch (GuessException e) {
			assertEquals(e.getCode().toString(), ErrorCodes.MM_GUESS_201.getValue());
			throw e;
		}
		Assert.fail("Should throw a GuessException with code " + ErrorCodes.MM_GUESS_201.getValue());
	}

	@Test(expected = CreationException.class)
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql") })
	public void testNewGameCreationCodeNegativeSize() throws CreationException {
		try {
			gameService.createGame(-4);
		} catch (CreationException e) {
			assertEquals(e.getCode().toString(), ErrorCodes.MM_CREATION_101.getValue());
			throw e;
		}
		Assert.fail("Should throw a GuessException with code " + ErrorCodes.MM_CREATION_101.getValue());
	}

	@Test(expected = GuessException.class)
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql") })
	public void testIncorrectGuessSize() throws CreationException, GuessException {
		Color[] guessColors = new Color[3];
		guessColors[0] = Color.WHITE;
		guessColors[1] = Color.ORANGE;
		guessColors[2] = Color.GREEN;

		PegDTO[] guessCode = createGuessCode(guessColors);
		try {
			gameService.processGuess(guessCode, 1L);

		} catch (GuessException e) {
			assertEquals(e.getCode().toString(), ErrorCodes.MM_GUESS_203.getValue());
			throw e;
		}
		Assert.fail("Should throw a GuessException with code " + ErrorCodes.MM_GUESS_203.getValue());
	}

	@Test(expected = GuessException.class)
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql") })
	public void testGameGuessWithNotColorsInPegs() throws GuessException {
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
		try {
			gameService.processGuess(guess, 1L);
		} catch (GuessException e) {
			assertEquals(e.getCode().toString(), ErrorCodes.MM_GUESS_205.getValue());
			throw e;
		}
		Assert.fail("Should throw a GuessException with code " + ErrorCodes.MM_GUESS_205.getValue());
	}

	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql") })
	public void testGuessGameWithNonCorrectColor() throws CreationException, GuessException {

		Color[] guessColors = new Color[4];
		guessColors[0] = Color.WHITE;
		guessColors[1] = Color.ORANGE;
		guessColors[2] = Color.GREEN;
		guessColors[3] = Color.YELLOW;

		PegDTO[] guessCode = createGuessCode(guessColors);

		ResponseDTO r = gameService.processGuess(guessCode, 1L);
		assertEquals(0, r.getOnlyColorGuess().size());
		assertEquals(0, r.getPositionColorGuess().size());
	}

	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql") })
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
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql") })
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
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql") })
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
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql") })
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
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql") })
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
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql") })
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
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql") })
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
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql") })
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
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql") })
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
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql") })
	public void testGameGuessEmptyGameHistory() throws CreationException, GuessException {
		GameIdResponse gameIdResponse = gameService.createGame(4);
		GameDTO game = gameService.getGame(gameIdResponse.getGameId());
		List<GuessHistoryDTO> result = gameService.getGameHistory(game.getId());
		
		assertEquals(result.size(), 0);

		
	}
	
	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql") })
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

	PegDTO[] createGuessCode(Color[] colors) {
		PegDTO[] PegDTOs = new PegDTO[colors.length];
		for (int i = 0; i < colors.length; i++) {
			PegDTO p = new PegDTO(colors[i]);
			PegDTOs[i] = p;
		}
		return PegDTOs;
	}
}
