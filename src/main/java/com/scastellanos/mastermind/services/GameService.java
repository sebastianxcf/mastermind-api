package com.scastellanos.mastermind.services;

import org.springframework.stereotype.Component;

import com.scastellanos.mastermind.dto.GameDTO;
import com.scastellanos.mastermind.dto.GameIdResponse;
import com.scastellanos.mastermind.exceptions.CreationException;

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
	   */ 
	GameDTO getGame(Long gameId);
	
	
}
