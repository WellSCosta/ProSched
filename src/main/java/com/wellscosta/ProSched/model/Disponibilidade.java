package com.wellscosta.ProSched.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Define os hórarios livres de um profissional
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Disponibilidade {

    private Long id;
    private Usuario profissional;
    private LocalDateTime inicio;
    private LocalDateTime fim;
}
