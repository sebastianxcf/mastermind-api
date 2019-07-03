package com.scastellanos.mastermind.dto;

public class GameDTO {
	
	private Long id;
	
	private CodeDTO code;
	

	/**
	 * @return the code
	 */
	public CodeDTO getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(CodeDTO code) {
		this.code = code;
	}

	/**
	 * @return the realId
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param realId the realId to set
	 */
	public void setId(Long realId) {
		this.id = realId;
	}

}
