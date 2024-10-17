package tn.esprit.spring.kaddem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Niveau;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.services.EquipeServiceImpl;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class EquipeServiceImplTest {

    @Mock
    private EquipeRepository equipeRepository;

    @InjectMocks
    private EquipeServiceImpl equipeService;

    private Equipe equipe;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        equipe = createEquipeWithEtudiants();
    }

    private Equipe createEquipeWithEtudiants() {
        Equipe equipe = new Equipe();
        equipe.setNiveau(Niveau.JUNIOR);
        equipe.setIdEquipe(1);

        Set<Etudiant> etudiants = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            Etudiant etudiant = new Etudiant();
            etudiant.setIdEtudiant(i + 1);
            etudiant.setContrats(createContrats());
            etudiants.add(etudiant);
        }

        equipe.setEtudiants(etudiants);
        return equipe;
    }

    private Set<Contrat> createContrats() {
        Set<Contrat> contrats = new HashSet<>();
        Contrat contratValide = new Contrat();
        contratValide.setArchive(false);
        contratValide.setDateDebutContrat(new Date(120, 0, 1));
        contratValide.setDateFinContrat(new Date(122, 0, 1));
        contrats.add(contratValide);
        return contrats;
    }

    @Test
    @Order(1)
    public void testEquipeCreation() {
        when(equipeRepository.save(any(Equipe.class))).thenReturn(equipe);
        Equipe savedEquipe = equipeService.addEquipe(equipe);

        assertNotNull(savedEquipe, "L'équipe ne doit pas être null.");
        assertEquals(Niveau.JUNIOR, savedEquipe.getNiveau(), "L'équipe doit être initialement au niveau JUNIOR.");
        assertEquals(4, savedEquipe.getEtudiants().size(), "L'équipe doit avoir exactement 4 étudiants.");
    }

    @Test
    @Order(2)
    public void testEvoluerEquipes() {
        when(equipeRepository.save(any(Equipe.class))).thenReturn(equipe);
        when(equipeRepository.findAll()).thenReturn(Arrays.asList(equipe));

        equipeService.evoluerEquipes();

        assertEquals(Niveau.SENIOR, equipe.getNiveau(), "L'équipe doit passer au niveau SENIOR après évolution.");
        assertEquals(4, equipe.getEtudiants().size(), "L'équipe doit avoir exactement 4 étudiants après l'évolution.");
    }

    @Test
    @Order(3)
    public void testContratsActifs() {
        boolean hasActiveContract = equipe.getEtudiants().stream()
                .flatMap(etudiant -> etudiant.getContrats().stream())
                .anyMatch(contrat -> !contrat.getArchive());

        assertTrue(hasActiveContract, "Chaque étudiant doit avoir un contrat actif non archivé.");
    }
}
