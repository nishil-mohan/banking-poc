package com.assignment.banking.config.security;

import io.jaegertracing.Configuration;
import io.opentracing.Tracer;
import org.springframework.context.annotation.Bean;

public class TraceConfig {

    @Bean
    public Tracer getTracer() {
        Configuration.SamplerConfiguration samplerConfig = Configuration
                .SamplerConfiguration.fromEnv()
                .withType("const").withParam(1);
        Configuration.ReporterConfiguration reporterConfig = Configuration
                .ReporterConfiguration.fromEnv()
                .withLogSpans(true);
        Configuration config = new Configuration("banking-service")
                .withSampler(samplerConfig)
                .withReporter(reporterConfig);
        return config.getTracer();
    }

}
