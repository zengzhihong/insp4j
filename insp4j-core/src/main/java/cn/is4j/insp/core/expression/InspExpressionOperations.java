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

package cn.is4j.insp.core.expression;

/**
 * @author zengzhihong
 */
public interface InspExpressionOperations {

    /**
     * single function authority
     *
     * @param funcAuthority
     * @return
     */
    boolean hasFunc(String funcAuthority);

    /**
     * single function、data authority composable
     *
     * @param funcAuthority
     * @param dataAuthority
     * @return
     */
    boolean hasFuncData(String funcAuthority, String dataAuthority);


    /**
     * matched any one function authority
     *
     * @param funcAuthority
     * @return
     */
    boolean hasAnyFunc(String... funcAuthority);

    /**
     * single data authority
     *
     * @param dataAuthority
     * @return
     */
    boolean hasData(String dataAuthority);

    /**
     * matched any one data authority
     *
     * @param dataAuthority
     * @return
     */
    boolean hasAnyData(String... dataAuthority);
}
