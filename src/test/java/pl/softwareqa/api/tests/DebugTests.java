package pl.softwareqa.api.tests;

import pl.softwareqa.api.tests.clients.PetClient;
import pl.softwareqa.api.tests.configuration.BaseTest;
import org.junit.jupiter.api.Test;

public class DebugTests extends BaseTest {

    PetClient pc = new PetClient(reporter);

    @Test
    public void firstTest(){
        pc.getPet(1);
    }

    @Test
    public void secondTest(){
        pc.getPet(2);
    }

    @Test
    public void thirdTest(){
        pc.getPet(3);
    }

}
