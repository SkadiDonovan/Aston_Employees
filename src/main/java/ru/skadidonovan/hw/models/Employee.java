package ru.skadidonovan.hw.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;

import lombok.Data;

import java.time.LocalDate;


@Data
public class Employee {
    public long id;

    @NotEmpty(message = "Имя не может быть пустым")
    public String firstName;

    @NotEmpty(message = "Фамилия не может быть пустой")
    public String lastName;

    @Past(message = "Недопустимая дата рождения" )
    @JsonFormat(pattern = "yyyy/MM/dd")
    public LocalDate dateOfBirth;

    public String position;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", position='" + position + '\'' +
                '}';
    }
}
