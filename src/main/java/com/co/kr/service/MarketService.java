package com.co.kr.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.co.kr.domain.MarketFileDomain;
import com.co.kr.domain.MarketListDomain;
import com.co.kr.vo.MarketListVO;

public interface MarketService {

	public int fileProcess(MarketListVO marketListVO, MultipartHttpServletRequest request, HttpServletRequest httpReq);
	
	public List<MarketListDomain> marketList();

	public void mkContentRemove(HashMap<String, Object> map);

	public void mkFileRemove(MarketFileDomain marketFileDomain);
	
	public MarketListDomain marketSelectOne(HashMap<String, Object> map);

	public List<MarketFileDomain> marketSelectOneFile(HashMap<String, Object> map);
	
}