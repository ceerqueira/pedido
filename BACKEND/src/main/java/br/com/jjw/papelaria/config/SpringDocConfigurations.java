package br.com.jjw.papelaria.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocConfigurations {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API para uma papelaria")
                        .description(
                                "API Rest para uma papelaria, contendo as funcionalidades de CRUD de produtos, carrinho e itens(cesta do carrinho)")
                        );
    }
}
