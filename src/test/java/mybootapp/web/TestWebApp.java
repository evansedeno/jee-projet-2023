package mybootapp.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import mybootapp.controller.DirectoryController;
import mybootapp.controller.DirectoryService;
import mybootapp.model.Groupe;
import mybootapp.model.Personne;
import org.junit.jupiter.api.BeforeEach;
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
    public void testTrouverGroupeParId() {
        DirectoryService directoryService = Mockito.mock(DirectoryService.class);

        Groupe groupe = new Groupe("Groupe 1");
        directoryService.ajouterGroupe(groupe);
        when(directoryService.trouverGroupeParId(1)).thenReturn(groupe);

        Groupe result = directoryService.trouverGroupeParId(1);
        Assertions.assertEquals("Groupe 1", result.getNom());
    }


}