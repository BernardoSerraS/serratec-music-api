package org.serratec.backend.controller;

import java.util.List;

import org.serratec.backend.entity.Usuario;
import org.serratec.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Tag(name = "Usuarios")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Operation(summary = "Listar todos os usuários")
	@GetMapping
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}

	@Operation(summary = "Buscar usuário por id")
	@GetMapping("/{id}")
	public Usuario buscar(@PathVariable Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
	}

	@Operation(summary = "Criar usuário com perfil (JSON aninhado)")
	@PostMapping
	public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario) {
		Usuario salvo = usuarioRepository.save(usuario);
		return ResponseEntity.status(201).body(salvo);
	}

	@Operation(summary = "Atualizar usuário")
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @Valid @RequestBody Usuario payload) {
		return usuarioRepository.findById(id).map(u -> {
			u.setNome(payload.getNome());
			u.setEmail(payload.getEmail());
			u.setPerfil(payload.getPerfil());
			usuarioRepository.save(u);
			return ResponseEntity.ok(u);
		}).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
	}

	@Operation(summary = "Deletar usuário")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		if (!usuarioRepository.existsById(id))
			throw new EntityNotFoundException("Usuario não encontrado");
		usuarioRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
