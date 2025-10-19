package de.paladinsinn.rollenspielcons;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.SpringApplication;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@SpringBootApplication
@Command(name = "rollenspiel-cons", mixinStandardHelpOptions = true,
        description = "rollenspiel-cons application launcher with optional DB update-only mode")
public class Application implements Runnable {

    @Option(names = {"-u", "--update-database"}, description = "Run Liquibase database update only and exit")
    boolean updateDatabase;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }

    @Override
    public void run() {
        if (updateDatabase) {
            // start Spring Boot with non-web type and a property to signal Liquibase-only run
            new SpringApplicationBuilder(Application.class)
                    .web(WebApplicationType.NONE)
                    .properties("spring.main.banner-mode=on", "app.update-database=true", "spring.active.profiles=local")
                    .run();
            // LiquibaseExitRunner will exit the JVM after Liquibase ran
        } else {
            // normal startup
            SpringApplication.run(Application.class);
        }
    }
}
