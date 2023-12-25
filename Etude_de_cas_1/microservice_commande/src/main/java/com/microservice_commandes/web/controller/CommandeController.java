package com.microservice_commandes.web.controller;

import com.microservice_commandes.configurations.ApplicationPropertiesConfiguration;
import com.microservice_commandes.dao.CommandeDao;
import com.microservice_commandes.model.Commande;
import com.microservice_commandes.web.exceptions.CommandeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @GetMapping(value = "/Commandes")
    public List<Commande> listeDesCommandes() throws CommandeNotFoundException {
        System.out.println(" ********* CommandeController listeDesCommandes() ");
        List<Commande> Commandes = commandedao.findAll();
        if (Commandes.isEmpty())
            throw new CommandeNotFoundException("Aucun Commande n'est disponible à la vente");
        LocalDate tenDaysAgo = LocalDate.now().minusDays(appProperties.getCommandes_last());
        return Commandes;

    }
    @GetMapping("/commandes-last")
    @ResponseBody
    public int getCommandesLast() {
        int commandesLast = appProperties.getCommandes_last();
        System.out.println("Commandes Last: " + commandesLast);
        return commandesLast;
    }


    // Supprimer un Commande par son id
    @DeleteMapping(value = "/Commandes/{id}")
    public void supprimerCommandes(@PathVariable int id) throws CommandeNotFoundException {
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