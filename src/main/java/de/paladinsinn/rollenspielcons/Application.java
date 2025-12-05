/*
 * Copyright (c) 2025. Roland T. Lichti, Kaiserpfalz EDV-Service
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
        System.exit(exitCode);
    }

    @Override
    public void run() {
        if (updateDatabase) {
            // start Spring Boot with non-web type and a property to signal Liquibase-only run
            new SpringApplicationBuilder(Application.class)
                    .web(WebApplicationType.NONE)
                    .properties("spring.main.banner-mode=on", "app.update-database=true")
                    .run();
            // LiquibaseExitRunner will exit the JVM after Liquibase ran
        } else {
            // normal startup
            SpringApplication.run(Application.class);
        }
    }
}
