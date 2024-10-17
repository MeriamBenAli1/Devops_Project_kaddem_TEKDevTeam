package tn.esprit.spring.kaddem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
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

import org.springframework.test.context.ActiveProfiles;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Niveau;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.services.EquipeServiceImpl;
@ActiveProfiles("test")

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

        equipe = new Equipe();
        equipe.setNiveau(Niveau.JUNIOR);
        equipe.setIdEquipe(1);

        Set<Etudiant> etudiants = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            Etudiant etudiant = new Etudiant();
            etudiant.setIdEtudiant(i + 1);

            Set<Contrat> contrats = new HashSet<>();


            Contrat contratValide = new Contrat();
            contratValide.setArchive(false);


            Date dateSysteme = new Date();

            contratValide.setDateDebutContrat(new Date(120, 0, 1));

            contratValide.setDateFinContrat(new Date(122, 0, 1));



            Date dateFin = new Date(dateSysteme.getTime() - (1000L * 60 * 60 * 24 * 365)); // 1 an dans le passé
            contrats.add(contratValide);
            etudiant.setContrats(contrats);
            etudiants.add(etudiant);
        }

        equipe.setEtudiants(etudiants);
    }


    @Test
    @Order(1)
    public void testEquipeCreation() {
        when(equipeRepository.save(any(Equipe.class))).thenReturn(equipe);

        Equipe savedEquipe = equipeService.addEquipe(equipe);

        assertNotNull(savedEquipe, "L'équipe ne doit pas être null.");
        assertEquals(Niveau.JUNIOR, savedEquipe.getNiveau(), "L'équipe doit être initialement au niveau JUNIOR.");
        assertEquals(4, savedEquipe.getEtudiants().size(), "L'équipe doit avoir exactement 3 étudiants.");
    }

    @Test
    @Order(2)
    public void testEvoluerEquipes() {
        when(equipeRepository.save(any(Equipe.class))).thenReturn(equipe);
        when(equipeRepository.findAll()).thenReturn(Arrays.asList(equipe));

        equipeService.evoluerEquipes();


        Set<Etudiant> etudiants = equipe.getEtudiants();

        assertNotNull(etudiants, "L'équipe doit avoir des étudiants.");
        assertEquals(4, etudiants.size(), "L'équipe doit avoir exactement 3 étudiants après l'évolution.");


        assertEquals(Niveau.SENIOR, equipe.getNiveau(), "L'équipe doit passer au niveau SENIOR après évolution.");


    }

    @Test
    @Order(3)
    public void testContratsActifs() {
        boolean hasActiveContract = false;
        for (Etudiant etudiant : equipe.getEtudiants()) {
            for (Contrat contrat : etudiant.getContrats()) {
                if (!contrat.getArchive()) {
                    hasActiveContract = true;
                    break;
                }
            }
        }
        assertTrue(hasActiveContract, "Chaque étudiant doit avoir un contrat actif non archivé.");
    }
}
