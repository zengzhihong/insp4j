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

/**
 * @author zengzhihong
 */
public class InspExpressionRoot implements InspExpressionOperations {

    private final InspAuthentication authentication;

    public InspExpressionRoot(InspAuthentication authentication) {
        if (authentication == null) {
            throw new IllegalArgumentException("Authentication object cannot be null");
        }
        this.authentication = authentication;
    }

    /**
     * single function authority
     *
     * @param funcAuthority
     * @return
     */
    @Override
    public boolean hasFunc(String funcAuthority) {
        return hasAnyFunc(funcAuthority);
    }

    /**
     * single function、data authority composable
     *
     * @param funcAuthority
     * @param dataAuthority
     * @return
     */
    @Override
    public boolean hasFuncData(String funcAuthority, String dataAuthority) {
        return hasFunc(funcAuthority) && hasData(dataAuthority);
    }

    /**
     * matched any one function authority
     *
     * @param funcAuthority
     * @return
     */
    @Override
    public boolean hasAnyFunc(String... funcAuthority) {
        if (authentication.isHighestAuth()) {
            return true;
        }
        for (String s : funcAuthority) {
            if (authentication.getFuncAuthorities().contains(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * single data authority
     *
     * @param dataAuthority
     * @return
     */
    @Override
    public boolean hasData(String dataAuthority) {
        return hasAnyData(dataAuthority);
    }

    /**
     * matched any one data authority
     *
     * @param dataAuthority
     * @return
     */
    @Override
    public boolean hasAnyData(String... dataAuthority) {
        if (authentication.isHighestAuth()) {
            return true;
        }
        for (String s : dataAuthority) {
            if (authentication.getDataAuthorities().contains(s)) {
                return true;
            }
        }
        return false;
    }

}
