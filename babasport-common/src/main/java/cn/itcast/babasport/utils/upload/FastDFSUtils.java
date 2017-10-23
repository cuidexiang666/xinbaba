package cn.itcast.babasport.utils.upload;


import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

/**
 * 文件上传fastdfs工具类
 * @author Administrator
 *
 */
public class FastDFSUtils {

	public static String UploadPicToFastDFS(byte[] file_buff,String filename) throws Exception{
		
		//加载fastdfs配置文件
		ClassPathResource resource = new ClassPathResource("fdfs_client.conf");
		
		//初始化fastDFS配置文件
		ClientGlobal.init(resource.getClassLoader().getResource("fdfs_client.conf").getPath());
		
		//创建跟踪服务器客户端
		TrackerClient trackerClient = new TrackerClient();
		
		//通过跟踪服务器客户端获取服务器端
		TrackerServer trackerServer = trackerClient.getConnection();
		
		//获取存储服务器客户端
		StorageClient1 storageClient1 = new StorageClient1(trackerServer, null);
		
		//将附件上传到服务器客户端
		String file_ext_name = FilenameUtils.getExtension(filename);
		String path = storageClient1.upload_file1(file_buff, file_ext_name, null);
		
		return path;
	}
}
