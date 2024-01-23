package com.jwrks.rpsgame.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Weapon {

    @Id
    private Long id;

    private String name;
    private String imgSrc;

}
