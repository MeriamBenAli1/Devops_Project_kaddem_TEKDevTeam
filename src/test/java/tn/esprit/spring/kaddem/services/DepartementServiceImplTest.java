package tn.esprit.spring.kaddem.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DepartementServiceImplTest {

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private DepartementServiceImpl departementService;

    private Departement departement;

    @BeforeEach
    void setUp() {
        departement = new Departement(1, "Informatique");
    }

    @Test
    void testRetrieveAllDepartements() {
        // Arrange
        List<Departement> expectedDepartements = new ArrayList<>();
        expectedDepartements.add(departement);
        when(departementRepository.findAll()).thenReturn(expectedDepartements);

        // Act
        List<Departement> actualDepartements = departementService.retrieveAllDepartements();

        // Assert
        assertEquals(expectedDepartements, actualDepartements);
    }

    @Test
    void testAddDepartement() {
        // Arrange
        when(departementRepository.save(departement)).thenReturn(departement);

        // Act
        Departement actualDepartement = departementService.addDepartement(departement);

        // Assert
        assertEquals(departement, actualDepartement);
    }

    @Test
    void testUpdateDepartement() {
        // Arrange
        when(departementRepository.save(departement)).thenReturn(departement);

        // Act
        Departement actualDepartement = departementService.updateDepartement(departement);

        // Assert
        assertEquals(departement, actualDepartement);
    }

    @Test
    void testRetrieveDepartement() {
        // Arrange
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        // Act
        Departement actualDepartement = departementService.retrieveDepartement(1);

        // Assert
        assertEquals(departement, actualDepartement);
    }

    @Test
    void testDeleteDepartement() {
        // Arrange
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        // Act
        departementService.deleteDepartement(1);

        // Assert
        verify(departementRepository).delete(departement);
    }
}
