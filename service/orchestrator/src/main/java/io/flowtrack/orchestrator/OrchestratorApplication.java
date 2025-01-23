package io.flowtrack.orchestrator;

import io.flowtrack.shared.config.JpaAuditingConfig;
import io.flowtrack.shared.config.SchemaInitConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({SchemaInitConfig.class, JpaAuditingConfig.class})
@SpringBootApplication
public class OrchestratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrchestratorApplication.class, args);
	}

}
