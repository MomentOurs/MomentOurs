package beyond.momentours.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@OpenAPIDefinition(info = @Info(title="Momentours API 명세서",
                                description = "Momentours API 명세서",
                                version = "v1"))
@Configuration
public class SwaggerConfiguration {

    @Bean
    @Profile("!Prod") // 운영환경은 제외
    public GroupedOpenApi userApi() {

        String[] paths = {"/api/user/**"};

        return GroupedOpenApi
                .builder()
                .group("유저 관련 api")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(buildSecurityOpenApi()).build();
    }

    @Bean
    @Profile("!Prod") // 운영환경은 제외
    public GroupedOpenApi coupleApi() {

        String[] paths = {"/api/couple/**"};

        return GroupedOpenApi
                .builder()
                .group("커플 관련 api")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(buildSecurityOpenApi()).build();
    }

    @Bean
    @Profile("!Prod") // 운영환경은 제외
    public GroupedOpenApi momentApi() {

        String[] paths = {"/api/moment/**"};

        return GroupedOpenApi
                .builder()
                .group("추억 관련 api")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(buildSecurityOpenApi()).build();
    }

    @Bean
    @Profile("!Prod") // 운영환경은 제외
    public GroupedOpenApi planApi() {

        String[] paths = {"/api/plan/**"};

        return GroupedOpenApi
                .builder()
                .group("계획 관련 api")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(buildSecurityOpenApi()).build();
    }

    public OpenApiCustomizer buildSecurityOpenApi() {
        // jwt token을 한번 설정하면 header에 값을 넣어주는 코드
        return OpenApi -> OpenApi.addSecurityItem(new SecurityRequirement().addList("jwt token"))
                .getComponents().addSecuritySchemes("jwt token", new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .in(SecurityScheme.In.HEADER)
                        .bearerFormat("JWT")
                        .scheme("bearer"));
    }
}
