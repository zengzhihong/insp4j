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

package cn.is4j.insp4j.core.expression;

import cn.is4j.insp4j.core.service.InspAuthentication;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;

/**
 * @author zengzhihong
 */
public abstract class AbstractInspExpressionHandler<T> implements InspExpressionHandler<T>, ApplicationContextAware {

    private ExpressionParser expressionParser = new SpelExpressionParser();
    private BeanResolver br;

    @Override
    public ExpressionParser getExpressionParser() {
        return this.expressionParser;
    }

    public final void setExpressionParser(ExpressionParser expressionParser) {
        Assert.notNull(expressionParser, "expressionParser cannot be null");
        this.expressionParser = expressionParser;
    }

    @Override
    public final EvaluationContext createEvaluationContext(InspAuthentication authentication, T invocation) {
        final InspExpressionOperations inspExpressionRoot = createInspExpressionRoot(authentication, invocation);
        StandardEvaluationContext ctx = createEvaluationContextInternal(authentication, invocation);
        ctx.setBeanResolver(br);
        ctx.setRootObject(inspExpressionRoot);
        return ctx;
    }


    protected StandardEvaluationContext createEvaluationContextInternal(InspAuthentication authentication, T invocation) {
        return new StandardEvaluationContext();
    }

    protected abstract InspExpressionOperations createInspExpressionRoot(InspAuthentication authentication, T invocation);


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.br = new BeanFactoryResolver(applicationContext);
    }
}
