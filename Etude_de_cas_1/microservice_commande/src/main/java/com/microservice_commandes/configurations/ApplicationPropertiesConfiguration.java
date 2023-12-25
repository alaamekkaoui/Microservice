package com.microservice_commandes.configurations;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("mes-configs-ms")
@RefreshScope
public class ApplicationPropertiesConfiguration {
    // correspond à la propriété « mes-configs.limitDeProduits » dans le fichier de configuration du MS
    private int commandes_last;
    public int getCommandes_last() {
        return commandes_last;
    }

    public void setCommandes_last(int commandes_last) {
        this.commandes_last = commandes_last;
    }

}