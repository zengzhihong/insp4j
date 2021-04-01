/*
 * Copyright 2020-2021 zengzhihong All rights reserved.
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

import lombok.Getter;
import lombok.Setter;

/**
 * @author zengzhihong
 */
@Getter
@Setter
public class InspMetadataSource {

    private String groupName;

    private String expressionString;

    /**
     * deprecated at @since 2.x
     */
    @Deprecated
    private Object[] expressionArgs;

    private String attrExpressionString;

    private Object attrExpressionValue;

    public InspMetadataSource(String groupName, String expressionString) {
        this.groupName = groupName;
        this.expressionString = expressionString;
    }
}
