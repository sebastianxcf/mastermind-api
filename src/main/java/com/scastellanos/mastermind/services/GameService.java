package com.scastellanos.mastermind.services;

import org.springframework.stereotype.Component;

import com.scastellanos.mastermind.dto.GameIdResponse;

@Component
public interface GameService {

	/**
	 * given a codeSize, generate a new game.
	 * @param codeSize
	 * @return GameIdResponse with the generated code and Id.
	 */
	GameIdResponse createGame(int codeSize);
	
	
}
