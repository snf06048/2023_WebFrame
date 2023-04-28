package com.co.kr.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName="builder")
public class MarketFileDomain {

	
	private Integer mkSeq;
	private String mbId;
	
	private String upOriginalFileName;
	private String upNewFileName; //동일 이름 업로드 방지
	private String upFilePath;
	private Integer upFileSize;
	
}