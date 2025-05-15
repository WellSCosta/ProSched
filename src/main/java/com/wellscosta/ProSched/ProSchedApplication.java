package com.wellscosta.ProSched;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Plataforma onde clientes encontram e agendam horários com profissionais autônomos.
 * A ideia é permitir agendamentos em tempo real, com confirmações automáticas, gerenciamento da agenda e notificações assícronas.
 */

@SpringBootApplication
public class ProSchedApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProSchedApplication.class, args);
	}

}
