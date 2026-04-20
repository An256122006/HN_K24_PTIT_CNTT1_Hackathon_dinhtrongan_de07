package org.example.config;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.jspecify.annotations.Nullable;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class webint extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?> @Nullable [] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?> @Nullable [] getServletConfigClasses() {
        return new Class<?>[] { AppConfig.class, UploadConfig.class};
    }

    @Override
    protected String[]  getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
            "E:\\web_service\\HN_K24_CNTT1_Hackathon_DinhTrongAn\\src\\main\\images\\",
                10*1024*1024,
                10*1024*1024,
                0
        );
        registration.setMultipartConfig(multipartConfigElement);
    }
}
