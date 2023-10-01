package com.mvm.servicecdrapi.integrationtest;

import com.mvm.servicecdrapi.ServiceCdrApiApplication;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ServiceCdrApiApplication.class})
@Tag("integrationTest")
public class BaseIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:12.9")
            .withDatabaseName("testDb").withUsername("test").withPassword("test");

    @DynamicPropertySource
    static void postgresSqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.flyway.url", postgresContainer::getJdbcUrl);
        registry.add("spring.flyway.user", postgresContainer::getUsername);
        registry.add("spring.flyway.password", postgresContainer::getPassword);
    }
}
