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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zengzhihong
 */
@RestController
@RequestMapping("/user")
public class FuncDataExampleController {

    /**
     * 拥有功能权限：user:list 就放行
     *
     * @param a
     * @return
     */
    @Insp(value = "hasFunc('user:list')")
    @RequestMapping("/listUser1")
    public String listUser1(String a) {
        return "ok1";
    }

    /**
     * 拥有功能权限：user:list 并且 拥有数据权限 shopId是动态传的 也可以写死 如：@Insp("hasFuncData('user:list',1)")
     *
     * @param shopId
     * @return
     */
    @Insp("hasFuncData('user:list',#shopId)")
    @RequestMapping("/listUser2")
    public String listUser2(String shopId) {
        return "ok2";
    }

    /**
     * 拥有数据权限 1 就放行
     *
     * @return
     */
    @Insp("hasData('1')")
    @RequestMapping("/listUser3")
    public String listUser3() {
        return "ok3";
    }

    /**
     * 拥有功能权限：user:list 或者 user:get  2个其中一个拥有就放行
     *
     * @param a
     * @return
     */
    @Insp(value = "hasAnyFunc('user:list','user:get')")
    @RequestMapping("/listUser4")
    public String listUser4(String a) {
        return "ok4";
    }

    /**
     * 拥有功能权限：user:list 和 user:get  2个同时拥有才放行
     *
     * @param a
     * @return
     */
    @Insp(value = "hasFunc('user:list') && hasFunc('user:get')")
    @RequestMapping("/listUser5")
    public String listUser5(String a) {
        return "ok5";
    }

    /**
     * 拥有功能权限：user:list 和 user:get  2个同时拥有 并且要拥有 参数shopId的数据权限才放行
     *
     * @param shopId
     * @return
     */
    @Insp(value = "hasFunc('user:list') && hasFunc('user:get') && hasData(#shopId)")
    @RequestMapping("/listUser6")
    public String listUser6(Long shopId) {
        return "ok6";
    }
}
