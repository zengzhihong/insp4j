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

package cn.is4j.insp.web.intercept.aopalliance;

import cn.is4j.insp.core.exception.InspExceptionTranslator;
import cn.is4j.insp.core.expression.InspMetadataSource;
import cn.is4j.insp.core.intercept.aopalliance.AbstractInspInterceptor;
import cn.is4j.insp.core.service.InspAuthentication;
import cn.is4j.insp.web.service.InspWebAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @author zengzhihong
 */
@Slf4j
public class MethodInspWebInterceptor extends AbstractInspInterceptor
        implements MethodInterceptor {

    private final InspWebAuthenticationService authenticationService;

    public MethodInspWebInterceptor(InspWebAuthenticationService authenticationService,
                                    InspExceptionTranslator inspExceptionTranslator) {
        this.authenticationService = authenticationService;
        if (null != inspExceptionTranslator) {
            super.setExceptionTranslator(inspExceptionTranslator);
        }

    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return super.proceed(invocation);
    }

    @Override
    public InspAuthentication loadAuthentication(InspMetadataSource metadataSource) {
        return authenticationService
                .loadAuthentication(((ServletRequestAttributes) Objects
                                .requireNonNull(RequestContextHolder.getRequestAttributes()))
                                .getRequest(),
                        metadataSource);
    }

}
