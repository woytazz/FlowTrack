package io.flowtrack.accountmanager;

import io.flowtrack.shared.config.JpaAuditingConfig;
import io.flowtrack.shared.config.SchemaInitConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({SchemaInitConfig.class, JpaAuditingConfig.class})
@SpringBootApplication
public class AccountManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountManagerApplication.class, args);
	}

}
