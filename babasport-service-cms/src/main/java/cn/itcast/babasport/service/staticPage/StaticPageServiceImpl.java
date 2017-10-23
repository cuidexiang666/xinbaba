package cn.itcast.babasport.service.staticPage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class StaticPageServiceImpl implements StaticPageService,ServletContextAware {

	private Configuration configuration;
	private ServletContext servletContext; // 获取项目的真实路径
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		
	}
	
	/**
	 * 
	 * @Title: setFreeMarkerConfigurer
	 * @Description: 注入好处：指定模板的相对路径
	 * @param freeMarkerConfigurer
	 * @return void
	 * @throws
	 */
	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.configuration = freeMarkerConfigurer.getConfiguration();
	}


	/*
	 * (non-Javadoc)
	 * <p>Title: index</p> 
	 * <p>Description: 生成静态页的入口方法</p> 
	 * @param rootMap
	 * @param id 
	 * @see cn.itcast.babasport.service.staticpage.StaticPageService#index(java.util.Map, java.lang.String)
	 */
	@Override
	public void index(Map<String, Object> rootMap, String id) {
		try {
			// 1、创建Configuration指定模板位置
			// 2、通过Configuration获取模板
			String pathname = "/html/product/"+id+".html";
			String realPath = servletContext.getRealPath(pathname);
			File file = new File(realPath);
			File parentFile = file.getParentFile();
			if(!parentFile.exists()){
				parentFile.mkdirs(); // 创建多级目录
			}
			Template template = configuration.getTemplate("product.html");
			// 3、准备数据：谁调用谁传递
			// 4、模板+数据=输出
			template.process(rootMap, new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}