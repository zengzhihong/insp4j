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

package cn.is4j.insp.core.intercept.aopalliance;

import cn.is4j.insp.core.expression.InspMetadataSource;
import cn.is4j.insp.core.service.InspAuthentication;

/**
 * @author zengzhihong
 */
public interface InspInterceptor {

    InspAuthentication onAuthentication(InspMetadataSource metadataSource);
}
