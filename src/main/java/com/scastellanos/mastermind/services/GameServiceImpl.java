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
import com.scastellanos.mastermind.entity.Game;
import com.scastellanos.mastermind.entity.Peg;
import com.scastellanos.mastermind.exceptions.CreationException;
import com.scastellanos.mastermind.repository.GameRepository;

@Component
public class GameServiceImpl implements GameService{

	Map<Long,Game> games = new HashMap<Long,Game>();
	
	@Autowired
	GameRepository gameRespository;
	
	
	
	/* (non-Javadoc)
	 * @see com.scastellanos.mastermind.services.GameService#createGame(int)
	 */
	@Override
	public GameIdResponse createGame(int codeSize) throws CreationException {
		GameIdResponse response = new GameIdResponse();
		
		Game game = new Game();
		
		Code code = createCode(codeSize);
		game.setCode(code);
		
		gameRespository.save(game);
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
		Game game = gameRespository.findById(gameId).orElse(null);
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
	public ResponseDTO processGuess(PegDTO[] guess, Long gameId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
