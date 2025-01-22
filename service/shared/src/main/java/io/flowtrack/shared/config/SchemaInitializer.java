package io.flowtrack.shared.config;

import io.flowtrack.shared.common.CommonConstants;
import io.flowtrack.shared.exception.SchemaInitializerException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class SchemaInitializer implements BeanPostProcessor {

    @Value("${spring.liquibase.default-schema}")
    private String schemaName;

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof DataSource dataSource) {
            try {
                try (Connection conn = dataSource.getConnection()) {
                    try (Statement statement = conn.createStatement()) {
                        statement.execute(String.format(CommonConstants.CREATE_SCHEMA, schemaName));
                    }
                }
            } catch (SQLException e) {
                throw new SchemaInitializerException(e);
            }
        }
        return bean;
    }

}
