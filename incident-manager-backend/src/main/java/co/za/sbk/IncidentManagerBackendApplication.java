package co.za.sbk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//import org.springframework.boot.autoconfigure.web.WebMvcRegistrationsAdapter;
import java.lang.reflect.Method;

@SpringBootApplication
@ComponentScan(basePackages = "co.za.sbk")
public class IncidentManagerBackendApplication {

	@Value("${rest.api.base.path}")
	private String restApiBasePath;

	public static void main(String[] args) {
		SpringApplication.run(IncidentManagerBackendApplication.class, args);
	}

//	@Bean
//	public WebMvcRegistrationsAdapter webMvcRegistrationsHandlerMapping() {
//		IncidentManagerBackendApplication application = this;
//		return new WebMvcRegistrationsAdapter() {
//			@Override
//			public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
//				return new RequestMappingHandlerMapping() {
//
//					@Override
//					protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
//						Class<?> beanType = method.getDeclaringClass();
//						RestController restApiController = beanType.getAnnotation(RestController.class);
//						if (restApiController != null) {
//							PatternsRequestCondition apiPattern = new PatternsRequestCondition(application.restApiBasePath)
//									.combine(mapping.getPatternsCondition());
//
//							mapping = new RequestMappingInfo(mapping.getName(), apiPattern,
//									mapping.getMethodsCondition(), mapping.getParamsCondition(),
//									mapping.getHeadersCondition(), mapping.getConsumesCondition(),
//									mapping.getProducesCondition(), mapping.getCustomCondition());
//						}
//
//						super.registerHandlerMethod(handler, method, mapping);
//					}
//				};
//			}
//		};
//	}

}
