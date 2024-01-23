package com.jwrks.rpsgame.repo;

import com.jwrks.rpsgame.model.Weapon;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeaponRepository extends CrudRepository<Weapon, Long> {
    List<Weapon> findAll(Sort id);
}
