package ru.krinitsky.registratura.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "ticket")
@NoArgsConstructor
public class Ticket extends Identify {

    @Getter
    @Setter
    @NotNull(message = "Поле должно содержать дату посещения")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate date;

    @Getter
    @Setter
    @NotNull(message = "Поле должно содержать время посещения")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalTime time;

    @Getter
    @Setter
    private boolean confirmed;

    @Getter
    @Setter
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Doctor doctor;

    @Getter
    @Setter
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private Client client;

}
