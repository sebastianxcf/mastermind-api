package com.scastellanos.mastermind.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scastellanos.mastermind.dto.GameIdResponse;
import com.scastellanos.mastermind.dto.GuessHistoryDTO;
import com.scastellanos.mastermind.dto.PegDTO;
import com.scastellanos.mastermind.dto.ResponseDTO;
import com.scastellanos.mastermind.dto.RestResponseDTO;
import com.scastellanos.mastermind.exceptions.CreationException;
import com.scastellanos.mastermind.exceptions.GuessException;
import com.scastellanos.mastermind.services.GameService;


@Controller
@RequestMapping("/mastermind")
public class MastermindController {

	
	@Autowired
	GameService gameService;
	
	private static final Logger logger = LoggerFactory.getLogger(MastermindController.class);
	
	
	/**
	 * Create a new game.
	 * @return the id of the created game
	 */
	@RequestMapping(value="/new/{codeSize}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestResponseDTO> createNewGame(@PathVariable("codeSize") Integer codeSize) {
		GameIdResponse response;
		try {
			response = gameService.createGame(codeSize);
			
		} catch (CreationException e) {
			logger.error("{}:{}",e.getCode(),e.getMessage());
			return new ResponseEntity<RestResponseDTO>(new RestResponseDTO(e.getCode() , e.getMessage(),null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<RestResponseDTO>(new RestResponseDTO(response), HttpStatus.OK);
	}
	
	
	/**
	 * Process a guess try, return one black peg for each position and color correct, and a White peg for only color corrects.
	 * @param guess
	 * @param gameId
	 * @return
	 */
	@RequestMapping(value="/guess/{gameId}", method = RequestMethod.POST)
	public ResponseEntity<RestResponseDTO>  processGuess(@RequestBody PegDTO[] guess,@PathVariable("gameId")  Long gameId) {
		ResponseDTO response = null;
		try {
			response = gameService.processGuess(guess, gameId);
			return new ResponseEntity<RestResponseDTO>(new RestResponseDTO(response), HttpStatus.OK);
		} catch (GuessException e) {
			logger.error("{}:{}",e.getCode(),e.getMessage());
			return new ResponseEntity<RestResponseDTO>(new RestResponseDTO(e.getCode() , e.getMessage(),null), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	
	/**
	 * Returns the history of tries.
	 * @param gameId
	 * @return
	 */
	@RequestMapping(value="/history/{gameId}", method = RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> getGameHistory( @PathVariable("gameId")  Long gameId) {
		List<GuessHistoryDTO> response = null;
		try {
			response = gameService.getGameHistory(gameId);
		} catch (GuessException e) {
			logger.error("{}:{}",e.getCode(),e.getMessage());
			return new ResponseEntity<RestResponseDTO>(new RestResponseDTO(e.getCode() , e.getMessage(),null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<RestResponseDTO>(new RestResponseDTO(response), HttpStatus.OK);
	}
	
	
	
}
