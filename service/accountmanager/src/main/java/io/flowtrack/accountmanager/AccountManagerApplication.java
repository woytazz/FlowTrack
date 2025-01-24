package io.flowtrack.accountmanager;

import io.flowtrack.shared.config.JpaAuditingConfig;
import io.flowtrack.shared.config.SchemaInitConfig;
import io.flowtrack.shared.security.GlobalSecurityConfig;
import io.flowtrack.shared.security.JwtInternalDecoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(value = {
        GlobalSecurityConfig.class, JwtInternalDecoder.class,
		SchemaInitConfig.class, JpaAuditingConfig.class
})
@SpringBootApplication
public class AccountManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountManagerApplication.class, args);
    }

}
