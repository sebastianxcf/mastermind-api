package com.scastellanos.mastermind.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scastellanos.mastermind.dto.CodeDTO;
import com.scastellanos.mastermind.dto.GameDTO;
import com.scastellanos.mastermind.dto.GameIdResponse;
import com.scastellanos.mastermind.dto.PegDTO;
import com.scastellanos.mastermind.dto.ResponseDTO;
import com.scastellanos.mastermind.entity.Code;
import com.scastellanos.mastermind.entity.Color;
import com.scastellanos.mastermind.entity.Game;
import com.scastellanos.mastermind.entity.Peg;
import com.scastellanos.mastermind.exceptions.CreationException;
import com.scastellanos.mastermind.exceptions.GuessException;
import com.scastellanos.mastermind.repository.GameRepository;



@Component
public class GameServiceImpl implements GameService{

	Map<Long,Game> games = new HashMap<Long,Game>();
	
	@Autowired
	GameRepository gameRepository;
	
	
	
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
		
		games.put(game.getId(), game);
		
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
			throw new CreationException("101","Error creating game, incorrect code size");
			
		}
	}

	/* (non-Javadoc)
	 * @see com.scastellanos.mastermind.services.GameService#getGame(java.lang.Long)
	 */
	@Override
	public GameDTO getGame(Long gameId) {
		Game game = gameRepository.findById(gameId).orElse(null);
		GameDTO dto = convertGameEntity2GameDTO(game);
		return dto;
	}

	/**
	 * Convert a Game pojo to a DTO object in order to avoid using entity business outside service layer.
	 * @param game
	 * @return
	 */
	public  GameDTO convertGameEntity2GameDTO(Game game) {
		GameDTO gameDTO = new GameDTO();
		gameDTO.setId(game.getId());
		
		CodeDTO code = new CodeDTO();
		if(game.getCode()!=null) {
			code.setCodeSize(game.getCode().getCodeSize());
			code.setPegs(convertPegsEntity2PegsDTO(game.getCode().getPegs()));
			gameDTO.setCode(code);
		}
		
		return gameDTO;
	}
	
	/**
	 * Convert a Peg pojo to a DTO object in order to avoid using entity business outside service layer.
	 * @param pegsEntity
	 * @return
	 */
	public  PegDTO[] convertPegsEntity2PegsDTO(Peg[] pegsEntity) {
		PegDTO[] pegsDTO = new PegDTO[pegsEntity.length];
		for (int i = 0; i < pegsEntity.length; i++) {
			PegDTO dto = new PegDTO();
			dto.setColor(pegsEntity[i].getColor());
			pegsDTO[i] = dto;
		}
		
		return pegsDTO;
	}

	
	/* (non-Javadoc)
	 * @see com.scastellanos.mastermind.services.GameService#processGuess(com.scastellanos.mastermind.dto.PegDTO[], java.lang.Long)
	 */
	@Override
	public ResponseDTO processGuess(PegDTO[] guess, Long gameId) throws GuessException {
		ResponseDTO response = new ResponseDTO();
		
		Game game = findGameById(gameId); 
		GameDTO gameDTO = convertGameEntity2GameDTO(game);;
		
		validateGuessStructure(guess, gameDTO.getCode());
		
		//first check position and color correct, in order to avoid removing wrong white pegs then.
		validateColorPosition(guess, response,gameDTO.getCode());
		
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
		throw new GuessException("201","Error retrieving the game");
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
					throw new GuessException("1205","Null pegs in the guess");
				}
			}
		}else {
			throw new GuessException("203","Invalid guess length");
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
}
