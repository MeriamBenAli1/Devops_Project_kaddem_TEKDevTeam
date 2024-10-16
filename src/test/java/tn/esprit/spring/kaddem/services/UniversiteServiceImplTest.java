package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UniversiteServiceImplTest {
    @Mock
    UniversiteRepository universiteRepository;
    @Mock
    DepartementRepository departementRepository;

    @InjectMocks
    UniversiteServiceImpl universiteService;

    Universite universite;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        universite = new Universite(1, "TUNIS");
    }
    @Test
    void testRetrieveAllUniversites() {
        when(universiteRepository.findAll()).thenReturn(Arrays.asList(universite));
        List<Universite> universites = universiteService.retrieveAllUniversites();

        assertNotNull(universites);
        assertEquals(1, universites.size());

        verify(universiteRepository).findAll();
    }
    @Test
    void testAddUniversite() {
        when(universiteRepository.save(universite)).thenReturn(universite);
        Universite universite1 = universiteService.addUniversite(universite);

        assertNotNull(universite1);
        assertEquals("TUNIS", universite1.getNomUniv());

        verify(universiteRepository).save(universite);
    }
    @Test
    void testUpdateUniversite() {
        Universite updatedUniversite = new Universite(1, "TUNIS Updated");
        when(universiteRepository.save(universite)).thenReturn(updatedUniversite);

        Universite result = universiteService.updateUniversite(universite);

        assertNotNull(result);
        assertEquals("TUNIS Updated", result.getNomUniv());
        verify(universiteRepository).save(universite);
    }
    @Test
    void addUniversite_nullUniversite() {
        when(universiteRepository.save(null)).thenThrow(new IllegalArgumentException("Universite cannot be null"));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            universiteService.addUniversite(null);
        });

        assertEquals("Universite cannot be null", exception.getMessage());
        verify(universiteRepository).save(null);
    }
    @Test
    void testRetrieveUniversite() {
       when(universiteRepository.findById(1)).thenReturn(java.util.Optional.of(universite));
       Universite universiteRetreived = universiteService.retrieveUniversite(1);

       assertNotNull(universiteRetreived);
       assertEquals(1, universiteRetreived.getIdUniv());

       verify(universiteRepository).findById(1);
    }
    @Test
    void testDeleteUniversite() {
        when(universiteRepository.findById(1)).thenReturn(java.util.Optional.of(universite));
        universiteService.deleteUniversite(1);

        verify(universiteRepository).delete(universite);

    }

    @Test
    void testAssaignUniversiteToDepartement() {
        Departement departement = new Departement(1, "Informatique");
        Set<Departement> departements = new HashSet<>();
        departements.add(departement);
        universite.setDepartements(departements);

        when(universiteRepository.findById(1)).thenReturn(java.util.Optional.of(universite));
        when(departementRepository.findById(1)).thenReturn(java.util.Optional.of(departement));

        universiteService.assignUniversiteToDepartement(1, 1);
        verify(universiteRepository).save(universite);
        assertTrue(universite.getDepartements().contains(departement));
    }
    @Test
    void testRetreiveDepartementsByUniversite() {
        Departement departement = new Departement(1, "Informatique");
        Set<Departement> departements = new HashSet<>();
        departements.add(departement);
        universite.setDepartements(departements);

        when(universiteRepository.findById(1)).thenReturn(java.util.Optional.of(universite));

        Set<Departement> departements1 = universiteService.retrieveDepartementsByUniversite(1);

        assertNotNull(departements1);
        assertEquals(1, departements1.size());
        verify(universiteRepository).findById(1);
    }


}