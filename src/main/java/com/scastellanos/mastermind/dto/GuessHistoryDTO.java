package com.scastellanos.mastermind.dto;

/**
 * 
 * @author scastellanos
 *
 */
public class GuessHistoryDTO {

	public GuessHistoryDTO() {}
	
	/**
	 * The list of PegEntitys that conform the code.
	 */
	private PegDTO[] pegs;

	private Long game;
	
	private int numberBlack;
	
	private int numberWhite;
	

	/**
	 * @return the numberBlack
	 */
	public int getNumberBlack() {
		return numberBlack;
	}

	/**
	 * @param numberBlack the numberBlack to set
	 */
	public void setNumberBlack(int numberBlack) {
		this.numberBlack = numberBlack;
	}

	/**
	 * @return the numberWhite
	 */
	public int getNumberWhite() {
		return numberWhite;
	}

	/**
	 * @param numberWhite the numberWhite to set
	 */
	public void setNumberWhite(int numberWhite) {
		this.numberWhite = numberWhite;
	}

	/**
	 * @return the game
	 */
	public Long getGame() {
		return game;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(Long game) {
		this.game = game;
	}

	/**
	 * @return the pegs
	 */
	public PegDTO[] getPegs() {
		return pegs;
	}

	/**
	 * @param pegs the pegs to set
	 */
	public void setPegs(PegDTO[] pegs) {
		this.pegs = pegs;
	}

}
