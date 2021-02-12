package ru.krinitsky.registratura.domain;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "clients")
@NoArgsConstructor
public class Client extends Identify {


    @Size(message = "Поле имя должно содержать минимум 2 символа", min = 2)
    @Getter
    @Setter
    @NotNull
    @NotBlank
    private String name;

    @Getter
    @Setter
    @NotNull
    @Size(message = "Поле фамилия должно содержать минимум 2 символа",min = 2)
    @NotBlank
    private String surname;

    @Getter
    @Setter
    @NotNull
    @Size(message = "Поле фамилия должно содержать минимум 2 символа", min = 2)
    @NotBlank
    private String patronymic;


    @Getter
    @Setter
    @NotNull
    @Pattern(regexp = "\\+7[0-9]{10}", message = "Телефонный номер должен начинаться с +7, затем - 10 цифр")
    private String phoneNumber;

    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Email
    private String email;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<Ticket> tickets;

}
