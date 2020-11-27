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

import cn.is4j.insp.core.annotation.Insp;
import cn.is4j.insp.core.context.InspContextHolder;
import cn.is4j.insp.core.exception.InspException;
import cn.is4j.insp.core.exception.InspExceptionTranslator;
import cn.is4j.insp.core.exception.ThrowableInsExceptionTranslator;
import cn.is4j.insp.core.exception.UnAuthenticationInspException;
import cn.is4j.insp.core.expression.DefaultMethodInspExpressionHandler;
import cn.is4j.insp.core.expression.InspExpressionHandler;
import cn.is4j.insp.core.expression.InspExpressionInvocationHandler;
import cn.is4j.insp.core.expression.InspExpressionOperations;
import cn.is4j.insp.core.expression.InspExpressionRoot;
import cn.is4j.insp.core.expression.InspMetadataSource;
import cn.is4j.insp.core.expression.method.MethodInspEvaluationContext;
import cn.is4j.insp.core.service.InspAuthentication;
import lombok.Getter;
import lombok.Setter;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.BeanResolver;
import org.springframework.util.StringUtils;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * aop core processing procedure
 *
 * @author zengzhihong
 */
public abstract class AbstractInspInterceptor
        implements InspInterceptor, ApplicationContextAware {

    @Getter
    private ApplicationContext applicationContext;
    @Setter
    private InspExceptionTranslator exceptionTranslator;
    @Setter
    private InspExpressionHandler expressionHandler = new DefaultMethodInspExpressionHandler();
    private BeanResolver br;

    public AbstractInspInterceptor() {
        this.exceptionTranslator = new ThrowableInsExceptionTranslator();
    }


    protected Object proceed(MethodInvocation invocation) throws Throwable {
        try {
            List<InspMetadataSource> metadataSources = obtainMetadataSource(invocation);
            for (InspMetadataSource metadataSource : metadataSources) {
                if (!StringUtils.hasText(metadataSource.getExpressionString())) {
                    continue;
                }
                MethodInspEvaluationContext ctx = (MethodInspEvaluationContext) expressionHandler
                        .createEvaluationContext(invocation);
                final InspExpressionOperations expressionOperations = (InspExpressionOperations) Proxy
                        .newProxyInstance(InspExpressionRoot.class.getClassLoader(),
                                InspExpressionRoot.class.getInterfaces(),
                                new InspExpressionInvocationHandler(
                                        new InspExpressionRoot(), this, metadataSource));
                ctx.setRootObject(expressionOperations);
                ctx.setBeanResolver(br);
                // do invoke
                final Boolean expressionValue = expressionHandler.getExpressionParser()
                        .parseExpression(metadataSource.getExpressionString())
                        .getValue(ctx, Boolean.class);
                if (null == expressionValue || !expressionValue) {
                    throw new UnAuthenticationInspException("deny of access", metadataSource);
                }
            }
        } catch (Exception e) {
            // exceptionTranslator may be trigger another exception
            // so clearContext before exceptionTranslator
            InspContextHolder.clearContext();
            if (e instanceof InspException) {
                exceptionTranslator.translate((InspException) e);
                return null;
            }
            throw e;
        }
        final Object result;
        try {
            result = invocation.proceed();
        } finally {
            InspContextHolder.clearContext();
        }
        return result;
    }

    protected List<InspMetadataSource> obtainMetadataSource(MethodInvocation invocation) {
        Insp onClass = AnnotationUtils
                .findAnnotation(invocation.getMethod().getDeclaringClass(), Insp.class);
        Insp onMethod = AnnotationUtils.findAnnotation(invocation.getMethod(),
                Insp.class);
        List<InspMetadataSource> metadataSources = new ArrayList<>();
        if (onClass != null) {
            metadataSources
                    .add(new InspMetadataSource(onClass.groupName(), onClass.value()));
        }
        if (onMethod != null) {
            metadataSources
                    .add(new InspMetadataSource(onMethod.groupName(), onMethod.value()));
        }
        return metadataSources;
    }

    @Override
    public InspAuthentication onAuthentication(InspMetadataSource metadataSource) {
        InspAuthentication authentication = InspContextHolder.getContext()
                .getAuthentication(metadataSource.getGroupName());
        if (null == authentication) {
            authentication = loadAuthentication(metadataSource);
            InspContextHolder.getContext()
                    .setAuthentication(metadataSource.getGroupName(), authentication);
        }
        return authentication;
    }

    protected abstract InspAuthentication loadAuthentication(
            InspMetadataSource metadataSource);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
        this.br = new BeanFactoryResolver(applicationContext);
    }
}
