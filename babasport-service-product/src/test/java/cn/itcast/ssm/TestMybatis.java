package cn.itcast.ssm;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration("classpath:spring-config.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMybatis {
	
	/*@Resource
	private BbsTestMapper bbsTestMapper;
//	@Resource
//	private BbsTestService bbsTestService;
*/	
	@Autowired
	private SolrServer solrServer;
	
	/*@Test
	public void testSM(){
		BbsTest bbsTest = new BbsTest();
		bbsTest.setName("小强1");
		bbsTest.setAge(29);
//		bbsTestService.insertBbsTest(bbsTest);
	}*/
	
	@Test
	public void testSolr() throws SolrServerException, IOException{
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "2");
		document.addField("name_ik", "呵呵呵");
		solrServer.add(document);
		solrServer.commit();
	}
}
