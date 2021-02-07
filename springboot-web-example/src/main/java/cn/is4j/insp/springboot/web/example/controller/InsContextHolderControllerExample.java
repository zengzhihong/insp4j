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

package cn.is4j.insp.springboot.web.example.controller;

import cn.is4j.insp.core.context.InspContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zengzhihong
 */
@RestController
@RequestMapping("/ctx")
public class InsContextHolderControllerExample {

    /**
     * 当前线程及子线程获取InspContext上下文 暂不支持线程池中的线程获取 如果有需求可以提issue
     *
     * @return
     */
    @GetMapping("/get")
    public String get() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ":" + ctxString());
            }
        }).start();
        return ctxString();
    }

    @SneakyThrows
    private String ctxString() {
        return new ObjectMapper().writeValueAsString(
                InspContextHolder.getContext().getAuthentication("a"));
    }
}
