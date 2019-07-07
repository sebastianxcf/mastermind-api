package com.scastellanos.mastermind.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;

/**
 * 
 * @author scastellanos
 *
 */
/**
 * This Class represents All the guess tries history for a game. It has the guess, the related game and the 
 * response separated in number of blacks and number of whites
 * @author scastellanos
 *
 */
@Entity
public class GuessHistory {

	public GuessHistory() {}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	/**
	 * The list of PegEntitys that conform the code.
	 */
	@OneToMany(mappedBy = "guess", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@OrderColumn(name = "pos")
	private Peg[] pegs;

	/**
	 * The game related with the history. Each history es related only with one game.
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="game_id")
	private Game game;
	
	/**
	 * Represent the number of balls guess in correct position
	 */
	@Column(name = "number_black",length = 10)
	private int numberBlack;
	
	/**
	 * Represent the number of correct balls colors but not in correct position
	 */
	@Column(name = "number_white",length = 10)
	private int numberWhite;
	
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
		this.pegs = new Peg[pegs.length];
		for (int i = 0; i < pegs.length; i++) {
			Peg pegEntity = pegs[i];
			this.pegs[i] = pegEntity;
			pegEntity.setGuess(this);
		}
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

	/**
	 * @return the numberBlack
	 */
	public int getNumberBlack() {
		return numberBlack;
	}

	/**
	 * @param numberBlack the numberBlack to set
	 */
	public void setNumberBlack(int numberBlack) {
		this.numberBlack = numberBlack;
	}

	/**
	 * @return the numberWhite
	 */
	public int getNumberWhite() {
		return numberWhite;
	}

	/**
	 * @param numberWhite the numberWhite to set
	 */
	public void setNumberWhite(int numberWhite) {
		this.numberWhite = numberWhite;
	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(Game game) {
		this.game = game;
	}

}
