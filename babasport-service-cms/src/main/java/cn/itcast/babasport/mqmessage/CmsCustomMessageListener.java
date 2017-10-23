package cn.itcast.babasport.mqmessage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;

import cn.itcast.babasport.mapper.product.ColorMapper;
import cn.itcast.babasport.pojo.product.Color;
import cn.itcast.babasport.pojo.product.Product;
import cn.itcast.babasport.pojo.product.Sku;
import cn.itcast.babasport.service.cms.CmsService;
import cn.itcast.babasport.service.staticPage.StaticPageService;

public class CmsCustomMessageListener implements MessageListener {
	
	@Resource
	private CmsService cmsService;
	
	@Resource
	private StaticPageService staticPageService;

	@Override
	public void onMessage(Message message) {
		try {
			ActiveMQTextMessage activeMQTextMessage = (ActiveMQTextMessage) message;
			String id = activeMQTextMessage.getText();
			System.out.println("service-cms:"+id);
			// 准备数据:商品信息、库存信息、颜色结果集
			Map<String, Object> rootMap = new HashMap<>();
			Product product = cmsService.selectProductById(Long.parseLong(id));
			rootMap.put("product", product);
			List<Sku> skus = cmsService.selectSkusByProIdWithStorkMoreZero(Long.parseLong(id));
			rootMap.put("skus", skus); 
			Set<Color> colors = new HashSet<>();
			for (Sku sku : skus) {
				colors.add(sku.getColor());
			}
			rootMap.put("colors", colors);
			// 调用静态页服务
			staticPageService.index(rootMap, id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}