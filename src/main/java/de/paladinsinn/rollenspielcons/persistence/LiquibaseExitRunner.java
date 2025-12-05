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

package de.paladinsinn.rollenspielcons.persistence;

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

