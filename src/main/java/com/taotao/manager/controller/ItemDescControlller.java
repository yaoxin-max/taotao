package com.taotao.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.manager.pojo.ItemDesc;
import com.taotao.manager.service.ItemDescService;

@Controller
@RequestMapping("item/desc")
public class ItemDescControlller {
	@Autowired
	private ItemDescService itemDescService;
	@RequestMapping(value="{itemId}",method=RequestMethod.GET)
	public ResponseEntity<ItemDesc> queryItemDescByItemId(@PathVariable("itemId")Long itemId){
		try {
			ItemDesc desc = itemDescService.queryById(itemId);
			if(null == desc){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			/*return ResponseEntity.ok(desc);*/
			return ResponseEntity.status(HttpStatus.OK).body(desc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		
	}
	
}
