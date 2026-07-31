package com.qiujing.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiujing.interceptor.BaseInterceptor;
import com.qiujing.util.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
@Configuration
@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "NullableProblems"})
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private List<? extends BaseInterceptor> baseInterceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        baseInterceptors.stream().filter(BaseInterceptor::isEnabled).forEach(interceptor -> { //
            registry.addInterceptor(interceptor)
                    .addPathPatterns(interceptor.getPathPatterns())
                    .excludePathPatterns(interceptor.getInterceptorExcludePathPatterns());
        });
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    // ------------------------------------------------------------------------------------------------------------------------

    @Bean
    public ObjectMapper defaultObjectMapper() {
        return JsonHelper.newStandardObjectMapper();
    }

}
