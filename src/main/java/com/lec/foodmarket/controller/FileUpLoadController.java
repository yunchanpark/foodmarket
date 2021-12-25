package com.lec.foodmarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.lec.foodmarket.commond.FileUpload;
import com.lec.foodmarket.domain.dto.FileUploadDTO;

@Controller
@RequestMapping("/common")
public class FileUpLoadController {

	// 파일 업로드(상세설명)
	@ResponseBody
	@PostMapping(value = "/ckUpload", produces = "application/json")
	public JsonObject ckUpload(@RequestParam MultipartFile upload) throws Exception {
		FileUpload file = new FileUpload();
		FileUploadDTO dto = file.ckUpload("/productImages/detail/", "productImages\\detail", upload);

		JsonObject json = new JsonObject();

		json.addProperty("uploaded", 1);
		json.addProperty("fileName", dto.getOrginName() );
		json.addProperty("url", dto.getUrl());
		return json;
	}
}
