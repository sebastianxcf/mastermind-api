package com.scastellanos.mastermind.dto;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents the feedback from comparing a guess against a game code 
 * answer in a game of Mastermind.
 * 
 * @author scastellanos
 *
 */
public class ResponseDTO {
	
	private List<PegDTO> guess = new ArrayList();
	
	private List<PegDTO> onlyColorGuess= new ArrayList<>();
	
	private List<PegDTO> positionColorGuess = new ArrayList();
	
	private boolean hasWon;
	
	
	public List<PegDTO> getOnlyColorGuess() {
		return onlyColorGuess;
	}

	public void setOnlyColorGuess(List<PegDTO> onlyColorGuess) {
		this.onlyColorGuess = onlyColorGuess;
	}

	public List<PegDTO> getPositionColorGuess() {
		return positionColorGuess;
	}

	public void setPositionColorGuess(List<PegDTO> positionColorGuess) {
		this.positionColorGuess = positionColorGuess;
	}

	/**
	 * @return the hasWon
	 */
	public boolean isHasWon() {
		return hasWon;
	}

	/**
	 * @param hasWon the hasWon to set
	 */
	public void setHasWon(boolean hasWon) {
		this.hasWon = hasWon;
	}

	/**
	 * @return the guess
	 */
	public List<PegDTO> getGuess() {
		return guess;
	}

	/**
	 * @param guess the guess to set
	 */
	public void setGuess(List<PegDTO> guess) {
		this.guess = guess;
	}

	
}
