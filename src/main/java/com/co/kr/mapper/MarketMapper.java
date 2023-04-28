package com.co.kr.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.domain.MarketContentDomain;
import com.co.kr.domain.MarketFileDomain;
import com.co.kr.domain.MarketListDomain;

@Mapper
public interface MarketMapper {

	//list
	public List<MarketListDomain> marketList();
	
	//content insert
	public void contentUpload(MarketContentDomain marketContentDomain);
	//file insert
	public void fileUpload(MarketFileDomain marketFileDomain);

	//content update
	public void mkContentUpdate(MarketContentDomain marketContentDomain);
	//file update
	public void mkFileUpdate(MarketFileDomain marketFileDomain);

  //content delete 
	public void mkContentRemove(HashMap<String, Object> map);
	//file delete 
	public void mkFileRemove(MarketFileDomain marketFileDomain);
	
	//select one
	public MarketListDomain marketSelectOne(HashMap<String, Object> map);

	//select one file
	public List<MarketFileDomain> marketSelectOneFile(HashMap<String, Object> map);
	
}