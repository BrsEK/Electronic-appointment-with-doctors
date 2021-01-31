package ru.krinitsky.registratura.domain;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "clients")
@NoArgsConstructor
public class Client extends Identify {


    @Size(min = 2)
    @Getter
    @Setter
    @NotNull
    @NotBlank
    private String name;

    @Getter
    @Setter
    @NotNull
    @Size(min = 2)
    @NotBlank
    private String surname;

    @Getter
    @Setter
    @NotNull
    @Size(min = 2)
    @NotBlank
    private String patronymic;


    @Getter
    @Setter
    @NotNull
    private int phoneNumber;

    @Getter
    @Setter
    @NotNull
    @NotBlank
    private String email;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<Ticket> tickets;

}
