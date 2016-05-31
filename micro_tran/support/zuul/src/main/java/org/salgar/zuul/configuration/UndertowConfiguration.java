package org.salgar.zuul.configuration;

import org.springframework.boot.context.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.undertow.servlet.api.DeploymentInfo;

@Component
public class UndertowConfiguration {
	@Bean
	public UndertowEmbeddedServletContainerFactory getUndertowEmbeddedServletContainerFactory() {
		UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
		
		factory.addDeploymentInfoCustomizers(new UndertowDeploymentInfoCustomizer() {
			
			@Override
			public void customize(DeploymentInfo deploymentInfo) {
				deploymentInfo.setAllowNonStandardWrappers(true);
				
			}
		});

		return factory;
	}
}