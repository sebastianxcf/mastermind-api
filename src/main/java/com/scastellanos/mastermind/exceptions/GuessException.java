package com.scastellanos.mastermind.exceptions;

public class GuessException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3849919475404131563L;
	private String code;
	
	
	public GuessException(String message) {
		super(message);
	}
	
	public GuessException(String code,String message) {
		super(message);
		this.code = code;
	}
	
	@Override
    public String getMessage() {
        return super.getMessage();
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
