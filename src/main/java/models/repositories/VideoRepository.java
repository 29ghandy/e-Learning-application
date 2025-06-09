package models.repositories;

import models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<User, Long> {
}
