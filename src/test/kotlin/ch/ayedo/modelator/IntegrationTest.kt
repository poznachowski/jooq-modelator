package ch.ayedo.modelator

import ch.ayedo.modelator.core.configuration.Configuration
import ch.ayedo.modelator.core.configuration.DockerConfig
import ch.ayedo.modelator.core.configuration.HealthCheckConfig
import ch.ayedo.modelator.core.configuration.MigrationConfig
import ch.ayedo.modelator.core.configuration.MigrationEngine.FLYWAY
import ch.ayedo.modelator.core.configuration.MigrationEngine.LIQUIBASE
import ch.ayedo.modelator.core.configuration.PortMapping
import ch.ayedo.modelator.core.Modelator
import org.testng.annotations.Test
import java.nio.file.Path
import java.nio.file.Paths

class IntegrationTest {

    @Test
    fun flywayPostgres() {
        Modelator(Configuration(
            dockerConfig = DockerConfig(
                tag = "postgres:9.5",
                env = listOf("POSTGRES_DB=postgres", "POSTGRES_USER=postgres", "POSTGRES_PASSWORD=secret"),
                portMapping = PortMapping(5432, 5432)),
            healthCheckConfig = HealthCheckConfig(),
            migrationConfig = MigrationConfig(engine = FLYWAY, migrationsPath = getResourcePath("/migrations")),
            jooqConfigPath = getResourcePath("/postgresConfiguration.xml")
        )).generate()

        // TODO: check generated files
    }

    @Test
    fun liquibasePostgres() {
        Modelator(Configuration(
            dockerConfig = DockerConfig(
                tag = "postgres:9.5",
                env = listOf("POSTGRES_DB=postgres", "POSTGRES_USER=postgres", "POSTGRES_PASSWORD=secret"),
                portMapping = PortMapping(5432, 5432)),
            healthCheckConfig = HealthCheckConfig(),
            migrationConfig = MigrationConfig(engine = LIQUIBASE, migrationsPath = getResourcePath("/migrations/liquibaseChangelog.xml")),
            jooqConfigPath = getResourcePath("/postgresConfiguration.xml")
        )).generate()

        // TODO: check generated files
    }

    @Test
    fun flywayMariaDb() {
        Modelator(Configuration(
            dockerConfig = DockerConfig(
                tag = "mariadb:10.2",
                env = listOf("MYSQL_DATABASE=maria", "MYSQL_ROOT_PASSWORD=pass", "MYSQL_PASSWORD=pass"),
                portMapping = PortMapping(3306, 3306)),
            healthCheckConfig = HealthCheckConfig(),
            migrationConfig = MigrationConfig(engine = FLYWAY, migrationsPath = getResourcePath("/migrations")),
            jooqConfigPath = getResourcePath("/mariaDbConfiguration.xml")
        )).generate()

        // TODO: check generated files
    }

    @Test
    fun liquibaseMariaDb() {
        Modelator(Configuration(
            dockerConfig = DockerConfig(
                tag = "mariadb:10.2",
                env = listOf("MYSQL_DATABASE=maria", "MYSQL_ROOT_PASSWORD=pass", "MYSQL_PASSWORD=pass"),
                portMapping = PortMapping(3306, 3306)),
            healthCheckConfig = HealthCheckConfig(),
            migrationConfig = MigrationConfig(engine = LIQUIBASE, migrationsPath = getResourcePath("/migrations/liquibaseChangelog.xml")),
            jooqConfigPath = getResourcePath("/mariaDbConfiguration.xml")
        )).generate()

        // TODO: check generated files
    }

    private fun getResourcePath(path: String): Path = Paths.get(this.javaClass.getResource(path).toURI())
}