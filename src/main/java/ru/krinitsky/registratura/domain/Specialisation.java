package ru.krinitsky.registratura.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.Set;

@Entity
@Table(name = "specialisations")
@NoArgsConstructor
public class Specialisation extends Identify {

    @Getter
    @Setter
    @NotBlank(message = "Поле специализации не должно быть пустым")
    private String title;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "specialisation", cascade = {CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Doctor> doctors;
}
