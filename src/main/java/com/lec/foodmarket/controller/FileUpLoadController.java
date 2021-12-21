package com.lec.foodmarket.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

@Controller
@RequestMapping("/common")
public class FileUpLoadController {

	@Value("${file.uploadpath}")
	private String uploadPath;

	// 파일 업로드
	@ResponseBody
	@PostMapping(value = "/ckUpload", produces = "application/json")
	public JsonObject ckUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile upload)
			throws Exception {
		UUID uid = UUID.randomUUID();
		
		OutputStream out = null;
		PrintWriter printWriter = null; 
		JsonObject json = new JsonObject();
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		try {
			String fileName = upload.getOriginalFilename(); 
			byte[] bytes = upload.getBytes(); 
			
			// 업로드 경로
			String fileUrl = "/ckUpload/productImages/detail/" + uid + "_" +fileName;
			String ckUploadPath = "C:\\spring_foodmarket\\ckupload\\productImages\\detail" + File.separator + uid + "_" + fileName;
			out = new FileOutputStream(new File(ckUploadPath));
			out.write(bytes);
			out.flush();
			
			json.addProperty("uploaded", 1);
			json.addProperty("fileName", uid + "_" + fileName);
			json.addProperty("url", fileUrl);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (printWriter != null) {
					printWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
}























