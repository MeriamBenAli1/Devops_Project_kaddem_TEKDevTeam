package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.controllers.ContratRestController;
import tn.esprit.spring.kaddem.controllers.dtos.ContratDTO;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.services.IContratService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContratRestControllerTest {

    @Mock
    private IContratService contratService;

    @InjectMocks
    private ContratRestController contratRestController;

    @Test
    void testGetContrats() {
        // Arrange
        List<Contrat> expectedContrats = Arrays.asList(new Contrat(), new Contrat());
        when(contratService.retrieveAllContrats()).thenReturn(expectedContrats);

        // Act
        List<Contrat> actualContrats = contratRestController.getContrats();

        // Assert
        assertEquals(expectedContrats, actualContrats);
    }

    @Test
    void testRetrieveContrat() {
        // Arrange
        int contratId = 1;
        Contrat expectedContrat = new Contrat();
        when(contratService.retrieveContrat(contratId)).thenReturn(expectedContrat);

        // Act
        Contrat actualContrat = contratRestController.retrieveContrat(contratId);

        // Assert
        assertEquals(expectedContrat, actualContrat);
    }

    @Test
    void testAddContrat() {
        // Arrange
        ContratDTO contratDTO = new ContratDTO();
        contratDTO.setDateDebutContrat(new Date());
        contratDTO.setDateFinContrat(new Date());

        Contrat addedContrat = new Contrat();
        addedContrat.setDateDebutContrat(contratDTO.getDateDebutContrat());
        addedContrat.setDateFinContrat(contratDTO.getDateFinContrat());

        when(contratService.addContrat(contratDTO)).thenReturn(addedContrat);

        // Act
        Contrat actualContrat = contratRestController.addContrat(contratDTO);

        // Assert
        assertEquals(addedContrat, actualContrat);
    }

    @Test
    void testUpdateContrat() {
        // Arrange
        ContratDTO contratDTO = new ContratDTO();
        contratDTO.setDateDebutContrat(new Date());
        contratDTO.setDateFinContrat(new Date());

        Contrat updatedContrat = new Contrat();
        updatedContrat.setDateDebutContrat(contratDTO.getDateDebutContrat());
        updatedContrat.setDateFinContrat(contratDTO.getDateFinContrat());

        when(contratService.updateContrat(contratDTO)).thenReturn(updatedContrat);

        // Act
        Contrat actualContrat = contratRestController.updateContrat(contratDTO);

        // Assert
        assertEquals(updatedContrat, actualContrat);
    }

    @Test
    void testGetnbContratsValides() {
        // Arrange
        Date startDate = new Date();
        Date endDate = new Date();
        Integer expectedCount = 5;

        when(contratService.nbContratsValides(startDate, endDate)).thenReturn(expectedCount);

        // Act
        Integer actualCount = contratRestController.getnbContratsValides(startDate, endDate);

        // Assert
        assertEquals(expectedCount, actualCount);
    }

    @Test
    void testMajStatusContrat() {
        // Arrange

        // Act
        assertDoesNotThrow(() -> contratRestController.majStatusContrat());

        // Assert
        verify(contratService).retrieveAndUpdateStatusContrat();
    }

}