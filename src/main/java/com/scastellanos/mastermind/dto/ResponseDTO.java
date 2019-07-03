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
	
	private List<PegDTO> onlyColorGuess= new ArrayList<>();
	
	private List<PegDTO> positionColorGuess = new ArrayList();
	

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

	
}
