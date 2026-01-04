package com.yatmk.test.adapter.output.persistence.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Slf4j
@AllArgsConstructor
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorUserAware")
@EnableJpaRepositories(
    entityManagerFactoryRef = LocalDB.EMF,
    transactionManagerRef = LocalDB.TM,
    repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class,
    basePackages = { LocalDB.BASE_REPO }
)
public class LocalDB {

    private final Environment env;

    public static final String DB = "local";

    public static final String DSP = DB + BasePackage.DSP;

    public static final String DS = DB + BasePackage.DS;

    public static final String JPA = DB + BasePackage.JPA;

    public static final String EMF = DB + BasePackage.EMF;

    public static final String TM = DB + BasePackage.TM;

    public static final String BASE_REPO = BasePackage.BASE_REPO + DB;

    public static final String BASE_MODEL = BasePackage.BASE_MODEL + DB;

    public static final String DSP_CONF = DB + "." + "spring.datasource";

    public static final String DS_CONF = DB + "." + "spring.datasource.configuration";

    @Primary
    @Bean(DSP)
    @ConfigurationProperties(DSP_CONF)
    public DataSourceProperties getDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(DS)
    @ConfigurationProperties(DS_CONF)
    public DataSource getDataSource(@Qualifier(DSP) DataSourceProperties dataSourceProperties) {
        log.info("Loading DB {} DataSource", DB);
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(JPA)
    public Map<String, String> getJpaProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put(
            "hibernate.hbm2ddl.auto",
            env.getProperty(String.format("%s.spring.jpa.hibernate.ddl-auto", DB))
        );
        properties.put("hibernate.show_sql", env.getProperty(String.format("%s.spring.jpa.show-sql", DB)));
        properties.put("hibernate.dialect", env.getProperty(String.format("%s.spring.jpa.database-platform", DB)));
        properties.put("hibernate.generate-ddl", env.getProperty(String.format("%s.spring.jpa.generate-ddl", DB)));
        properties.put("sql.init.mode", env.getProperty(String.format("%s.spring.sql.init.mode", DB)));

        return properties;
    }

    @Primary
    @Bean(EMF)
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(
        EntityManagerFactoryBuilder builder,
        @Qualifier(DS) DataSource dataSource,
        @Qualifier(JPA) Map<String, String> jpaProperties
    ) {
        log.info("Loading DB {} EntityManger & JPA", DB);
        return builder.dataSource(dataSource).properties(jpaProperties).packages(BASE_MODEL).build();
    }

    @Primary
    @Bean(TM)
    public PlatformTransactionManager getTransactionManager(
        @Qualifier(EMF) LocalContainerEntityManagerFactoryBean entityManagerFactory
    ) {
        log.info("Loading DB {} TransactionManager", DB);
        return Optional
            .ofNullable(entityManagerFactory)
            .map(LocalContainerEntityManagerFactoryBean::getObject)
            .map(JpaTransactionManager::new)
            .orElseThrow(() -> new RuntimeException("Error initilizing transaction manager"));
    }
}
