package cn.itcast.babasport.utils.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class CustomConverter implements Converter<String, String>{

	@Override
	public String convert(String source) {
		if (StringUtils.isNotBlank(source)) {
			source = source.trim();
			if (!"".equals(source)) {
				return source;
			}
		}
		return null;
	}

}
