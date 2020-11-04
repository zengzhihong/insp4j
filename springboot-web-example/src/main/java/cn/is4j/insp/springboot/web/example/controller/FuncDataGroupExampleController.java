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
import cn.is4j.insp.springboot.web.example.ExampleGroupConst;
import lombok.Data;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zengzhihong
 */
@RestController
@RequestMapping("/group")
public class FuncDataGroupExampleController {

	/**
	 * 拥有功能权限：user:list 就放行
	 *
	 * @param a
	 * @return
	 */
	@Insp(value = "hasFunc('shop:list')", groupName = ExampleGroupConst.GROUP_SHOP)
	@RequestMapping("/listShop")
	public String listShop(String a) {
		return "ok1";
	}

	/**
	 * 拥有功能权限：user:list 并且 拥有数据权限 shopId是动态传的 也可以写死 如：@Insp("hasFuncData('user:list',1)")
	 *
	 * @param shopId
	 * @return
	 */
	@Insp(value = "hasFuncData('enterprise:list',#shopId)", groupName = ExampleGroupConst.GROUP_ENTERPRISE)
	@RequestMapping("/listEnterprise")
	public String listEnterprise(String shopId) {
		return "ok2";
	}

	@Insp(value = "hasFuncData({'enterprise:list'},#param.shopId)", groupName = ExampleGroupConst.GROUP_ENTERPRISE)
	@RequestMapping("/postEnterprise")
	public String postEnterprise(@RequestBody PostEnterpriseParam param) {
		return "postEnterprise";
	}

	@Insp(value = "{#a,#a}")
	@RequestMapping("/a")
	public String a(Long a) {
		return "a";
	}

	@Data
	static class PostEnterpriseParam {
		private String shopId;
		private Integer optId;
	}

}
