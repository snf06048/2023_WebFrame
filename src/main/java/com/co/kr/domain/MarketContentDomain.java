package com.co.kr.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName="builder")
public class MarketContentDomain {

	private Integer mkSeq;
	private String mbId;

	private String mkTitle;
	private String mkContent;

}