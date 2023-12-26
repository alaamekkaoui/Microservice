package com.microservice_commande.model;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Commande {
    @Id
    @GeneratedValue
    private Integer id;
    private String description;
    private LocalDate date;
    private Integer quantite;
    private Double montant;
    @Override
    public String toString() {
        return "Commande{id=" + id + ", date=" + date + ", otherFields=...}";
    }

}