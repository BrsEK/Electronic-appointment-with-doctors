package ru.krinitsky.registratura.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "subscribers")
@NoArgsConstructor
public class Subscriber extends Identify {

    public Subscriber(String email, Doctor doctor) {
        this.email = email;
        this.doctor = doctor;
    }

    @Getter
    @Setter
    @NotBlank(message = "Поле не должно быть пустым")
    @Email
    private String email;

    @Getter
    @Setter
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Doctor doctor;
}
