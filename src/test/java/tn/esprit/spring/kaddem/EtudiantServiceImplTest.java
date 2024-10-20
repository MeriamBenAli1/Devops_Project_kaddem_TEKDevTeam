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

    @Mock
    private EquipeRepository equipeRepository;

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
    @Test
    @Order(2)
    public void testContratsEtudiant() {
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant));

        Etudiant fetchedEtudiant = etudiantService.retrieveEtudiant(1);
        Set<Contrat> contrats = fetchedEtudiant.getContrats();

        assertNotNull(contrats, "L'étudiant doit avoir des contrats.");
        assertEquals(1, contrats.size(), "L'étudiant doit avoir exactement 1 contrat.");

        boolean hasActiveContract = contrats.stream().anyMatch(contrat -> !contrat.getArchive());
        assertTrue(hasActiveContract, "L'étudiant doit avoir au moins un contrat actif non archivé.");
    }
    @Test
    @Order(3)
    public void testOptionEtudiant() {
        etudiant.setOp(Option.SE);
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        Etudiant updatedEtudiant = etudiantService.updateEtudiant(etudiant);

        assertEquals(Option.SE, updatedEtudiant.getOp(), "L'option de l'étudiant doit être 'GENIE_CIVIL'.");
    }

    @Test
    @Order(4)
    public void testGetEtudiantsWithExpiringContracts() {
        Etudiant etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(1);
        Contrat contratExpirant = new Contrat();
        contratExpirant.setArchive(false);
        contratExpirant.setDateFinContrat(new Date(System.currentTimeMillis() + (20L * 24 * 60 * 60 * 1000)));
        etudiant1.setContrats(new HashSet<>(Collections.singletonList(contratExpirant)));

        Etudiant etudiant2 = new Etudiant();
        etudiant2.setIdEtudiant(2);
        Contrat contratNonExpirant = new Contrat();
        contratNonExpirant.setArchive(false);
        contratNonExpirant.setDateFinContrat(new Date(System.currentTimeMillis() + (40L * 24 * 60 * 60 * 1000)));
        etudiant2.setContrats(new HashSet<>(Collections.singletonList(contratNonExpirant)));

        when(etudiantRepository.findAll()).thenReturn(Arrays.asList(etudiant1, etudiant2));

        List<Etudiant> result = etudiantService.getEtudiantsWithExpiringContracts();

        assertEquals(1, result.size(), "Il doit y avoir un étudiant avec un contrat expirant.");
        assertEquals(1, result.get(0).getIdEtudiant(), "L'étudiant avec le contrat expirant doit avoir l'ID 1.");
    }
    @Test
    @Order(5)
    public void testCalculAncienneteMoyenneContrats() {
        Equipe equipe = new Equipe();
        equipe.setIdEquipe(1);

        Etudiant etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(1);
        Set<Contrat> contrats = new HashSet<>();

        Contrat contrat1 = new Contrat();
        contrat1.setDateDebutContrat(new Date(System.currentTimeMillis() - (10L * 24 * 60 * 60 * 1000))); // 10 jours
        contrat1.setDateFinContrat(new Date(System.currentTimeMillis() + (10L * 24 * 60 * 60 * 1000))); // 10 jours
        contrats.add(contrat1);

        Contrat contrat2 = new Contrat();
        contrat2.setDateDebutContrat(new Date(System.currentTimeMillis() - (20L * 24 * 60 * 60 * 1000))); // 20 jours
        contrat2.setDateFinContrat(new Date(System.currentTimeMillis() + (20L * 24 * 60 * 60 * 1000))); // 20 jours
        contrats.add(contrat2);

        etudiant1.setContrats(contrats);

        Set<Etudiant> etudiants = new HashSet<>();
        etudiants.add(etudiant1);
        equipe.setEtudiants(etudiants);

        when(equipeRepository.findById(1)).thenReturn(Optional.of(equipe));

        when(etudiantRepository.findAll()).thenReturn(Arrays.asList(etudiant1));

        double averageSeniority = etudiantService.calculateAverageContractDurationForEquipe(1);

        assertEquals(15.0, averageSeniority, "L'ancienneté moyenne doit être de 15 jours.");
    }

}
