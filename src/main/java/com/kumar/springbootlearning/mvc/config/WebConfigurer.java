package com.kumar.springbootlearning.mvc.config;

import com.kumar.springbootlearning.mvc.formatter.StringInstantFormatter;
import com.kumar.springbootlearning.mvc.interceptor.MultiTenantHandlerInterceptor;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.data.web.config.SortHandlerMethodArgumentResolverCustomizer;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static java.net.URLDecoder.decode;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
@Slf4j
public class WebConfigurer implements ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory>, WebMvcConfigurer {

    private final Environment env;

//    private final ComWebProperties webProperties;
    @Autowired
    private MultiTenantHandlerInterceptor multiTenantInterceptor;

    public WebConfigurer(Environment env) {
        this.env = env;
//        this.webProperties = webProperties;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (env.getActiveProfiles().length != 0) {
            log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }

        log.info("Web application fully configured");
    }

    /**
     * Customize the Servlet engine: Mime types, the document root, the cache.
     */
    @Override
    public void customize(WebServerFactory server) {
        // When running in an IDE or with ./mvnw spring-boot:run, set location of the static web assets.
        setLocationForStaticAssets(server);
    }

    private void setLocationForStaticAssets(WebServerFactory server) {
        if (server instanceof ConfigurableServletWebServerFactory servletWebServer) {
            File root;
            String prefixPath = resolvePathPrefix();
            root = new File(prefixPath + "target/classes/static/");
            if (root.exists() && root.isDirectory()) {
                servletWebServer.setDocumentRoot(root);
            }
        }
    }

    /**
     * Resolve path prefix to static resources.
     */
    private String resolvePathPrefix() {
        String fullExecutablePath = decode(this.getClass().getResource("").getPath(), StandardCharsets.UTF_8);
        String rootPath = Paths.get(".").toUri().normalize().getPath();
        String extractedPath = fullExecutablePath.replace(rootPath, "");
        int extractionEndIndex = extractedPath.indexOf("target/");
        if (extractionEndIndex <= 0) {
            return "";
        }
        return extractedPath.substring(0, extractionEndIndex);
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = webProperties.getCors();
//        if (!CollectionUtils.isEmpty(config.getAllowedOrigins()) ||
//                !CollectionUtils.isEmpty(config.getAllowedOriginPatterns())) {
//            log.debug("Registering CORS filter");
//            source.registerCorsConfiguration("/api/**", config);
//            source.registerCorsConfiguration("/management/**", config);
//            source.registerCorsConfiguration("/v3/api-docs", config);
//            source.registerCorsConfiguration("/swagger-ui/**", config);
//        }
//        return new CorsFilter(source);
//    }
//
    /**
     * Configure the converters to use the ISO format for dates by default.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        log.info("Registering StringToInstantConverter");
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);
        registry.addFormatter(new StringInstantFormatter());
    }

//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        resolvers.add(new FilterResolver());
//        final SnakeCaseToCamelCaseSortResolver sortResolver = new SnakeCaseToCamelCaseSortResolver();
//        sortResolver.setSortParameter("_sort");
//        resolvers.add(sortResolver);
//        PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver(sortResolver);
//        pageableResolver.setPrefix("_");
//        resolvers.add(pageableResolver);
//
//    }
//
//    @Bean
//    public PageableHandlerMethodArgumentResolverCustomizer pageableHandlerCustomizer() {
//        return new PageableHandlerMethodArgumentResolverCustomizer() {
//            @Override
//            public void customize(PageableHandlerMethodArgumentResolver pageableResolver) {
//                pageableResolver.setPageParameterName("_page");
//                pageableResolver.setSizeParameterName("_size");
//            }
//        };
//    }
//
//    @Bean
//    public SortHandlerMethodArgumentResolverCustomizer sortHandlerCustomizer() {
//        return new SortHandlerMethodArgumentResolverCustomizer() {
//            @Override
//            public void customize(SortHandlerMethodArgumentResolver sortResolver) {
//                sortResolver.setSortParameter("_sort");
//            }
//        };
//    }
//
//    @Bean
//    public OpenAPI customOpenAPI(@Value("${application-description}") String appDescription,
//                                 @Value("${application-version}") String appVersion,
//                                 @Value("${spring.application.name}") String appName,
//                                 @Value("${spring.security.oauth2.client.provider.oidc.issuer-uri}") String authBaseUrl) {
//        final Info info = new Info().title(appName)
//                .version(appVersion)
//                .description(appDescription)
//                .license(new License().name("Proprietary").url("https://logistiex.com"));
//
//        SecurityRequirement securityRequirement = new SecurityRequirement();
//        securityRequirement.addList("OAuth2");
//        final List<SecurityRequirement> security = Collections.singletonList(securityRequirement);
//
//        final OAuthFlows oAuthFlows = new OAuthFlows().authorizationCode(new OAuthFlow().tokenUrl(authBaseUrl + "/protocol/openid-connect/token")
//                .authorizationUrl(authBaseUrl + "/protocol/openid-connect/auth"));
//        final SecurityScheme securityScheme = new SecurityScheme().type(SecurityScheme.Type.OAUTH2).flows(oAuthFlows)
//                .in(SecurityScheme.In.HEADER).scheme("bearer")
//                .bearerFormat("JWT");
//
//        return new OpenAPI().info(info)
//                .servers(Collections.singletonList(new Server().url("/").description("Default Base URL")))
//                .security(security)
//                .components(new Components().addSecuritySchemes("OAuth2", securityScheme));
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(multiTenantInterceptor);
    }
}
