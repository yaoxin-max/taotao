package com.taotao.manager.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.manager.pojo.Item;
import com.taotao.manager.pojo.ItemDesc;
import com.taotao.manager.service.ItemDescService;
import com.taotao.manager.service.ItemService;

@Controller
@RequestMapping("item")
public class ItemController {
	public static Logger logger = LoggerFactory.getLogger(ItemController.class);
	// error - warn - info - debug  级别依次递减
	@Autowired
	private ItemService itemService;
	
	/**
	 * 保存商品
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item item,@RequestParam("desc")String desc){
		try {
			logger.info("接受的参数信息 item = {}, desc = {}",item,desc);
			//item的标题是不是为空
			if(StringUtils.isEmpty(item.getTitle())){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
			boolean result = itemService.saveItem(item,desc);
			if(!result){
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
			logger.debug("保存商品成功 ,商品id为 {}",item.getId());
			return  ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("服务器出错 item="+item, e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 分页查询商品
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<EasyUIResult> queryItemList(@RequestParam(value="page",required=false,defaultValue="1")int pageNo,
			@RequestParam(value = "rows",required =false,defaultValue = "30")int pageSize){
		try {
			EasyUIResult result = itemService.queryItemList(pageNo,pageSize);
			if(result == null || result.getRows() == null || result.getRows().size() == 0 )
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	/**
	 * 保存编辑商品
	 * @param item
	 * @param desc
	 * @param itemParams
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> updateItem(Item item,@RequestParam("desc")String desc,@RequestParam("itemParams")String itemParams){
		try {
			logger.info("接受的参数信息 item = {}, desc = {}",item,desc);
			if(StringUtils.isEmpty(item.getTitle())){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
			boolean result = itemService.updateItem(item,desc,itemParams);
			if(!result){
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
			logger.debug("编辑商品成功 ,商品id为 {}",item.getId());
			return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("服务器出错 item="+item, e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}
