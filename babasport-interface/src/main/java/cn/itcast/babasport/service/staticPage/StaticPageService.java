package cn.itcast.babasport.service.staticPage;

import java.util.Map;

public interface StaticPageService {

	/**
	 * 生成静态页的入口方法
	 * @param rootMap  静态页需要展示的数据
	 * @param id 静态页面名称
	 */
	public void index(Map<String, Object> rootMap,String id);
}
