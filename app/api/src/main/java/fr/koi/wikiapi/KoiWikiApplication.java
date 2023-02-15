package fr.koi.wikiapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class KoiWikiApplication {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);

        SpringApplication.run(KoiWikiApplication.class, args);
    }

}
