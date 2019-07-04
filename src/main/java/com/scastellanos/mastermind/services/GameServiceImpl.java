package com.scastellanos.mastermind.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scastellanos.mastermind.dto.CodeDTO;
import com.scastellanos.mastermind.dto.GameDTO;
import com.scastellanos.mastermind.dto.GameIdResponse;
import com.scastellanos.mastermind.dto.GuessHistoryDTO;
import com.scastellanos.mastermind.dto.PegDTO;
import com.scastellanos.mastermind.dto.ResponseDTO;
import com.scastellanos.mastermind.entity.Code;
import com.scastellanos.mastermind.entity.Color;
import com.scastellanos.mastermind.entity.Game;
import com.scastellanos.mastermind.entity.GuessHistory;
import com.scastellanos.mastermind.entity.Peg;
import com.scastellanos.mastermind.exceptions.CreationException;
import com.scastellanos.mastermind.exceptions.GuessException;
import com.scastellanos.mastermind.repository.GameRepository;
import com.scastellanos.mastermind.repository.GuessHistoryRepository;
import com.scastellanos.mastermind.util.ErrorCodes;
import com.scastellanos.mastermind.util.ServiceUtils;



@Component
public class GameServiceImpl implements GameService{

	@Autowired
	GameRepository gameRepository;
	
	
	@Autowired
	GuessHistoryRepository guessHistoryRepository;
	
	
	/* (non-Javadoc)
	 * @see com.scastellanos.mastermind.services.GameService#createGame(int)
	 */
	@Override
	public GameIdResponse createGame(int codeSize) throws CreationException {
		GameIdResponse response = new GameIdResponse();
		
		Game game = new Game();
		
		Code code = createCode(codeSize);
		
		game.setCode(code);
		
		gameRepository.save(game);
		
		response.setGameId(game.getId());
		
		return response;
		
		
	}
	
	/**
	 * Generate a code composed by a list of Pegs of codeSize size.
	 * @param codeSize determinate the size of the code
	 * @return
	 */
	public Code createCode(int codeSize) throws CreationException{
		//Create a new Code with the code size
		try {
			Code code = new Code(codeSize);
			for(int i= 0; i < code.getCodeSize() ; i++ ) {
				Peg peg = new Peg();
				code.getPegs()[i]= peg;
				peg.setCode(code);
			}
			return code;
		}catch(NegativeArraySizeException e) {
			throw new CreationException(ErrorCodes.MM_CREATION_101.getValue(),ErrorCodes.MM_CREATION_101.getDescription());
			
		}
	}

	/* (non-Javadoc)
	 * @see com.scastellanos.mastermind.services.GameService#getGame(java.lang.Long)
	 */
	@Override
	public GameDTO getGame(Long gameId) {
		Game game = gameRepository.findById(gameId).orElse(null);
		GameDTO dto = ServiceUtils.convertGameEntity2GameDTO(game);
		return dto;
	}


	
	/* (non-Javadoc)
	 * @see com.scastellanos.mastermind.services.GameService#processGuess(com.scastellanos.mastermind.dto.PegDTO[], java.lang.Long)
	 */
	@Override
	public ResponseDTO processGuess(PegDTO[] guess, Long gameId) throws GuessException {
		ResponseDTO response = new ResponseDTO();
		
		Game game = findGameById(gameId); 
		GameDTO gameDTO = ServiceUtils.convertGameEntity2GameDTO(game);;
		
		validateGuessStructure(guess, gameDTO.getCode());
		
		response.getGuess().addAll(Arrays.asList(guess));
		
		//first check position and color correct, in order to avoid removing wrong white pegs then.
		validateColorPosition(guess, response,gameDTO.getCode());
		
		//Second check only color correct
		validateOnlyColor(guess, response,gameDTO.getCode());
		
		createGuessHistory(game, response, guess);
		
		if(response.getOnlyColorGuess().size() == game.getCode().getPegs().length) 
			response.setHasWon(Boolean.TRUE);
		
		return response;
	}
	
	/**
	 * Given a gameId retrieve the game from the in memory DB.
	 * @param gameId
	 * @return
	 * @throws GuessException
	 */
	public Game findGameById(Long gameId) throws GuessException{
		Game game =  gameRepository.findById(gameId).orElse(null);
		if(game!=null) {
			return game;
		}
		throw new GuessException(ErrorCodes.MM_GUESS_201.getValue(),ErrorCodes.MM_GUESS_201.getDescription());
	}
	
	
	/**
	 * Validates that each color is inside of allowed colors.
	 * @param guess
	 * @return
	 */
	private void validateGuessStructure(PegDTO[] guess,CodeDTO code) throws GuessException {
		if(guess != null && guess.length == code.getCodeSize()) {
			for (PegDTO peg : guess) {
				if(peg.getColor()==null) {
					throw new GuessException(ErrorCodes.MM_GUESS_205.getValue(),ErrorCodes.MM_GUESS_205.getDescription());
				}
			}
		}else {
			throw new GuessException(ErrorCodes.MM_GUESS_203.getValue(),ErrorCodes.MM_GUESS_203.getDescription());
		}
	}
	
	/**
	 * A white peg is added to result, if a color peg appears in both code pattern and guess
 	 *  pattern but at different positions.
	 * @param guess
	 * @param result
	 * @param code
	 * @return
	 */
	private ResponseDTO validateColorPosition(PegDTO[] guess,ResponseDTO response,CodeDTO code) {
		PegDTO pegGuess;
		for (int i = 0; i < guess.length; i++) {
			pegGuess = guess[i];
			if(pegGuess!=null) {
				if(code.getPegs()[i].getColor().equals(pegGuess.getColor())){
					//flag the pegs in order to not evaluate it again.
					code.getPegs()[i].setAlreadyChequed(true);
					pegGuess.setAlreadyChequed(true);
					//if its same color and same position, add a Black response
					PegDTO pegBlack = new PegDTO();
					pegBlack.setColor(Color.BLACK);
					response.getPositionColorGuess().add(pegBlack);
					
				}
			}
		}
		return response;
	}
	
	/** A black peg is added to result, if a color peg appears
	 *  at the same positions in both code pattern and guess pattern.
	 * @param guess
	 * @param response
	 * @param code
	 * @return
	 */
	private ResponseDTO validateOnlyColor(PegDTO[] guess,ResponseDTO response,CodeDTO code) {
		PegDTO pegGuess;
		for (int i = 0; i < guess.length; i++) {
			//check only for the same color
			pegGuess = guess[i];
			//In the case that for one peg in the guess there are to same color pegs in the code will return only one WHITE correct peg. 
			//Others variation of the game can consider that the response should be two white pegs.
			if(!pegGuess.isAlreadyChequed() && contains(pegGuess,code.getPegs())) {
				pegGuess.setAlreadyChequed(true);
				PegDTO pegWhite = new PegDTO();
				pegWhite.setColor(Color.WHITE);
				response.getOnlyColorGuess().add(pegWhite);
			}
		}
		return response;
	}
	
	/**
	 * Check if the pegGuess parameter is contained at least one in the pegsCode. In that cases the peg in the code is flagged and the function return true. 
	 * @param pegGuess
	 * @param pegsCode
	 * @return
	 */
	public boolean contains(PegDTO pegGuess,PegDTO[] pegsCode) {
		for (PegDTO peg : pegsCode) {
			if(!peg.isAlreadyChequed() && peg.getColor().name().equals(pegGuess.getColor().name())) {
				peg.setAlreadyChequed(true);
				return true;
			}
		}
		return false;
	}

	/**
	 * Given a game, a list of pegs and the results of process the guess, creates a new entry in guess History.
	 * @param game
	 * @param response
	 * @param guessPegs
	 */
	private void createGuessHistory(Game game,ResponseDTO response,PegDTO[] guessPegs) {
		GuessHistory history = new GuessHistory();
		history.setNumberBlack(response.getPositionColorGuess().size());
		history.setNumberWhite(response.getOnlyColorGuess().size());
		history.setGame(game);
		
		game.setGuess(history);
		
		Peg[] pegsEntity = ServiceUtils.convertPegsDTO2PegsEntity(guessPegs);
		history.setPegs(pegsEntity);
		guessHistoryRepository.save(history);
	}
	
	
	/**
	 * Return the history of guess attempts for a given game id. The response contains the guess, 
	 * and a list of blacks hits and white hits. 
	 */
	@Override
	public List<GuessHistoryDTO> getGameHistory(Long gameId) throws GuessException {
		List<GuessHistory> history =  guessHistoryRepository.findByGameId(gameId);;
		return ServiceUtils.convertGuessEntity2DTO(history);
	}
	
}
