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

package cn.is4j.insp.springboot.web.example.service;

import cn.is4j.insp.core.expression.InspMetadataSource;
import cn.is4j.insp.core.service.InspAuthentication;
import cn.is4j.insp.web.service.InspWebAuthenticationService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 需要实现 {@link InspWebAuthenticationService} 交由spring ioc 接下来就可以在controller层用 @Insp注解了 通过
 * groupName 隔离各个业务系统权限
 *
 * @author zengzhihong
 */
@Component
public class InspWebAuthenticationServiceImpl implements InspWebAuthenticationService {

    @Override
    public InspAuthentication loadAuthentication(HttpServletRequest request,
                                                 InspMetadataSource metadataSource) {
        final Long userId = getUserId();
        List<String> funcAuthorities = RedisStore.get(metadataSource.getGroupName(),
                userId, 0);
        List<String> dataAuthorities = RedisStore.get(metadataSource.getGroupName(),
                userId, 1);
        return new InspAuthentication(userId + "", funcAuthorities, dataAuthorities);
        // 如果是超管可以这样new
        // return InspAuthentication.createHighestAuth(userId + "");
    }

    private Long getUserId() {
        return ThreadLocalRandom.current().nextLong();
    }

    /**
     * 模拟从redis获取权限数据
     */
    static class RedisStore {

        public static List<String> get(String groupName, Long userId, int funcOrData) {

            String redisKey = groupName + ":" + userId + ":" + funcOrData;

            return getFromRedis(redisKey, funcOrData);
        }

        private static List<String> getFromRedis(String key, int funcOrData) {

            if (funcOrData == 0) {
                return Arrays.asList("user:list", "user:save", "user:get", "shop:list",
                        "enterprise:list");
            }
            if (funcOrData == 1) {
                return new ArrayList<String>() {
                    {
                        for (int i = 0; i < 10; i++) {
                            add(i + "");
                        }
                    }
                };
            }
            throw new IllegalArgumentException("error funcOrData");
        }
    }
}
