package tn.esprit.spring.kaddem.services;

import tn.esprit.spring.kaddem.controllers.dtos.ContratDTO;
import tn.esprit.spring.kaddem.entities.Contrat;
import java.util.Date;
import java.util.List;

public interface IContratService {
    public List<Contrat> retrieveAllContrats();

    public Contrat updateContrat (ContratDTO ce);

    public  Contrat addContrat (ContratDTO ce);

    public Contrat retrieveContrat (Integer  idContrat);

    public  void removeContrat(Integer idContrat);

    public Contrat affectContratToEtudiant (Integer idContrat, String nomE, String prenomE);

        public 	Integer nbContratsValides(Date startDate, Date endDate);


    public float getChiffreAffaireEntreDeuxDates(Date startDate, Date endDate);

    public void retrieveAndUpdateStatusContrat();
}

