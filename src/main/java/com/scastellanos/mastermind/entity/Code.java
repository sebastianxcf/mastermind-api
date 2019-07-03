package com.scastellanos.mastermind.entity;

/**
 * 
 * @author scastellanos
 *
 */
public class Code {

	public Code() {}
	
	Long id;
	
	
	/**
	 * The list of PegEntitys that conform the code.
	 */
	private Peg[] pegs;
	
	
	/**
	 * The length of the code.
	 */
	private int codeSize;
	
	
	public Code(int codeSize) {
		pegs = new Peg[codeSize];
		this.codeSize= codeSize;
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

	/**
	 * @return the pegs
	 */
	public Peg[] getPegs() {
		return pegs;
	}

	/**
	 * @param pegs the pegs to set
	 */
	public void setPegs(Peg[] pegs) {
		this.pegs = pegs;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
