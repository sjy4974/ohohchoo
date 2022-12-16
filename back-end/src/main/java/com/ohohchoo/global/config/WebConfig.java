package com.ohohchoo.global.config;

import com.ohohchoo.domain.user.global.config.interceptor.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/resources/webjars/springfox-swagger-ui/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE");
    }

//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(jwtInterceptor)
//		.addPathPatterns("/**")
//		.excludePathPatterns("/swagger-resources/**",
//				"/swagger-ui/**",
//				"/v2/api-docs");
//	}

}
