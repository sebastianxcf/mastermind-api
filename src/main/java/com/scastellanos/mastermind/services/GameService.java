package com.scastellanos.mastermind.services;

import org.springframework.stereotype.Component;

import com.scastellanos.mastermind.dto.GameDTO;
import com.scastellanos.mastermind.dto.GameIdResponse;

@Component
public interface GameService {

	/**
	 * Given a codeSize for the game, creates a new game with codeSize length and a random set of colors using Colors Enum.
	 * @param codeSize
	 * @return GameIdResponse with the generated code and Id.
	 */
	GameIdResponse createGame(int codeSize);
	
	
	GameDTO getGame(Long gameId);
	
	
}
