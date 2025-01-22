package io.flowtrack.gateway;

import io.flowtrack.shared.config.JpaAuditingConfig;
import io.flowtrack.shared.config.SchemaInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({SchemaInitializer.class, JpaAuditingConfig.class})
@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
