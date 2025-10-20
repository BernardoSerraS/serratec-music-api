package org.serratec.backend.repository;

import org.serratec.backend.entity.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
}
