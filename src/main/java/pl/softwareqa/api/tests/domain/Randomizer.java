package pl.softwareqa.api.tests.domain;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.List;
import java.util.Random;

public class Randomizer extends Faker {

    public static String randomTestName(){
        return "TEST_" + RandomStringUtils.randomAlphabetic(10);
    }

    public static String randomPastDate(){
            return LocalDate.now().minusDays(
                    new Random().nextInt(365*10)
            ).toString("yyyy-MM-dd");
    }

    static String randomPastDateTime(){
        return LocalDateTime.now().minusMinutes(
                new Random().nextInt(365*10*24*60)
        ).toString("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }

    static String randomFutureDate(){
        return LocalDate.now().plusDays(
                new Random().nextInt(365*10)
        ).toString("yyyy-MM-dd");
    }

    static String randomFutureDateTime(){
        return LocalDateTime.now().plusMinutes(
                new Random().nextInt(365*10*24*60)
        ).toString("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }

    public static <T> T randomFromList(List<T> list){
        return list.get(new Random().nextInt(list.size()));
    }

    public static String randomUrl(){
        return "http://www." + RandomStringUtils.randomAlphabetic(10).toLowerCase() + ".com";
    }

    public static String randomPhoneNumber(){ //TODO: Generate phone number based on location???
        return "+6141" + RandomStringUtils.randomNumeric(7);
    }

}

