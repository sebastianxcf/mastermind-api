package com.scastellanos.mastermind.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scastellanos.mastermind.dto.GameDTO;
import com.scastellanos.mastermind.dto.GameIdResponse;
import com.scastellanos.mastermind.dto.GuessHistoryDTO;
import com.scastellanos.mastermind.dto.PegDTO;
import com.scastellanos.mastermind.dto.ResponseDTO;
import com.scastellanos.mastermind.exceptions.CreationException;
import com.scastellanos.mastermind.exceptions.GuessException;

@Component
public interface GameService {

	/**
	 * Given a codeSize for the game, creates a new game with codeSize length and a random set of colors using Colors Enum.
	 * @param codeSize
	 * @return GameIdResponse with the generated code and Id.
	 */
	GameIdResponse createGame(int codeSize) throws CreationException;
	
	 /** 
	   * Given an id, return the DTO representation of the Game  
	   * @param gameId 
	   * @return 
	 * @throws GuessException 
	   */ 
	GameDTO getGame(Long gameId) throws GuessException;
	
	
	/**
	 *  Given a guess and a gameId, return a response object with the details of the guess.
	 *  A black peg is given, if a color peg appears
	 *  at the same positions in both code pattern and guess pattern.
 	 *  A white peg is given, if a color peg appears in both code pattern and guess
 	 *  pattern but at different positions. 
	 * @param answer
	 * @param code
	 * @return
	 * @throws GuessException
	 */
	ResponseDTO processGuess(PegDTO[] guess, Long gameId) throws GuessException;
	
	
	/**
	 * For a given game, retrieve all guess tries history. 
	 * @param gameId
	 * @return
	 * @throws GuessException
	 */
	List<GuessHistoryDTO> getGameHistory(Long gameId) throws GuessException;
	
}
