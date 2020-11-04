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

package cn.is4j.insp.core.intercept.aopalliance;

import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @author zengzhihong
 */
public class InspAnnotationBeanPostProcessor extends AbstractAdvisingBeanPostProcessor
		implements BeanFactoryAware {

	private final MethodInterceptor interceptor;

	public InspAnnotationBeanPostProcessor(MethodInterceptor interceptor) {
		this.interceptor = interceptor;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		setBeforeExistingAdvisors(true);
		this.advisor = new MethodInspAnnotationAdvisor(this.interceptor);
	}
}
