package com.scastellanos.mastermind.exceptions;

public class CreationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8350088277683636392L;

	private String code;
	
	
	public CreationException(String message) {
		super(message);
	}
	
	public CreationException(String code,String message) {
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
