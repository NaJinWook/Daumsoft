package com.daumsoft.test3.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.daumsoft.test3.model.dao.ViewDAO;
import com.daumsoft.test3.model.dto.ViewDTO;

@Service
public class ViewServiceImpl implements ViewService {
	@Inject
	ViewDAO viewDao;

	@Override
	public void insert(ViewDTO dto) throws Exception {
		viewDao.insert(dto);
	}

	@Override
	public void delete() throws Exception {
		viewDao.delete();
	}

	@Override
	public Map<String, Object> select() throws Exception {
	      List<ViewDTO> list = viewDao.select();
	      Map<String, Object> resMap = new HashMap<String, Object>();
	      JSONArray jsonArray = null;
	      JSONObject jsonObject = null;
	      
	      int positive_sum = 0;
	      int negative_sum = 0;
	      int neutral_sum = 0;
	      jsonArray = new JSONArray();
	      
	      for(ViewDTO dto : list) {
	    	  jsonObject = new JSONObject();
	    	  jsonObject.put("register_date", dto.getRegister_date());
	    	  jsonObject.put("positive_count", dto.getPositive_count());
	    	  jsonObject.put("negative_count", dto.getNegative_count());
	    	  jsonObject.put("neutral_count", dto.getNeutral_count());
	    	  jsonArray.add(jsonObject);
	    	  
	    	  positive_sum += dto.getPositive_count();
	    	  negative_sum += dto.getNegative_count();
	    	  neutral_sum += dto.getNeutral_count();
	      }
	      
	      resMap.put("jsonArray",jsonArray);
	      resMap.put("positive_sum",positive_sum);
	      resMap.put("negative_sum",negative_sum);
	      resMap.put("neutral_sum",neutral_sum);
	      
	      return resMap;
	      
//	      jsonStr += "[";
//	      for(int i=0; i<list.size(); i++) {
//	    	  jsonStr += "{\"register_date\":\"" + list.get(i).getRegister_date() + "\", \"positive_count\":" + list.get(i).getPositive_count() + ", \"negative_count\":" + list.get(i).getNegative_count()  + ", \"neutral_count\":" + list.get(i).getNeutral_count() + "}";
//	         if(i != list.size()-1) {
//	        	 jsonStr += ",";
//	         }
//	      }
//	      jsonStr += "]";
	}
}
