package com.scastellanos.mastermind.entity;


public class Game {

	
	public Game() {
	}
	
	/**
	 * Game id
	 */
	private Long id;
	
	
	/**
	 * The code auto-generated. This code should not be serialized in the response.
	 */
	private Code code;
	

	/**
	 * @return the code
	 */
	public Code getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Code code) {
		this.code = code;
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
