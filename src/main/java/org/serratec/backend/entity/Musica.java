package org.serratec.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.*;

@Entity
@Schema(description = "Música")
public class Musica {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Schema(example = "Minha Canção")
	private String titulo;

	@PositiveOrZero
	@Schema(example = "3")
	private Integer minutos;

	@Enumerated(EnumType.STRING)
	private GeneroMusical genero;

	@ManyToMany
	@JoinTable(name = "musica_artista", joinColumns = @JoinColumn(name = "musica_id"), inverseJoinColumns = @JoinColumn(name = "artista_id"))
	private List<Artista> artistas = new ArrayList<>();

	@ManyToMany(mappedBy = "musicas")
	private List<Playlist> playlists = new ArrayList<>();

	// getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getMinutos() {
		return minutos;
	}

	public void setMinutos(Integer minutos) {
		this.minutos = minutos;
	}

	public GeneroMusical getGenero() {
		return genero;
	}

	public void setGenero(GeneroMusical genero) {
		this.genero = genero;
	}

	public List<Artista> getArtistas() {
		return artistas;
	}

	public void setArtistas(List<Artista> artistas) {
		this.artistas = artistas;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}
}
