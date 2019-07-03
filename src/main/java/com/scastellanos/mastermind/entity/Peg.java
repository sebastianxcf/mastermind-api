package com.scastellanos.mastermind.entity;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Peg implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7271548057445812355L;

	@Id
	String id;

	@Enumerated(value = EnumType.STRING)
	private Color color;
	
	@ManyToOne
	@JoinColumn(name="code_id")
	private Code code;
	
	
	public Peg (Color color){
		this.id = UUID.randomUUID().toString();
		this.color = color;
	}
	
	
	public Peg (){
		this.id = UUID.randomUUID().toString();
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


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Peg other = (Peg) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (color != other.color)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


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



	
}
