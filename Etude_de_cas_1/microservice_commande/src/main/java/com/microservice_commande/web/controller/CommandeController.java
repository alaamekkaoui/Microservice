package com.microservice_commande.web.controller;

import com.microservice_commande.web.exceptions.CommandeNotFoundException;
import com.microservice_commande.configurations.ApplicationPropertiesConfiguration;
import com.microservice_commande.dao.CommandeDao;
import com.microservice_commande.model.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class CommandeController implements HealthIndicator {
    @Autowired
    CommandeDao commandedao;
    @Autowired
    ApplicationPropertiesConfiguration appProperties;
    // Affiche la liste de tous les Commandes disponibles
    @GetMapping(value = "/Commandes")
    //@ResponseBody
    public List<Commande> listeDesCommandes() throws CommandeNotFoundException {
        System.out.println(" ********* CommandeController listeDesCommandes() ");
        List<Commande> Commandes = commandedao.findAll();
        if (Commandes.isEmpty())
            throw new CommandeNotFoundException("Aucun Commande n'est disponible à la vente");

        LocalDate daysAgo = LocalDate.now().minusDays(appProperties.getCommandes_last());
        List<Commande> commandesLastDays = commandedao.findByDateAfter(daysAgo);

        System.out.println("Local Date :" + daysAgo);
        System.out.println("Last Commands:" + commandesLastDays);

        return commandesLastDays;

    }

    // Supprimer un Commande par son id
    @DeleteMapping(value = "/Commandes/{id}")
    public void supprimerCommande(@PathVariable int id) throws CommandeNotFoundException {
        System.out.println(" ********* CommandeController supprimerCommande(@PathVariable int id) ");
        Optional<Commande> commande = commandedao.findById(id);
        if (!commande.isPresent())
            throw new CommandeNotFoundException("Le Commande correspondant à l'id " + id + " n'existe pas");

        commandedao.deleteById(id);
    }
    // Récuperer un Commande par son id
    @GetMapping(value = "/Commandes/{id}")
    public Optional<Commande> recupererUnCommande(@PathVariable int id) throws CommandeNotFoundException {
        System.out.println(" ********* CommandeController recupererUnCommande(@PathVariable int id) ");
        Optional<Commande> Commande = commandedao.findById(id);
        if (!Commande.isPresent())
            throw new CommandeNotFoundException("Le Commande correspondant à l'id " + id + " n'existe pas");
        return Commande;
    }
    @Override
    public Health health() {
        System.out.println("****** Actuator : CommandeController health() ");
        List<Commande> Commandes = commandedao.findAll();
        if (Commandes.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }
}