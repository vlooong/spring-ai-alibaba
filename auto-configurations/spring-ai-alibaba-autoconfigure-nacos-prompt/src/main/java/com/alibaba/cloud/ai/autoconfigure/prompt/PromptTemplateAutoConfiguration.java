/*
 * Copyright 2024-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.cloud.ai.autoconfigure.prompt;

import com.alibaba.cloud.ai.prompt.ConfigurablePromptTemplateFactory;

import com.alibaba.cloud.ai.prompt.PromptTemplateBuilderConfigure;
import com.alibaba.cloud.ai.prompt.PromptTemplateCustomizer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

/**
 * @author KrakenZJC
 * @author yuluo
 */

@AutoConfiguration
@Conditional(PromptTmplNacosConfigCondition.class)
@EnableConfigurationProperties(NacosPromptTmplProperties.class)
public class PromptTemplateAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public PromptTemplateBuilderConfigure promptTemplateBuilderConfigure(
			ObjectProvider<PromptTemplateCustomizer> customizerProvider) {
		PromptTemplateBuilderConfigure promptTemplateBuilderConfigure = new PromptTemplateBuilderConfigure();
		promptTemplateBuilderConfigure.setPromptTemplateBuilderCustomizers(customizerProvider.orderedStream().toList());
		return promptTemplateBuilderConfigure;
	}

	// @formatter:off
	@Bean
	@ConditionalOnMissingBean
	public ConfigurablePromptTemplateFactory configurablePromptTemplateFactory(PromptTemplateBuilderConfigure promptTemplateBuilderConfigure) {

		return new ConfigurablePromptTemplateFactory(promptTemplateBuilderConfigure);
	}
	// @formatter:on

}
