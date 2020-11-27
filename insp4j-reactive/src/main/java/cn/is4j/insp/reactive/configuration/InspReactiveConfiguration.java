/*
 * Copyright 2020 zengzhihong All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.is4j.insp.reactive.configuration;

import cn.is4j.insp.core.exception.InspExceptionTranslator;
import cn.is4j.insp.core.intercept.aopalliance.InspAnnotationBeanPostProcessor;
import cn.is4j.insp.reactive.filter.ReactiveRequestContextFilter;
import cn.is4j.insp.reactive.intercept.aopalliance.MethodInspReactiveInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zengzhihong
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@Configuration
public class InspReactiveConfiguration {

    private InspExceptionTranslator inspExceptionTranslator;

    @Autowired(required = false)
    public void setInspExceptionTranslator(InspExceptionTranslator inspExceptionTranslator) {
        this.inspExceptionTranslator = inspExceptionTranslator;
    }

    @Bean
    public ReactiveRequestContextFilter reactiveRequestContextFilter() {
        return new ReactiveRequestContextFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public MethodInspReactiveInterceptor methodInspInterceptor() {
        return new MethodInspReactiveInterceptor(inspExceptionTranslator);
    }

    @Bean
    public InspAnnotationBeanPostProcessor inspAnnotationBeanPostProcessor(
            MethodInspReactiveInterceptor methodInspWebInterceptor) {
        return new InspAnnotationBeanPostProcessor(methodInspWebInterceptor);
    }

}
