package cn.itcast.babasport.controller.upload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import cn.itcast.babasport.service.upload.UploadService;
import cn.itcast.babasport.utils.contants.BbsContants;
import cn.itcast.babasport.utils.upload.FastDFSUtils;

@Controller
@RequestMapping("upload")
public class UploadController {

	@Resource
	private UploadService uploadService;
	
	
	/*@RequestMapping("uploadPic.do")
	public void uploadPic(MultipartFile pic,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		if (pic!=null &&pic.getSize()>0) {
			//修改附件名称
			String filename = pic.getOriginalFilename();
			String extension = FilenameUtils.getExtension(filename);//附件扩展名  jpg
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			String newName = uuid+"."+extension;
			
			//附件上传
			String realPath = request.getServletContext().getRealPath("");//真实路径
			String filePath = "\\upload\\"+newName;
			String path = realPath+filePath;
			File file = new File(path);
			pic.transferTo(file);
			
			//附件回显
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("allUrl", filePath);//图片回显
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(jsonObject.toString());
			
			
		}
	}*/
	@RequestMapping("uploadPic.do")
	public void uploadPic(MultipartFile pic,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		if (pic!=null &&pic.getSize()>0) {
			
			String path = uploadService.uploadPicToFastDFS(pic.getBytes(), pic.getOriginalFilename());
			String allUrl = BbsContants.IMG_URL+path;//完整路径
			
			//附件回显
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("allUrl", allUrl);//图片回显
			jsonObject.put("imgUrl", path);// 图片URL保存到数据库
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(jsonObject.toString());
		}
	}
	
	@RequestMapping("uploadPics.do")
	@ResponseBody
	public List<String> uploadPics(@RequestParam MultipartFile[] pics) throws IOException, Exception{
		//将附件上传到fastdfs上
		List<String> urls = new ArrayList<>();
		for (MultipartFile pic : pics) {
			String path = uploadService.uploadPicToFastDFS(pic.getBytes(), pic.getOriginalFilename());
			String url = BbsContants.IMG_URL + path;
			urls.add(url);
		}
		return urls;
	}
	
	@RequestMapping("uploadFck.do")
	public void uploadFck(HttpServletRequest request,HttpServletResponse response) throws IOException, Exception{
		
		//通过request接收附件在springmvc中是万能的做法
		//通过request取出附件信息
		MultipartRequest multipartRequest = (MultipartRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		Set<Entry<String,MultipartFile>> entrySet = fileMap.entrySet();
		for (Entry<String, MultipartFile> entry : entrySet) {
			MultipartFile pic = entry.getValue();
			String path = uploadService.uploadPicToFastDFS(pic.getBytes(), pic.getOriginalFilename());
			String url = BbsContants.IMG_URL + path;
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("url", url);//图片回显    key必须是URL
			jsonObject.put("error", 0);//是否上传成功
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(jsonObject.toString());
		}
	}
	
}










