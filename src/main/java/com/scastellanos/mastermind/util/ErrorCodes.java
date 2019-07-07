package com.scastellanos.mastermind.util;

public enum ErrorCodes {
	//1 System
	//2 Method
	//3 Number
	
	MM_CREATION_101("1101","Error creating game, incorrect code size"),
	MM_GUESS_201("1201","Error retrieving the game"),
	MM_GUESS_203("1203","Invalid guess length"),
	MM_GUESS_204("1204","Colors non-existent in the game"),
	MM_GUESS_205("1205","Null pegs in the guess");
	
	
	private String code;
	private final String description;
	
	

	ErrorCodes(String code,String desc) { this.code = code; this.description = desc;}

    
    
    
    public String getValue() { return code; }
    
    
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.code = value;
	}
	
	 @Override
	  public String toString() {
	    return code + ": " + description;
	  }
    

}
