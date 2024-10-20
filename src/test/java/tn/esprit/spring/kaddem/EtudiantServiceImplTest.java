package tn.esprit.spring.kaddem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Option;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.EtudiantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    private Etudiant etudiant;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Création d'un étudiant pour le test
        etudiant = new Etudiant();
        etudiant.setIdEtudiant(1);
        etudiant.setNomE("Amine");
        etudiant.setPrenomE("Akrimi");
        etudiant.setOp(Option.GAMIX);
    }

    @Test
    void testGetEtudiantById() {
        // Simuler le comportement du mock pour renvoyer l'étudiant
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant));

        // Appeler la méthode à tester
        Optional<Etudiant> retrievedEtudiant = Optional.ofNullable(etudiantService.retrieveEtudiant(1));

        // Vérifier que l'étudiant récupéré n'est pas vide et qu'il a les bonnes valeurs
        assertTrue(retrievedEtudiant.isPresent());
        assertEquals("Amine", retrievedEtudiant.get().getNomE());
        assertEquals("Akrimi", retrievedEtudiant.get().getPrenomE());
    }
}
