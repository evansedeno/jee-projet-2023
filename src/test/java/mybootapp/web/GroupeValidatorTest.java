package mybootapp.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;

import mybootapp.controller.MainController;
import mybootapp.model.objects.Groupe;
import mybootapp.model.validators.GroupeValidator;
import mybootapp.model.IDirectoryDAO;
import mybootapp.model.objects.Personne;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ContextConfiguration(classes = Starter.class)
@AutoConfigureMockMvc
public class GroupeValidatorTest {




    @Mock
    private IDirectoryDAO directoryDAO;

    @Autowired
    private MockMvc mockMvc;


    @InjectMocks
    private MainController directoryController;


    @BeforeEach
    public void setUp() {
        validator = new GroupeValidator();
    }


    private GroupeValidator validator;
    private Groupe groupe;



    @Test
    public void testSupports(){
        Assertions.assertTrue(validator.supports(Groupe.class));
        Assertions.assertFalse(validator.supports(Personne.class));
    }


    @Test
    public void testValidate_nomTropCourt(){
        Groupe groupe = new Groupe();
        groupe.setNom("a");
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(groupe, "groupe");
        validator.validate(groupe, errors);
        Assertions.assertEquals(1, errors.getFieldErrors("nom").size());
        Assertions.assertEquals("groupe.nom.tropCourt", errors.getFieldError("nom").getCode());
    }


    @Test
    public void testValidate_nomTropLong(){
        Groupe groupe = new Groupe();
        groupe.setNom("Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia,\n" +
                "molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum\n" +
                "numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium\n" +
                "optio, eaque rerum! Provident similique accusantium nemo autem. Veritatis\n" +
                "obcaecati tenetur iure eius earum ut molestias architecto voluptate aliquam\n" +
                "nihil, eveniet aliquid culpa officia aut! Impedit sit sunt quaerat, odit,\n" +
                "tenetur error, harum nesciunt ipsum debitis quas aliquid. Reprehenderit,\n" +
                "quia. Quo neque error repudiandae fuga? Ipsa laudantium molestias eos \n" +
                "sapiente officiis modi at sunt excepturi expedita sint? Sed quibusdam\n" +
                "recusandae alias error harum maxime adipisci amet laborum. Perspiciatis \n" +
                "minima nesciunt dolorem! Officiis iure rerum voluptates a cumque velit \n" +
                "quibusdam sed amet tempora. Sit laborum ab, eius fugit doloribus tenetur \n" +
                "fugiat, temporibus enim commodi iusto libero magni deleniti quod quam \n" +
                "consequuntur! Commodi minima excepturi repudiandae velit hic maxime\n" +
                "doloremque. Quaerat provident commodi consectetur veniam similique ad \n" +
                "earum omnis ipsum saepe, voluptas, hic voluptates pariatur est explicabo \n" +
                "fugiat, dolorum eligendi quam cupiditate excepturi mollitia maiores labore \n" +
                "suscipit quas? Nulla, placeat. Voluptatem quaerat non architecto ab laudantium\n" +
                "modi minima sunt esse temporibus sint culpa, recusandae aliquam numquam \n" +
                "totam ratione voluptas quod exercitationem fuga. Possimus quis earum veniam \n" +
                "quasi aliquam eligendi, placeat qui corporis!\n");
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(groupe, "groupe");
        validator.validate(groupe, errors);
        Assertions.assertEquals(1, errors.getFieldErrors("nom").size());
        Assertions.assertEquals("groupe.nom.tropLong", errors.getFieldError("nom").getCode());
    }

    @Test
    public void testValidate_nomValide(){
        Groupe groupe =new Groupe();
        groupe.setNom("Groupe de mega bg");
        BeanPropertyBindingResult errors =new BeanPropertyBindingResult(groupe,"groupe");
        Assertions.assertEquals(0,errors.getFieldErrors("nom").size());
    }

}