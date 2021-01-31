package ru.krinitsky.registratura.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctors")
@NoArgsConstructor
public class Doctor extends Identify {


    @Getter
    @Setter
    @NotBlank(message = "Поле не должно быть пустым")
    @Size(min = 2, max = 20, message = "Не менее 2 и не более 20 символов")
    private String name;

    @Getter
    @Setter
    @NotBlank(message = "Поле не должно быть пустым")
    @Size(min = 2, max = 20, message = "Не менее 2 и не более 20 символов")
    private String surname;

    @Getter
    @Setter
    @NotBlank(message = "Поле не должно быть пустым")
    @Size(min = 2, max = 20, message = "Не менее 2 и не более 20 символов")
    @Column(name = "PATRONYMIC")
    private String patronymic;

    @Getter
    @Setter
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Specialisation specialisation;

    @Getter
    @Setter
    @NotBlank(message = "Поле не должно быть пустым")
    @Email
    private String email;


    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "doctor", cascade = {CascadeType.REMOVE, CascadeType.MERGE} , orphanRemoval = true)
    private Set<Ticket> tickets = new HashSet<>();

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "doctor", cascade = CascadeType.REMOVE)
    private Set<Subscriber> subscribers;

}
