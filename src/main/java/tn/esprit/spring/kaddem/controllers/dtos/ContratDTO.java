package tn.esprit.spring.kaddem.controllers.dtos;

import tn.esprit.spring.kaddem.entities.Specialite;

import java.util.Date;

public class ContratDTO {
    private Integer id;
    private Date dateDebutContrat;
    private Date dateFinContrat;
    private Specialite specialite;

    // Default constructor is intentionally left empty
    // Add a comment here to explain why this method is empty.

    public ContratDTO() {
        // An empty constructor is provided for frameworks or libraries that require it.
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateDebutContrat() {
        return dateDebutContrat;
    }

    public void setDateDebutContrat(Date dateDebutContrat) {
        this.dateDebutContrat = dateDebutContrat;
    }

    public Date getDateFinContrat() {
        return dateFinContrat;
    }

    public void setDateFinContrat(Date dateFinContrat) {
        this.dateFinContrat = dateFinContrat;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }
}
