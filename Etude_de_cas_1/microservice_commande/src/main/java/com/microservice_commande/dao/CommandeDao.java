package com.microservice_commande.dao;
import com.microservice_commande.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommandeDao extends JpaRepository<Commande, Integer> {

    @Query("SELECT c FROM Commande c WHERE c.date >= :date")
    List<Commande> findByDateAfter(LocalDate date);
}
