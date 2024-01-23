package com.jwrks.rpsgame.model;

import lombok.Data;

@Data
public class Round {

    private final Weapon playerWeapon;
    private final Weapon computerWeapon;
    private int result;

}
