package cn.itcast.babasport.service.upload;

public interface UploadService {

	public String uploadPicToFastDFS(byte[] file_buff,String filename) throws Exception;
}
