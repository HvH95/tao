package com.taotao.manage.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.service.ItemCatService;

@Controller
@RequestMapping("api/item/cat")
public class ApiItemCatController {

	@Autowired
	private ItemCatService itemCatService;

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> queryItemCat(@RequestParam("callback") String callback) {
		try {
			ItemCatResult itemCatResult = this.itemCatService.queryAllToTree();
			String json = MAPPER.writeValueAsString(itemCatResult);
			if (StringUtils.isEmpty(callback)) {
				return ResponseEntity.ok(json);
			}
			return ResponseEntity.ok(callback + "(" + json + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

}
