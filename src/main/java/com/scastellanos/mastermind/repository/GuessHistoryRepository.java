package com.scastellanos.mastermind.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.scastellanos.mastermind.entity.GuessHistory;

public interface GuessHistoryRepository extends CrudRepository<GuessHistory, Long>   {
	
	List<GuessHistory> findByGameId(Long gameId);
	
}
