package com.lec.foodmarket.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
	private String update_id;
	private String update_pw;
	private String update_name;
	private String update_phoneNo;
	private String update_email;
	private String update_addr;
	private String update_addr_detail;
}