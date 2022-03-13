package com.mercadolibre.animalia;

import com.mercadolibre.animalia.models.Citizen;
import com.mercadolibre.animalia.services.CitizenServices;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class CitizensTests {

    public CitizenServices citizensServices;

    @Test
    public void testSaveCitizens(){
        Citizen citizen = new Citizen();
        citizen.setName("Carlos");
        citizen.setIdentifier("55555-5");
        citizen.setHeight("15KG");
        citizen.setDescription("Testing");
       // citizen.setPet(true);
        citizen.setPhoto("https://www.citypng.com/public/uploads/small/11639594360nclmllzpmer2dvmrgsojcin90qmnuloytwrcohikyurvuyfzvhxeeaveigoiajks5w2nytyfpix678beyh4ykhgvmhkv3r3yj5hi.png");
       // citizen.setSpecies("Humano");
      //  citizensServices.createCitizen(citizen);
       /* citizensRepository.findById(new Long(1))
                .map(newUser ->{
                    Assert.assertEquals("John",newUser.getName());
                    return true;
                });*/
    }
}
