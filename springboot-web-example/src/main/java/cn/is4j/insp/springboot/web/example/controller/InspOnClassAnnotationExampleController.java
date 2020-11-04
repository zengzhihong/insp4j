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

package cn.is4j.insp.springboot.web.example.controller;

import cn.is4j.insp.core.annotation.Insp;
import cn.is4j.insp.core.context.InspContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注解在类上定义 会类下面的所有方法都会进行权限校验 注解在类上有定义 方法上也有定义 则是 且的关系 先校验类上的权限 再校验方法上的权限
 *
 * @author zengzhihong
 */
@Insp(value = "hasFunc('user:list')", groupName = "a")
@RestController
@RequestMapping("/class")
public class InspOnClassAnnotationExampleController {

    @SneakyThrows
    @Insp(value = "hasFunc('shop:list')", groupName = "a")
    @GetMapping("/1")
    public String class1() {
        return new ObjectMapper().writeValueAsString(
                InspContextHolder.getContext().getAuthentication("a"));
    }

    @SneakyThrows
    @Insp(value = "hasFunc('user:save1')", groupName = "b")
    @GetMapping("/2")
    public String class2() {
        return new ObjectMapper().writeValueAsString(
                InspContextHolder.getContext().getAuthentication("b"));
    }

}
