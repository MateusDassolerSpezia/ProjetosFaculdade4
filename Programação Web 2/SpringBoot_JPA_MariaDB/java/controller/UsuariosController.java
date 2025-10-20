package br.com.exemplo.spring_jpa_mariadb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.exemplo.spring_jpa_mariadb.repository.UsuarioRepositorio;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuariosController {

	private UsuarioRepositorio usuarioRepositorio;
	
	public UsuariosController (UsuarioRepositorio usuarioRepositorio) {
		this.usuarioRepositorio = usuarioRepositorio;
	}
	
	@GetMapping()
	public ResponseEntity getUsuarios() {
		return ResponseEntity.status(HttpStatus.OK).body(usuarioRepositorio.findAll());
	}
}
