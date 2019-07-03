package com.scastellanos.mastermind.repository;

import org.springframework.data.repository.CrudRepository;

import com.scastellanos.mastermind.entity.Game;

public interface GameRepository extends CrudRepository<Game, Long>   {

}
