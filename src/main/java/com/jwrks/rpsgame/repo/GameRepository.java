package com.jwrks.rpsgame.repo;

import com.jwrks.rpsgame.model.Game;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
     List<Game> findAll(Sort gameNumber);
}
