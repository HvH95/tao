package com.taotao.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;

@Service
public class ItemCatService extends BaseService<ItemCat> {

	@Autowired
	private ItemCatMapper itemCatMapper;

	/**
	 * 根据父节点id查询商品类目列表
	 */
	public List<ItemCat> queryItemCatListByParentId(Long parentId) {
		ItemCat record = new ItemCat();
		record.setParentId(parentId);
		return itemCatMapper.select(record);
	}

	public ItemCatResult queryAllToTree() {
		return null;
	}

}
