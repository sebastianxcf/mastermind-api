package com.scastellanos.mastermind.dto;

import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scastellanos.mastermind.entity.Color;

public class PegDTO {

	
	private Color color;
	
	@JsonIgnore
	private boolean alreadyChequed;
	
	
	public PegDTO (Color color){
		this.color = color;
	}
	
	
	public PegDTO (){
		//Create random colors
		Color color; 
		Random random = new Random();
		color = Color.values()[random.nextInt(Color.values().length)];
		this.color = color; 
	}
	
	
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color c) {
		this.color = c;
	}
	
	public boolean isAlreadyChequed() {
		return alreadyChequed;
	}
	public void setAlreadyChequed(boolean alreadyChequed) {
		this.alreadyChequed = alreadyChequed;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object param) {
		PegDTO obj = (PegDTO)param;
		return obj.getColor().name().equals(this.getColor().name());
	}
	
	
}
