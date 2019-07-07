package com.scastellanos.mastermind.dto;

public class CodeDTO {

	
	private int codeSize;
	
	private PegDTO[] pegs;

	
	
	public CodeDTO() {
	}
	
	public CodeDTO(PegDTO[] Peg) {
		this.pegs = Peg;
		this.codeSize= Peg.length;
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

	/**
	 * @return the codeSize
	 */
	public int getCodeSize() {
		return codeSize;
	}

	/**
	 * @param codeSize the codeSize to set
	 */
	public void setCodeSize(int codeSize) {
		this.codeSize = codeSize;
	}
}
