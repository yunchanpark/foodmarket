package com.lec.foodmarket.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lec.foodmarket.domain.ProductCategory;
import com.lec.foodmarket.domain.dto.FileUploadDTO;
import com.lec.foodmarket.domain.dto.ProductDTO;

@Service
public class ExcelService {

	ProductService productService;

	@Autowired
	public ExcelService(ProductService productService) {
		this.productService = productService;
	}

	// 엑셀 양식 생성 후 다운로드
	public void downloadProductInfo(HttpServletResponse response, List<FileUploadDTO> list) throws IOException {
		// 엑셀 파일 생성
		Workbook workbook = new SXSSFWorkbook();
		// 엑셀 파일 내부에 Sheet를 하나 생성
		Sheet sheet = workbook.createSheet();
		

		Font font = workbook.createFont();
		font.setColor(IndexedColors.WHITE.getIndex());
		
		// 테이블 헤더용 스타일
		CellStyle headStyle = workbook.createCellStyle();
		headStyle.setWrapText(true);
		headStyle.setAlignment(HorizontalAlignment.CENTER);
		headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headStyle.setBorderBottom(BorderStyle.MEDIUM);
		headStyle.setBorderTop(BorderStyle.MEDIUM);
		headStyle.setBorderLeft(BorderStyle.MEDIUM);
		headStyle.setBorderRight(BorderStyle.MEDIUM);
		headStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		headStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		headStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		
		CellStyle headStyle1 = workbook.createCellStyle();
		headStyle1.setWrapText(true);
		headStyle1.setAlignment(HorizontalAlignment.CENTER);
		headStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headStyle1.setFont(font);
		headStyle1.setFillForegroundColor(IndexedColors.RED.getIndex());  
		headStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headStyle1.setBorderBottom(BorderStyle.MEDIUM);
		headStyle1.setBorderTop(BorderStyle.MEDIUM);
		headStyle1.setBorderLeft(BorderStyle.MEDIUM);
		headStyle1.setBorderRight(BorderStyle.MEDIUM);
		headStyle1.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		headStyle1.setTopBorderColor(IndexedColors.BLACK.getIndex());
		headStyle1.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headStyle1.setRightBorderColor(IndexedColors.BLACK.getIndex());

		// 헤더를 생성합니다
		int rowIndex = 0;
		Row headerRow = sheet.createRow(rowIndex++);
		Cell headerCell1 = headerRow.createCell(0);
		headerCell1.setCellValue("*필수\n카테고리\n(카테고리는 숫자로 또는 카테고리이름으로 입력해주세요)");
		headerCell1.setCellStyle(headStyle1);

		Cell headerCell2 = headerRow.createCell(1);
		headerCell2.setCellValue("*필수\n상품명");
		headerCell2.setCellStyle(headStyle1);

		Cell headerCell3 = headerRow.createCell(2);
		headerCell3.setCellValue("추가상품명");
		headerCell3.setCellStyle(headStyle);
		
		Cell headerCell4 = headerRow.createCell(3);
		headerCell4.setCellValue("*필수\n판매가격\n(판매가는 숫자로 입력하고 매입가보다 작을 수 없고 0이하 일 수 없습니다)");
		headerCell4.setCellStyle(headStyle1);

		Cell headerCell5 = headerRow.createCell(4);
		headerCell5.setCellValue("*필수\n매입가");
		headerCell5.setCellStyle(headStyle1);

		Cell headerCell6 = headerRow.createCell(5);
		headerCell6.setCellValue("*필수\n재고\n(재고는 0이하 일 수 없습니다)");
		headerCell6.setCellStyle(headStyle1);

		Cell headerCell7 = headerRow.createCell(6);
		headerCell7.setCellValue("할인유무\n(\"Y\" 혹은 \"N\"로 입력해주세요)");
		headerCell7.setCellStyle(headStyle);

		Cell headerCell8 = headerRow.createCell(7);
		headerCell8.setCellValue("즉시할인\n(할인 유무를 \"Y\"로 입력하신 경우 필수 입니다.\nex) %인 경우 0~100, 원인 경우 0~판매가)");
		headerCell8.setCellStyle(headStyle);

		Cell headerCell9 = headerRow.createCell(8);
		headerCell9.setCellValue("할인방법 \n(할인 유무를 \"Y\"로 입력하신 경우 필수 입니다.\nex)\"%\"와 \"원\"으로 입력해주세요.)");
		headerCell9.setCellStyle(headStyle);

		Cell headerCell10 = headerRow.createCell(9);
		headerCell10.setCellValue("기간할인설정 유무\n(\"Y\" 혹은 \"N\"로 입력해주세요)");
		headerCell10.setCellStyle(headStyle);

		Cell headerCell11 = headerRow.createCell(10);
		headerCell11.setCellValue("할인 시작하는 시간\n(기산할인설정 유무를 \"Y\"로 입력하신 경우 필수 입니다.)\nex)\"yyyy-MM-dd HH:mm 형식으로 입력해주세요.");
		headerCell11.setCellStyle(headStyle);

		Cell headerCell12 = headerRow.createCell(11);
		headerCell12.setCellValue("할인 끝나는 시간\n(기산할인설정 유무를 \"Y\"로 입력하신 경우 필수 입니다.)\nex)\"yyyy-MM-dd HH:mm 형식으로 입력해주세요");
		headerCell12.setCellStyle(headStyle);

		Cell headerCell13 = headerRow.createCell(12);
		headerCell13.setCellValue("*필수\n원본 이미지 이름");
		headerCell13.setCellStyle(headStyle1);

		Cell headerCell14 = headerRow.createCell(13);
		headerCell14.setCellValue("*필수\n서버에 저장된 이미지 이름");
		headerCell14.setCellStyle(headStyle1);

		Cell headerCell15 = headerRow.createCell(14);
		headerCell15.setCellValue("상세설명");
		headerCell15.setCellStyle(headStyle);

		// 바디에 데이터를 넣어줍니다
		for (FileUploadDTO dto : list) {
			Row bodyRow = sheet.createRow(rowIndex++);

			Cell bodyCell1 = bodyRow.createCell(0);
			bodyCell1.setCellValue("");

			Cell bodyCell2 = bodyRow.createCell(1);
			bodyCell2.setCellValue("");

			Cell bodyCell3 = bodyRow.createCell(2);
			bodyCell3.setCellValue("");

			Cell bodyCell4 = bodyRow.createCell(3);
			bodyCell4.setCellValue("");

			Cell bodyCell5 = bodyRow.createCell(4);
			bodyCell5.setCellValue("");

			Cell bodyCell6 = bodyRow.createCell(5);
			bodyCell6.setCellValue("");

			Cell bodyCell7 = bodyRow.createCell(6);
			bodyCell7.setCellValue("");

			Cell bodyCell8 = bodyRow.createCell(7);
			bodyCell8.setCellValue("");

			Cell bodyCell9 = bodyRow.createCell(8);
			bodyCell9.setCellValue("");

			Cell bodyCell10 = bodyRow.createCell(9);
			bodyCell10.setCellValue("");

			Cell bodyCell11 = bodyRow.createCell(10);
			bodyCell11.setCellValue("");

			Cell bodyCell12 = bodyRow.createCell(11);
			bodyCell12.setCellValue("");

			Cell bodyCell13 = bodyRow.createCell(12);
			bodyCell13.setCellValue(dto.getOrginName());

			Cell bodyCell14 = bodyRow.createCell(13);
			bodyCell14.setCellValue(dto.getSaveName());

			Cell bodyCell15 = bodyRow.createCell(14);
			bodyCell15.setCellValue("");
		}

		sheet.setColumnWidth(0, sheet.getColumnWidth(0) + 6000);
		sheet.setColumnWidth(1, sheet.getColumnWidth(0) + 2000);
		sheet.setColumnWidth(2, sheet.getColumnWidth(0) + 2000);
		sheet.setColumnWidth(3, sheet.getColumnWidth(0) + 2000);
		sheet.setColumnWidth(4, sheet.getColumnWidth(0) + 2000);
		sheet.setColumnWidth(5, sheet.getColumnWidth(0) + 2000);
		sheet.setColumnWidth(6, sheet.getColumnWidth(0) + 2000);
		sheet.setColumnWidth(7, sheet.getColumnWidth(0) + 2000);
		sheet.setColumnWidth(8, sheet.getColumnWidth(0) + 2000);
		sheet.setColumnWidth(9, sheet.getColumnWidth(0) + 2000);
		sheet.setColumnWidth(10, sheet.getColumnWidth(0) + 6000);
		sheet.setColumnWidth(11, sheet.getColumnWidth(0) + 6000);
		sheet.setColumnWidth(12, sheet.getColumnWidth(0) + 1000);
		sheet.setColumnWidth(13, sheet.getColumnWidth(0) + 6000);
		sheet.setColumnWidth(14, sheet.getColumnWidth(0) + 6000);

		// 컨텐츠 타입과 파일명 지정
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=example.xlsx");

		workbook.write(response.getOutputStream());
		workbook.close();
	}

	// 엑셀 읽기
	public Map<ProductDTO, Integer> uploadExcelFile(MultipartFile excelFile) throws Exception {
		List<ProductDTO> list = new ArrayList<ProductDTO>();

		OPCPackage opcPackage = OPCPackage.open(excelFile.getInputStream());
		XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);

		// 첫번째 시트 불러오기
		XSSFSheet sheet = workbook.getSheetAt(0);

		String regex = "\\d";
		Pattern pat = Pattern.compile(regex);
		Matcher matcher;

		for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
			ProductDTO product = new ProductDTO();
			XSSFRow row = sheet.getRow(i);

			// 행이 존재하기 않으면 패스
			if (null != row) {
				// 셀의 수
				int cells = row.getPhysicalNumberOfCells();
				for (int j = 0; j <= cells; j++) {
					// 셀값 가져오기
					XSSFCell cell = row.getCell(j);
					if (cell == null)
						continue;
					else {
						// 타입별로 내용 읽기
						switch (cell.getCellType()) {
						case NUMERIC:
							if (j == 0) {
								ProductCategory category = productService
										.productCategoryById((long) cell.getNumericCellValue());
								if (category != null)
									product.setCategoryNo(category);
							} else if (j == 1) {
								product.setName(Integer.toString((int) cell.getNumericCellValue()));
							} else if (j == 2) {
								product.setDescription(Integer.toString((int) cell.getNumericCellValue()));
							} else if (j == 3) {
								product.setPrice((int) cell.getNumericCellValue());
							} else if (j == 4) {
								product.setPurchasePrice((int) cell.getNumericCellValue());
							} else if (j == 5) {
								product.setStock((int) cell.getNumericCellValue());
							} else if (j == 7) {
								product.setDiscount((int) cell.getNumericCellValue());
							} else if (j == 10 || j == 11) {
								if (DateUtil.isCellDateFormatted(cell)) {
									if (j == 10) 
										product.setStartDate(cell.getLocalDateTimeCellValue());
									else if (j == 11)
										product.setEndDate(cell.getLocalDateTimeCellValue());
								}
							}
							break;
						case STRING:
							if (j == 0) {
								ProductCategory category = productService
										.productCategoryByName(cell.getStringCellValue());
								if (category != null)
									product.setCategoryNo(category);
							} else if (j == 1) {
								product.setName(cell.getStringCellValue());
							} else if (j == 2) {
								product.setDescription(cell.getStringCellValue());
							} else if (j == 3) {
								matcher = pat.matcher(cell.getStringCellValue());
								if (matcher.find()) {
									product.setPrice(Integer.parseInt(cell.getStringCellValue()));
								}
							} else if (j == 4) {
								matcher = pat.matcher(cell.getStringCellValue());
								if (matcher.find()) {
									product.setPurchasePrice(Integer.parseInt(cell.getStringCellValue()));
								}
							} else if (j == 5) {
								matcher = pat.matcher(cell.getStringCellValue());
								if (matcher.find()) {
									product.setStock(Integer.parseInt(cell.getStringCellValue()));
								}
							} else if (j == 6) {
								if (cell.getStringCellValue().equals("Y") || cell.getStringCellValue().equals("N")) {
									product.setDiscountCk(cell.getStringCellValue());
								}
							} else if (j == 7) {
								matcher = pat.matcher(cell.getStringCellValue());
								if (matcher.find()) {
									product.setDiscount(Integer.parseInt(cell.getStringCellValue()));
								}
							} else if (j == 8) {
								if (cell.getStringCellValue().equals("%")) {
									product.setExchangeRate("percent");
								} else if (cell.getStringCellValue().equals("원")) {
									product.setExchangeRate("won");
								}
							} else if (j == 9) {
								if (cell.getStringCellValue().equals("Y") || cell.getStringCellValue().equals("N")) {
									product.setDuringCheck(cell.getStringCellValue());
								}
							} else if (j == 12) {
								product.setOrginName(cell.getStringCellValue());
							} else if (j == 13) {
								product.setSaveName(cell.getStringCellValue());
							} else if (j == 14) {
								product.setDetailContent(cell.getStringCellValue());
							}
							break;
						}
					}
				}
				list.add(product);
			}
		}
		return excelValidator(list);
	}

	public Map<ProductDTO, Integer> excelValidator(List<ProductDTO> list) {
		Map<ProductDTO, Integer> map = new HashMap<ProductDTO, Integer>();
		for (ProductDTO dto : list) {
			ProductCategory categoryNo = dto.getCategoryNo(); // 카테고리 번호
			String name = dto.getName(); // 상품 이름
			Integer price = dto.getPrice(); // 상품가격
			Integer purchasePrice = dto.getPurchasePrice(); // 상품 매입가
			Integer stock = dto.getStock();// 상품 재고
			String discountCk = dto.getDiscountCk(); // 상품 할인 체크
			Integer discount = dto.getDiscount(); // 상품 할인
			String exchangeRate = dto.getExchangeRate(); // 상품 할인 구분(ex. %, 원)
			String duringCheck = dto.getDuringCheck(); // 상품 기간 할인 설정
			LocalDateTime startDate = dto.getStartDate(); // 상품 할인 시작 날짜
			LocalDateTime endDate = dto.getEndDate(); // 상품 할인 끝 날짜
			
			int cnt = 0;

			if (categoryNo == null || name == null || price == null || purchasePrice == null
					|| dto.getSaveName() == null || dto.getOrginName() == null || stock == null) {
				cnt = 1;
			} else {
				
				// 가격
				if (price < purchasePrice || price <= 0 || purchasePrice <= 0) {
					cnt = 1;
				} 
				
				// 할인
				if (discountCk != null && discountCk.equals("Y")) {
					if (discount == null) {
						cnt = 1;
					} else {
						if (discount <= 0) {
							cnt = 1;
						} else if (exchangeRate != null) { 
							if (exchangeRate.equals("percent")) {
								if (discount > 100)
									cnt = 1;
							} else if (exchangeRate.equals("won")) {
								if (discount >= price)
									cnt = 1;
							}
						}
					}
				}
				
				// 상품 기간
				if (duringCheck != null && duringCheck.equals("Y")) { 
					if (startDate == null || endDate == null) {
						cnt = 1;
					} else if (startDate != null && endDate != null) {
						LocalDateTime now = LocalDateTime.now();
						if (!(now.isBefore(startDate))) {
							cnt = 1;
						} else if (!(startDate.isBefore(endDate))) {
							cnt = 1;
						}
					}
				} 
				
				// 재고
				if (stock <= 0) { 
					cnt = 1;
				}
			}
			map.put(dto, cnt);
		}
		return map;
	}
}
