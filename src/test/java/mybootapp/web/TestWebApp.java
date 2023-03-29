package mybootapp.web;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.ui.Model;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import mybootapp.controller.DirectoryController;
import mybootapp.controller.DirectoryService;
import mybootapp.model.Groupe;
import mybootapp.model.Personne;
import org.hibernate.validator.internal.engine.groups.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Arrays;
import java.util.Date;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class DirectoryControllerTest {


    @Mock
    private DirectoryService directoryService;

    @Autowired
    private MockMvc mockMvc;


    @InjectMocks
    private DirectoryController directoryController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(directoryController).build();
    }



    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }


    @Test
    public void testAfficherGroupe() throws Exception {
        long groupeId = 1L;
        Groupe groupe = new Groupe("Groupe 1");
        when(directoryService.trouverGroupeParId(groupeId)).thenReturn(groupe);

        List<Personne> personnes = Arrays.asList(new Personne("Nom1", "Prenom1", "email1@example.com", "http://example.com", new Date(), groupe, "mdp1"),
                new Personne("Nom2", "Prenom2", "email2@example.com", "http://example.com", new Date(), groupe, "mdp2"));
        when(directoryService.trouverPersonnesParGroupe(groupe)).thenReturn(personnes);

        mockMvc.perform(MockMvcRequestBuilders.get("/groupe").param("id", Long.toString(groupeId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("groupe"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("groupe"))
                .andExpect(MockMvcResultMatchers.model().attribute("groupe", groupe))
                .andExpect(MockMvcResultMatchers.model().attributeExists("personnes"))
                .andExpect(MockMvcResultMatchers.model().attribute("personnes", personnes));
    }


}