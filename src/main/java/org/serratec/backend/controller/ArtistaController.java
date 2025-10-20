package org.serratec.backend.controller;

import java.util.List;

import org.serratec.backend.entity.Artista;
import org.serratec.backend.repository.ArtistaRepository;
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

@Tag(name = "Artistas")
@RestController
@RequestMapping("/artistas")
public class ArtistaController {

	@Autowired
	private ArtistaRepository artistaRepository;

	@Operation(summary = "Listar artistas")
	@GetMapping
	public List<Artista> listar() {
		return artistaRepository.findAll();
	}

	@Operation(summary = "Buscar artista por id")
	@GetMapping("/{id}")
	public Artista buscar(@PathVariable Long id) {
		return artistaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Artista não encontrado com ID: \" + id"));
	}

	@Operation(summary = "Criar artista")
	@PostMapping
	public ResponseEntity<Artista> criar(@Valid @RequestBody Artista artista) {
		return ResponseEntity.status(201).body(artistaRepository.save(artista));
	}

	@Operation(summary = "Atualizar artista")
	@PutMapping("/{id}")
	public ResponseEntity<Artista> atualizar(@PathVariable Long id, @Valid @RequestBody Artista payload) {
		return artistaRepository.findById(id).map(a -> {
			a.setNome(payload.getNome());
			a.setNacionalidade(payload.getNacionalidade());
			artistaRepository.save(a);
			return ResponseEntity.ok(a);
		}).orElseThrow(() -> new EntityNotFoundException("Artista não encontrado com ID: \" + id"));
	}

	@Operation(summary = "Deletar artista")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		if (!artistaRepository.existsById(id))
			throw new EntityNotFoundException("Artista não encontrado com ID: \" + id");
		artistaRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
