package mybootapp.model.validators;

import mybootapp.Starter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;

import mybootapp.model.objects.Groupe;
import mybootapp.model.validators.GroupeValidator;
import mybootapp.model.objects.Personne;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = Starter.class)
@AutoConfigureMockMvc
public class GroupeValidatorTest {

    @BeforeEach
    public void setUp() {
        validator = new GroupeValidator();
    }

    private GroupeValidator validator;


    @Test
    public void testSupports() {
        Assertions.assertTrue(validator.supports(Groupe.class));
        Assertions.assertFalse(validator.supports(Personne.class));
    }

    @Test
    public void testValidate_nomTropCourt() {
        Groupe groupe = new Groupe();
        groupe.setNom("a");
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(groupe, "groupe");
        validator.validate(groupe, errors);
        Assertions.assertEquals(1, errors.getFieldErrors("nom").size());
        Assertions.assertEquals("groupe.nom.tropCourt", errors.getFieldError("nom").getCode());
    }


    @Test
    public void testValidate_nomTropLong() {
        Groupe groupe = new Groupe();
        groupe.setNom("""
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia,
                molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum
                numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium
                optio, eaque rerum! Provident similique accusantium nemo autem. Veritatis
                obcaecati tenetur iure eius earum ut molestias architecto voluptate aliquam
                nihil, eveniet aliquid culpa officia aut! Impedit sit sunt quaerat, odit,
                tenetur error, harum nesciunt ipsum debitis quas aliquid. Reprehenderit,
                quia. Quo neque error repudiandae fuga? Ipsa laudantium molestias eos\s
                sapiente officiis modi at sunt excepturi expedita sint? Sed quibusdam
                recusandae alias error harum maxime adipisci amet laborum. Perspiciatis\s
                minima nesciunt dolorem! Officiis iure rerum voluptates a cumque velit\s
                quibusdam sed amet tempora. Sit laborum ab, eius fugit doloribus tenetur\s
                fugiat, temporibus enim commodi iusto libero magni deleniti quod quam\s
                consequuntur! Commodi minima excepturi repudiandae velit hic maxime
                doloremque. Quaerat provident commodi consectetur veniam similique ad\s
                earum omnis ipsum saepe, voluptas, hic voluptates pariatur est explicabo\s
                fugiat, dolorum eligendi quam cupiditate excepturi mollitia maiores labore\s
                suscipit quas? Nulla, placeat. Voluptatem quaerat non architecto ab laudantium
                modi minima sunt esse temporibus sint culpa, recusandae aliquam numquam\s
                totam ratione voluptas quod exercitationem fuga. Possimus quis earum veniam\s
                quasi aliquam eligendi, placeat qui corporis!
                """);
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(groupe, "groupe");
        validator.validate(groupe, errors);
        Assertions.assertEquals(1, errors.getFieldErrors("nom").size());
        Assertions.assertEquals("groupe.nom.tropLong", errors.getFieldError("nom").getCode());
    }

    @Test
    public void testValidate_nomValide() {
        Groupe groupe = new Groupe();
        groupe.setNom("Groupe de mega bg");
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(groupe, "groupe");
        Assertions.assertEquals(0, errors.getFieldErrors("nom").size());
    }

}