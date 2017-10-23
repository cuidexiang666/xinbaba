package cn.itcast.babasport.pojo.brand;

import java.io.Serializable;

/**
 * 
 * @ClassName: Brand
 * @Company: http://www.itcast.cn/
 * @Description: 品牌信息
 * @author JD 
 * @date 2016年3月31日 上午10:56:56
 */
@SuppressWarnings("serial")
public class BrandQuery implements Serializable{

		private String name; 		// 品牌名称
		private Integer isDisplay; 	// 是否可用   0 不可用 1 可用
		
		private Integer startRow; //起始行
		private Integer pageSize = 3;	//每页显示的
		private Integer pageNo = 1;		//当前页码
		
		
		public Integer getStartRow() {
			return startRow;
		}
		public void setStartRow(Integer startRow) {
			this.startRow = (pageNo-1)*pageSize;
		}
		public Integer getPageSize() {
			return pageSize;
		}
		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}
		public Integer getPageNo() {
			return pageNo;
		}
		public void setPageNo(Integer pageNo) {
			this.pageNo = pageNo;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getIsDisplay() {
			return isDisplay;
		}
		public void setIsDisplay(Integer isDisplay) {
			this.isDisplay = isDisplay;
		}
		
		
		
}
