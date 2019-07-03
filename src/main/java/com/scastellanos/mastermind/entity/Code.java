package com.scastellanos.mastermind.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

/**
 * 
 * @author scastellanos
 *
 */
@Entity
@Table(name = "code")
public class Code {

	public Code() {}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	
	/**
	 * The list of PegEntitys that conform the code.
	 */
	@OneToMany(mappedBy = "code", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@OrderColumn(name = "pos")
	private Peg[] pegs;
	
	
	/**
	 * The length of the code.
	 */
	@Column(name = "codeSize", nullable = false, length = 10)
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
