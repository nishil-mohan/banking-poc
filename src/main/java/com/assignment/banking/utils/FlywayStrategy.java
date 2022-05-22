package com.assignment.banking.utils;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.stereotype.Component;

@Component
public class FlywayStrategy implements FlywayMigrationStrategy {
    @Override
    public void migrate(Flyway flyway) {
        flyway.migrate();
    }
}
