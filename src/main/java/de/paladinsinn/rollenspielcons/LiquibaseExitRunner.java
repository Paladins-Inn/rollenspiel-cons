package de.paladinsinn.rollenspielcons;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class LiquibaseExitRunner implements ApplicationRunner {

    private final ApplicationContext ctx;
    private final Environment env;

    public LiquibaseExitRunner(ApplicationContext ctx, Environment env) {
        this.ctx = ctx;
        this.env = env;
    }

    @Override
    public void run(ApplicationArguments args) {
        boolean flagFromArgs = args.containsOption("update-database") || args.containsOption("u");
        boolean flagFromProp = Boolean.parseBoolean(env.getProperty("app.update-database", "false"));

        if (flagFromArgs || flagFromProp) {
            System.out.println("--update-database detected: Liquibase should have run during startup. Exiting application.");
            int exitCode = SpringApplication.exit(ctx, () -> 0);
            // System.exit to ensure process termination in all environments
            System.exit(exitCode);
        }
        // otherwise do nothing and let the application run normally
    }
}

