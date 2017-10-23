package cn.itcast.babasport.service.test;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.babasport.mapper.test.BbsTestMapper;
import cn.itcast.babasport.pojo.test.BbsTest;

@Service("bbsTestService")
public class BbsTestServiceImpl implements BbsTestService {

	@Resource
	private BbsTestMapper bbsTestMapper;
	
	@Transactional
	@Override
	public void insertBbsTest(BbsTest bbsTest) {

		bbsTestMapper.insertBbsTest(bbsTest);
//		System.out.println(9/0);
	}

	@Override
	public List<BbsTest> queryList() {
		return bbsTestMapper.queryList();
	}

}
