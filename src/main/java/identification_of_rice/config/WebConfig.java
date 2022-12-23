package identification_of_rice.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String profilePath = System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + "main"
                + File.separator + "webapp"
                + File.separator + "profile"
                + File.separator;
        String pestPath = System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + "main"
                + File.separator + "webapp"
                + File.separator + "pest"
                + File.separator;
        String deepLearningPath = System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + "main"
                + File.separator + "webapp"
                + File.separator + "deep_learning"
                + File.separator;
        registry.addResourceHandler("/profile/**").addResourceLocations("file:", profilePath);
        registry.addResourceHandler("/pestFile/**").addResourceLocations("file:", pestPath);
        registry.addResourceHandler("/deepLearningFile/**").addResourceLocations("file:", deepLearningPath);
    }
}
