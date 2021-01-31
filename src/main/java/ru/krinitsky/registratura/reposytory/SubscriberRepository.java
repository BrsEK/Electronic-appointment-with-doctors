package ru.krinitsky.registratura.reposytory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krinitsky.registratura.domain.Subscriber;

public interface SubscriberRepository extends JpaRepository <Subscriber, Long> {
}
