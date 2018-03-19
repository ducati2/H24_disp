package com.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.SearcherInfo;
import com.github.abel533.echarts.Option;
import com.service.DataServiceImpl;


@Controller
public class HtmlController {
	@Resource
	private DataServiceImpl dataServiceImpl;
	
	
	
	@RequestMapping("/all")
    public String all(ModelMap map ,String StartDate, String EndDate){
		System.out.println(StartDate);
		System.out.println(EndDate);
		map.addAttribute("userList", dataServiceImpl.getSearcherInfo(StartDate, EndDate));
		map.addAttribute("StartDate",StartDate);
		map.addAttribute("EndDate",EndDate);
		return "dis_Data";
    }
	
	@RequestMapping("/test")
    public String test(ModelMap map ,String StartDate, String EndDate){
		System.out.println(StartDate);
		System.out.println(EndDate);
		map.addAttribute("userList", dataServiceImpl.getSearcherInfo(StartDate, EndDate));
		map.addAttribute("StartDate",StartDate);
		map.addAttribute("EndDate",EndDate);
		return "test";
    }
	
	
	@RequestMapping(value="getDataByConditions")
    public @ResponseBody Option[] getDataByConditions(String StartDate, String EndDate){ 
		return dataServiceImpl.getOptionsDataByConditions(StartDate,EndDate);
    }
}
