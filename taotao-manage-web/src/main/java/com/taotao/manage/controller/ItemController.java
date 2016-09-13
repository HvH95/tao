package com.taotao.manage.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;

@Controller
@RequestMapping("item")
public class ItemController {
	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private ItemService itemService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc") String desc,
			@RequestParam("itemParams") String itemParams) {
		try {
			if (log.isInfoEnabled()) {
				log.info("新增商品,Item = {},desc={}", item, desc);
			}
			if (StringUtils.isEmpty(item.getTitle())) {
				// 响应400
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			this.itemService.saveItem(item, desc, itemParams);
			if (log.isInfoEnabled()) {
				log.info("新增商品成功,itemId={}", item.getId());
			}
			// 成功201
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("新增商品失败");
		}
		// 出错500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<EasyUIResult> queryItemList(//
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "30") Integer rows) {

		try {
			PageInfo<Item> pageInfo = this.itemService.queryPageList(page, rows);
			EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
			return ResponseEntity.ok(easyUIResult);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 出错500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> updateItem(Item item, @RequestParam("desc") String desc,
			@RequestParam("itemParams") String itemParams) {
		try {
			if (log.isInfoEnabled()) {
				log.info("修改商品,Item = {},desc={}", item, desc);
			}
			if (StringUtils.isEmpty(item.getTitle())) {
				// 响应400
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			// 修改商品
			this.itemService.updateItem(item, desc, itemParams);
			if (log.isInfoEnabled()) {
				log.info("修改商品成功,itemId={}", item.getId());
			}
			// 成功204
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改商品失败");
		}
		// 出错500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * 根据商品id查询商品数据
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "{itemId}", method = RequestMethod.GET)
	public ResponseEntity<Item> queryById(@PathVariable("itemId") Long itemId) {
		try {
			Item item = this.itemService.queryById(itemId);
			if (null == item) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 出错 500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

}
