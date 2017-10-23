package cn.itcast.babasport.service.upload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.itcast.babasport.utils.contants.BbsContants;
import cn.itcast.babasport.utils.upload.FastDFSUtils;

@Service("uploadService")
public class UploadServiceImpl implements UploadService {

	@Override
	public String uploadPicToFastDFS(byte[] file_buff, String filename) throws Exception {

		String path = FastDFSUtils.UploadPicToFastDFS(file_buff, filename);
		return path;
	}

	
}
