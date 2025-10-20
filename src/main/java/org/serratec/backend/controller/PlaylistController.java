package org.serratec.backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.serratec.backend.entity.Musica;
import org.serratec.backend.entity.Playlist;
import org.serratec.backend.entity.Usuario;
import org.serratec.backend.repository.MusicaRepository;
import org.serratec.backend.repository.PlaylistRepository;
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

@Tag(name = "Playlists")
@RestController
@RequestMapping("/playlists")
public class PlaylistController {

	@Autowired
	private PlaylistRepository playlistRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private MusicaRepository musicaRepository;

	@Operation(summary = "Listar playlists")
	@GetMapping
	public List<Playlist> listar() {
		return playlistRepository.findAll();
	}

	@Operation(summary = "Buscar playlist por id")
	@GetMapping("/{id}")
	public Playlist buscar(@PathVariable Long id) {
		return playlistRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Playlist não encontrada"));
	}

	@Operation(summary = "Criar playlist associada a um usuário (informe usuario.id no body)")
	@PostMapping
	public ResponseEntity<Playlist> criar(@Valid @RequestBody Playlist payload) {
		if (payload.getUsuario() == null || payload.getUsuario().getId() == null) {
			throw new IllegalArgumentException("Informe usuario.id no body");
		}
		Usuario user = usuarioRepository.findById(payload.getUsuario().getId())
				.orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
		payload.setUsuario(user);

		List<Musica> musicas = new ArrayList<>();
		if (payload.getMusicas() != null) {
			for (Musica m : payload.getMusicas()) {
				if (m.getId() != null) {
					Musica found = musicaRepository.findById(m.getId()).orElseThrow(
							() -> new EntityNotFoundException("Musica id " + m.getId() + " não encontrada"));
					musicas.add(found);
				} else {
					musicas.add(m);
				}
			}
		}
		payload.setMusicas(musicas);
		Playlist salvo = playlistRepository.save(payload);
		return ResponseEntity.status(201).body(salvo);
	}

	@Operation(summary = "Atualizar playlist - permite trocar lista de músicas informando ids")
	@PutMapping("/{id}")
	public ResponseEntity<Playlist> atualizar(@PathVariable Long id, @RequestBody Playlist payload) {
		return playlistRepository.findById(id).map(p -> {
			if (payload.getNome() != null)
				p.setNome(payload.getNome());
			if (payload.getDescricao() != null)
				p.setDescricao(payload.getDescricao());
			if (payload.getMusicas() != null) {
				List<Musica> musicas = new ArrayList<>();
				for (Musica m : payload.getMusicas()) {
					if (m.getId() == null)
						throw new IllegalArgumentException("Informe ids das músicas");
					Musica found = musicaRepository.findById(m.getId()).orElseThrow(
							() -> new EntityNotFoundException("Musica id " + m.getId() + " não encontrada"));
					musicas.add(found);
				}
				p.setMusicas(musicas);
			}
			playlistRepository.save(p);
			return ResponseEntity.ok(p);
		}).orElseThrow(() -> new EntityNotFoundException("Playlist não encontrada"));
	}

	@Operation(summary = "Deletar playlist")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		if (!playlistRepository.existsById(id))
			throw new EntityNotFoundException("Playlist não encontrada");
		playlistRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
