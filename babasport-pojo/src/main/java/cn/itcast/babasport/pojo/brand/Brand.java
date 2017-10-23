package cn.itcast.babasport.pojo.brand;

import java.io.Serializable;

import cn.itcast.babasport.utils.contants.BbsContants;

/**
 * 
 * @ClassName: Brand
 * @Company: http://www.itcast.cn/
 * @Description: 品牌信息
 * @author JD 
 * @date 2016年3月31日 上午10:56:56
 */
@SuppressWarnings("serial")
public class Brand implements Serializable{

		private Long id; 			// 品牌ID  bigint
		private String name; 		// 品牌名称
		private String description; // 描述
		private String imgUrl; 		// 图片URL
		private Integer sort; 		// 排序  越大越靠前   
		private Integer isDisplay; 	// 是否可用   0 不可用 1 可用
		
		public String getAllUrl(){//完整的路径
			return BbsContants.IMG_URL + imgUrl;
		}
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getImgUrl() {
			return imgUrl;
		}
		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}
		public Integer getSort() {
			return sort;
		}
		public void setSort(Integer sort) {
			this.sort = sort;
		}
		public Integer getIsDisplay() {
			return isDisplay;
		}
		public void setIsDisplay(Integer isDisplay) {
			this.isDisplay = isDisplay;
		}
		
}
