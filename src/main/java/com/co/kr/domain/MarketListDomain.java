package com.co.kr.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName="builder")
public class MarketListDomain {

	private String mkSeq;
	private String mbId;
	private String mkTitle;
	private String mkContent;
	private String mkCreateAt;
	private String mkUpdateAt;

}