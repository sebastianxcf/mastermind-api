package com.scastellanos.mastermind.services;

import org.springframework.stereotype.Component;

import com.scastellanos.mastermind.dto.GameDTO;
import com.scastellanos.mastermind.dto.GameIdResponse;
import com.scastellanos.mastermind.entity.Code;
import com.scastellanos.mastermind.entity.Game;
import com.scastellanos.mastermind.entity.Peg;

@Component
public class GameServiceImpl implements GameService{

	
	/* (non-Javadoc)
	 * @see com.scastellanos.mastermind.services.GameService#createGame(int)
	 */
	@Override
	public GameIdResponse createGame(int codeSize) {
		GameIdResponse response = new GameIdResponse();
		
		Game game = new Game();
		
		Code code = createCode(codeSize);
		game.setCode(code);
		
		response.setGameId(game.getId());
		
		return response;
		
		
	}
	
	/**
	 * Generate a code composed by a list of Pegs of codeSize size.
	 * @param codeSize determinate the size of the code
	 * @return
	 */
	public Code createCode(int codeSize){
		//Create a new Code with the code size
		Code code = new Code(codeSize);
				
		for(int i= 0; i < code.getCodeSize() ; i++ ) {
			Peg peg = new Peg();
			code.getPegs()[i]= peg;
			peg.setCode(code);
		}
		return code;
	}

	@Override
	public GameDTO getGame(Long gameId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
