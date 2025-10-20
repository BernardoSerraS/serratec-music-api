package org.serratec.backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.serratec.backend.entity.Artista;
import org.serratec.backend.entity.Musica;
import org.serratec.backend.repository.ArtistaRepository;
import org.serratec.backend.repository.MusicaRepository;
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

@Tag(name = "Musicas")
@RestController
@RequestMapping("/musicas")
public class MusicaController {

    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    @Operation(summary = "Listar músicas")
    @GetMapping
    public List<Musica> listar() { return musicaRepository.findAll(); }

    @Operation(summary = "Buscar música por id")
    @GetMapping("/{id}")
    public Musica buscar(@PathVariable Long id) {
        return musicaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Musica não encontrada"));
    }

    @Operation(summary = "Criar música (aceita artistas por id)")
    @PostMapping
    public ResponseEntity<Musica> criar(@Valid @RequestBody Musica musica) {
        List<Artista> artistas = new ArrayList<>();
        if (musica.getArtistas() != null) {
            for (Artista a : musica.getArtistas()) {
                if (a.getId() != null) {
                    Artista found = artistaRepository.findById(a.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Artista id " + a.getId() + " não encontrado"));
                    artistas.add(found);
                } else {
                    artistas.add(a);
                }
            }
        }
        musica.setArtistas(artistas);
        Musica salvo = musicaRepository.save(musica);
        return ResponseEntity.status(201).body(salvo);
    }

    @Operation(summary = "Atualizar música")
    @PutMapping("/{id}")
    public ResponseEntity<Musica> atualizar(@PathVariable Long id, @Valid @RequestBody Musica payload) {
        return musicaRepository.findById(id).map(m -> {
            m.setTitulo(payload.getTitulo());
            m.setMinutos(payload.getMinutos());
            m.setGenero(payload.getGenero());
            List<Artista> artistas = new ArrayList<>();
            if (payload.getArtistas() != null) {
                for (Artista a : payload.getArtistas()) {
                    if (a.getId() != null) {
                        Artista found = artistaRepository.findById(a.getId())
                            .orElseThrow(() -> new EntityNotFoundException("Artista id " + a.getId() + " não encontrado"));
                        artistas.add(found);
                    } else {
                        artistas.add(a);
                    }
                }
            }
            m.setArtistas(artistas);
            musicaRepository.save(m);
            return ResponseEntity.ok(m);
        }).orElseThrow(() -> new EntityNotFoundException("Musica não encontrada"));
    }

    @Operation(summary = "Deletar música")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!musicaRepository.existsById(id)) throw new EntityNotFoundException("Musica não encontrada");
        musicaRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
