package com.lec.foodmarket.commond;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.lec.foodmarket.domain.dto.FileUploadDTO;

@Component
public class FileUpload {
	public FileUploadDTO ckUpload(String url, String realPath, MultipartFile upload) throws Exception {
		FileUploadDTO dto = null;
		UUID uid = UUID.randomUUID(); // 고유 식별값
		OutputStream out = null; // 파일 쓰기

		try {
			String orginName = upload.getOriginalFilename();
			String saveName = uid + "_" + orginName;
			byte[] bytes = upload.getBytes();

			String fileUrl = "/ckUpload" + url + saveName;
			String ckUploadPath = "D:\\spring_foodmarket\\ckupload\\"+ realPath + File.separator + saveName;

			dto = FileUploadDTO.builder()
					.orginName(orginName)
					.saveName(saveName)
					.url(fileUrl)
					.build();

			out = new FileOutputStream(new File(ckUploadPath));
			out.write(bytes);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return dto;
	}
}
