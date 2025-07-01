package auth.papertrail.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import auth.papertrail.app.interceptor.AuthInterceptor;

@Configuration
public class WebServerConfig implements WebMvcConfigurer {

    private AuthInterceptor authInterceptor;

    @Autowired
    public WebServerConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(authInterceptor);
    }

}
