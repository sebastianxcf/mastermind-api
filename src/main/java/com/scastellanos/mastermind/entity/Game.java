package com.scastellanos.mastermind.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "game")
public class Game {

	
	public Game() {
	}
	
	/**
	 * Game id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="code")
	private Code code;
	
	@OneToOne(mappedBy = "game",
            fetch = FetchType.EAGER, optional = false)
	private GuessHistory guess;

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

	/**
	 * @return the guess
	 */
	public GuessHistory getGuess() {
		return guess;
	}

	/**
	 * @param guess the guess to set
	 */
	public void setGuess(GuessHistory guess) {
		this.guess = guess;
	}


	
	
}
