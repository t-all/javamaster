package ru.javamaster.javamaster.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Inactivation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private InactivationReason reason;

    public Inactivation(InactivationReason reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Inactivation{" +
                "id=" + id +
                ", reason=" + reason +
                '}';
    }
}
