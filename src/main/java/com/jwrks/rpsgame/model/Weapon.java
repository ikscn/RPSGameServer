package com.jwrks.rpsgame.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Weapon {

    @Id
    private Long id;

    private String name;
    private String imgSrc;

}
