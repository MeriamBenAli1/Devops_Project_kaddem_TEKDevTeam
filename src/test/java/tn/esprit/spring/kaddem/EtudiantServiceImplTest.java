package tn.esprit.spring.kaddem;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import tn.esprit.spring.kaddem.entities.*;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.EtudiantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;
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
        Set<Contrat> contrats = new HashSet<>();
        Contrat contrat = new Contrat();
        contrat.setArchive(false); // Un contrat actif
        contrat.setDateDebutContrat(new Date(120, 0, 1));
        contrat.setDateFinContrat(new Date(122, 0, 1));
        contrats.add(contrat);
        etudiant.setContrats(contrats);
    }

    @Test
    @Order(1)
    public void testEtudiantCreation() {
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant);

        assertNotNull(savedEtudiant, "L'étudiant ne doit pas être null.");
        assertEquals("Amine", savedEtudiant.getNomE(), "Le nom de l'étudiant doit être 'Amine'.");
        assertEquals(Option.GAMIX, savedEtudiant.getOp(), "L'option de l'étudiant doit être GAMIX.");
    }
}
