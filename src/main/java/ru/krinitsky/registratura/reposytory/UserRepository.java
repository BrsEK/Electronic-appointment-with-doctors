package ru.krinitsky.registratura.reposytory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krinitsky.registratura.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String name);
}
