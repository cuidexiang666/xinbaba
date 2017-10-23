package cn.itcast.babasport.utils.json;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	public static String parseObjectToJson(Object object){
		
		ObjectMapper oMapper = new ObjectMapper();
		oMapper.setSerializationInclusion(Include.NON_NULL);
		StringWriter w = new StringWriter();
		try {
			oMapper.writeValue(w, object);
			return w.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} 	
		return null;
	}
}
