package com.lec.foodmarket.commond;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lec.foodmarket.domain.dto.FileUploadDTO;

@Component
public class FileUpload {
	UUID uid = UUID.randomUUID();

	// 단일 파일 업로드
	public FileUploadDTO ckUpload(String url, String realPath, MultipartFile upload) throws Exception {
		FileUploadDTO dto = null;
		uid = UUID.randomUUID(); // 고유 식별값
		OutputStream out = null; // 파일 쓰기

		try {
			String orginName = upload.getOriginalFilename();
			String saveName = uid + "_" + orginName;
			byte[] bytes = upload.getBytes();

			String fileUrl = "/ckUpload" + url + saveName;
			String ckUploadPath = "C:\\spring_foodmarket\\ckupload\\" + realPath + File.separator + saveName;

			dto = FileUploadDTO.builder().orginName(orginName).saveName(saveName).url(fileUrl).build();

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

	// 다중 파일 업로드
	public List<FileUploadDTO> ckAllUpload(String url, String realPath, MultipartHttpServletRequest upload)
			throws Exception {
		List<FileUploadDTO> list = new ArrayList<FileUploadDTO>();
		List<MultipartFile> fileList = upload.getFiles("multImage");
		FileUploadDTO dto = null;
		uid = UUID.randomUUID(); // 고유 식별값
		OutputStream out = null; // 파일 쓰기

		try {
			for (MultipartFile file : fileList) {
				String orginName = file.getOriginalFilename();
				String saveName = uid + "_" + orginName;
				byte[] bytes = file.getBytes();

				String fileUrl = "/ckUpload" + url + saveName;
				String ckUploadPath = "C:\\spring_foodmarket\\ckupload\\" + realPath + File.separator + saveName;

				dto = FileUploadDTO.builder().orginName(orginName).saveName(saveName).url(fileUrl).build();
				out = new FileOutputStream(new File(ckUploadPath));
				out.write(bytes);
				out.flush();
				list.add(dto);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return list;
	}
}
