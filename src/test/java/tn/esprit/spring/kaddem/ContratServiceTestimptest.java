package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Specialite;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest

public class ContratServiceTestimptest {

    @Test
    void testContratConstructorAndGetters() {
        Date dateDebut = new Date();
        Date dateFin = new Date();
        Contrat contrat = new Contrat(dateDebut, dateFin, Specialite.IA, false, 300);

        assertNotNull(contrat);
        assertEquals(dateDebut, contrat.getDateDebutContrat());
        assertEquals(dateFin, contrat.getDateFinContrat());
        assertEquals(Specialite.IA, contrat.getSpecialite());
        assertFalse(contrat.getArchive());
        assertEquals(300, contrat.getMontantContrat());
    }

    @Test
    void testSetters() {
        Contrat contrat = new Contrat();
        contrat.setMontantContrat(400);
        assertEquals(400, contrat.getMontantContrat());

        contrat.setArchive(true);
        assertTrue(contrat.getArchive());
    }
}
