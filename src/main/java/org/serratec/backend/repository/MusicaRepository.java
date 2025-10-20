package org.serratec.backend.repository;

import org.serratec.backend.entity.Musica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicaRepository extends JpaRepository<Musica, Long> {
}
