package com.scastellanos.mastermind.util;

import java.util.ArrayList;
import java.util.List;

import com.scastellanos.mastermind.dto.CodeDTO;
import com.scastellanos.mastermind.dto.GameDTO;
import com.scastellanos.mastermind.dto.GuessHistoryDTO;
import com.scastellanos.mastermind.dto.PegDTO;
import com.scastellanos.mastermind.entity.Game;
import com.scastellanos.mastermind.entity.GuessHistory;
import com.scastellanos.mastermind.entity.Peg;

public class ServiceUtils {
	
	/**
	 * Convert a PegDTO to a peg entity object.
	 * @param pegsDTO
	 * @return
	 */
	public static Peg[] convertPegsDTO2PegsEntity(PegDTO[] pegsDTO) {
		Peg[] pegsEntities = new Peg[pegsDTO.length];
		for (int i = 0; i < pegsDTO.length; i++) {
			Peg entity = new Peg();
			entity.setColor(pegsDTO[i].getColor());
			pegsEntities[i] = entity;
		}
		
		return pegsEntities;
	}
	
	/**
	 * Convert a Game pojo to a DTO object in order to avoid using entity business outside service layer.
	 * @param game
	 * @return
	 */
	public static GameDTO convertGameEntity2GameDTO(Game game) {
		GameDTO gameDTO = new GameDTO();
		gameDTO.setId(game.getId());
		
		CodeDTO code = new CodeDTO();
		if(game.getCode()!=null) {
			code.setCodeSize(game.getCode().getCodeSize());
			code.setPegs(convertPegsEntity2PegsDTO(game.getCode().getPegs()));
			gameDTO.setCode(code);
		}
		
		return gameDTO;
	}

	/**
	 * Convert a Peg pojo to a DTO object in order to avoid using entity business outside service layer.
	 * @param pegsEntity
	 * @return
	 */
	public static PegDTO[] convertPegsEntity2PegsDTO(Peg[] pegsEntity) {
		PegDTO[] pegsDTO = new PegDTO[pegsEntity.length];
		for (int i = 0; i < pegsEntity.length; i++) {
			PegDTO dto = new PegDTO();
			dto.setColor(pegsEntity[i].getColor());
			pegsDTO[i] = dto;
		}
		
		return pegsDTO;
	}
	
	/**
	 * Convert a Guess  pojo to a GuessHistoryDTO object in order to avoid using entity business outside service layer.
	 * This convert also converts the Pegs entities included in the GameHistory and its relations. 
	 * @param pegsEntity
	 * @return
	 */
	public static List<GuessHistoryDTO> convertGuessEntity2DTO(List<GuessHistory> history){
		List<GuessHistoryDTO> dtoList = new ArrayList();
		for (GuessHistory guessHistory : history) {
			GuessHistoryDTO dto = new GuessHistoryDTO();
			dto.setGame(guessHistory.getGame().getId());
			dto.setNumberBlack(guessHistory.getNumberBlack());
			dto.setNumberWhite(guessHistory.getNumberWhite());
			PegDTO[] pegsDTO = convertPegsEntity2PegsDTO(guessHistory.getPegs());
			dto.setPegs(pegsDTO);
			dtoList.add(dto);
		}
		return 	dtoList;
	}
		
	
}
