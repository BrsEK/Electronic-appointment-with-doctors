package ru.krinitsky.registratura.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "receptionist")
@NoArgsConstructor
public class Receptionist extends Identify{

    @Getter
    @Setter
    @NotBlank(message = "поле Имя не должно быть пустым")
    private String name;

    @Getter
    @Setter
    @NotBlank(message = "поле Фамилия не должно быть пустым")
    private String surname;

    @Getter
    @Setter
    @NotBlank(message = "поле отчество не должно быть пустым")
    private String patronymic;

    @Getter
    @Setter
    @NotBlank(message = "поле email не должно быть пустым")
    @Email
    private String email;
}
