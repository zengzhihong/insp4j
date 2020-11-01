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
import cn.is4j.insp.core.expression.DefaultMethodInspExpressionHandler;
import cn.is4j.insp.core.exception.InspExceptionTranslator;
import cn.is4j.insp.core.exception.ThrowableInsExceptionTranslator;
import cn.is4j.insp.core.exception.UnAuthenticationInspException;
import cn.is4j.insp.core.expression.InspExpressionHandler;
import cn.is4j.insp.core.expression.MethodInspInterceptorMetadataSource;
import cn.is4j.insp.core.service.InspAuthentication;
import lombok.Setter;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * aop core processing procedure
 *
 * @author zengzhihong
 */
public abstract class AbstractInspInterceptor {

    @Setter
    private InspExpressionHandler<MethodInvocation> expressionHandler = new DefaultMethodInspExpressionHandler();

    private final InspExceptionTranslator exceptionTranslator;

    public AbstractInspInterceptor() {
        this.exceptionTranslator = new ThrowableInsExceptionTranslator();
    }

    public AbstractInspInterceptor(InspExceptionTranslator exceptionTranslator) {
        this.exceptionTranslator = exceptionTranslator;
    }

    protected Object proceed(MethodInvocation invocation) throws Throwable {
        try {
            List<MethodInspInterceptorMetadataSource> metadataSources = obtainMetadataSource(invocation);
            for (MethodInspInterceptorMetadataSource metadataSource : metadataSources) {
                if (!StringUtils.hasText(metadataSource.getExpressionString())) {
                    continue;
                }
                EvaluationContext ctx = expressionHandler.createEvaluationContext(
                        metadataSource.getAuthentication(), metadataSource.getInvocation());
                final Boolean expressionValue = expressionHandler.getExpressionParser()
                        .parseExpression(metadataSource.getExpressionString()).getValue(ctx, Boolean.class);
                if (null == expressionValue || !expressionValue) {
                    throw new UnAuthenticationInspException("deny of access");
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


    protected List<MethodInspInterceptorMetadataSource> obtainMetadataSource(MethodInvocation invocation) {
        Insp onClass = AnnotationUtils.findAnnotation(invocation.getMethod().getDeclaringClass(), Insp.class);
        Insp onMethod = AnnotationUtils.findAnnotation(invocation.getMethod(), Insp.class);
        List<MethodInspInterceptorMetadataSource> metadataSources = new ArrayList<>();
        if (onClass != null) {
            metadataSources.add(
                    new MethodInspInterceptorMetadataSource(
                            invocation, loadAuthenticationCtx(onClass.groupName()), onClass.value()));
        }
        if (onMethod != null) {
            metadataSources.add(
                    new MethodInspInterceptorMetadataSource(
                            invocation, loadAuthenticationCtx(onMethod.groupName()), onMethod.value()));
        }
        return metadataSources;
    }

    private InspAuthentication loadAuthenticationCtx(String groupName) {
        InspAuthentication authentication = InspContextHolder.getContext().getAuthentication(groupName);
        if (null == authentication) {
            authentication = loadAuthentication(groupName);
            InspContextHolder.getContext().setAuthentication(groupName, authentication);
        }
        return authentication;
    }

    protected abstract InspAuthentication loadAuthentication(String groupName);

}
