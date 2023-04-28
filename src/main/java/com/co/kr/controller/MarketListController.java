package com.co.kr.controller;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.code.Code;
import com.co.kr.domain.MarketFileDomain;
import com.co.kr.domain.MarketListDomain;
import com.co.kr.exception.RequestException;
import com.co.kr.service.MarketService;
import com.co.kr.vo.MarketListVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MarketListController {
	@Autowired
	private MarketService marketService;

	
	@PostMapping(value = "mkupload")
	public ModelAndView mkUpload(MarketListVO marketListVO, MultipartHttpServletRequest request, HttpServletRequest httpReq) throws IOException, ParseException {
		
		ModelAndView mav = new ModelAndView();
		int mkSeq = marketService.fileProcess(marketListVO, request, httpReq);
		marketListVO.setContent(""); 
		marketListVO.setTitle(""); 
		
		
		mav = mkSelectOneCall(marketListVO, String.valueOf(mkSeq),request);
		mav.setViewName("market/marketList.html");
		return mav;
		
	}
	
	//detail
	@GetMapping("mkdetail")
    public ModelAndView mkDetail(@ModelAttribute("marketListVO") MarketListVO marketListVO, @RequestParam("mkSeq") String mkSeq, HttpServletRequest request) throws IOException {
		ModelAndView mav = new ModelAndView();
		//하나파일 가져오기
		mav = mkSelectOneCall(marketListVO, mkSeq,request);
		mav.setViewName("market/marketList.html");
		return mav;
	}
	
	@GetMapping("mkedit")
		public ModelAndView mkedit(MarketListVO marketListVO, @RequestParam("mkSeq") String mkSeq, HttpServletRequest request) throws IOException{
	
			ModelAndView mav = new ModelAndView();
	
			HashMap<String, Object> map = new HashMap<String, Object>();
			HttpSession session = request.getSession();
			
			map.put("mkSeq", Integer.parseInt(mkSeq));
			MarketListDomain marketListDomain = marketService.marketSelectOne(map);
			List<MarketFileDomain> marketList = marketService.marketSelectOneFile(map);
			
			for(MarketFileDomain list : marketList) {
				String path = list.getUpFilePath().replaceAll("\\\\", "/");
				list.setUpFilePath(path);
			}
			
			marketListVO.setSeq(marketListDomain.getMkSeq());
			marketListVO.setContent(marketListDomain.getMkContent());
			marketListVO.setTitle(marketListDomain.getMkTitle());
			marketListVO.setIsEdit("mkedit"); // upload 재활용
			
			mav.addObject("mkdetail", marketListDomain);
			mav.addObject("files", marketList);
			mav.addObject("fileLen", marketList.size());
			
			mav.setViewName("market/marketEditList.html");
			return mav;
		}
	
	@PostMapping("mkeditSave")
		public ModelAndView mkeditSave(@ModelAttribute("marketListVO") MarketListVO marketListVO, MultipartHttpServletRequest request, HttpServletRequest httpReq) throws IOException {
		ModelAndView mav = new ModelAndView();
		
		
		//저장
		marketService.fileProcess(marketListVO, request, httpReq);
		
		mav = mkSelectOneCall(marketListVO, marketListVO.getSeq(), request);
		marketListVO.setContent("");	// 컨텐츠 초기화
		marketListVO.setTitle("");	// 타이틀 초기화
	
		mav.setViewName("market/marketList.html");
		return mav;
		}
	
	@GetMapping("mkremove")
		public ModelAndView mkRemove(@RequestParam("mkSeq") String mkSeq, HttpServletRequest request) throws IOException {
		
			ModelAndView mav = new ModelAndView();
			
			HttpSession session = request.getSession();
			HashMap<String, Object> map = new HashMap<String, Object>();
			List<MarketFileDomain> marketList = null;
			if(session.getAttribute("files") != null) {
				marketList = (List<MarketFileDomain>) session.getAttribute("files");
			}
			
			map.put("mkSeq", Integer.parseInt(mkSeq));
			
			//내용 삭제하기
			marketService.mkContentRemove(map);
			
			for(MarketFileDomain list : marketList) {
				list.getUpFilePath();
				Path filePath  = Paths.get(list.getUpFilePath());
				
				
				try {
					// 파일 물리적 삭제하기
					Files.deleteIfExists(filePath);
					// DB 삭제하기 
					marketService.mkFileRemove(list);
					
				} catch (DirectoryNotEmptyException e) {
					//throw RequestException.fire(Code.E404, "디렉토리가 존재하지 않습니다", HttpStatus.NOT_FOUND);
				}
				  catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// 세션 해제하기
			session.removeAttribute("files"); // 삭제
			mav = mkListCall();
			mav.setViewName("market/marketList.html");
			
			return mav;
		
		}
		
	public ModelAndView mkListCall() {
		ModelAndView mav = new ModelAndView();
		List<MarketListDomain> items = marketService.marketList();
		mav.addObject("items", items);
		return mav;
	}

	//리스트 하나 가져오기 따로 함수뺌
	public ModelAndView mkSelectOneCall(@ModelAttribute("marketListVO") MarketListVO marketListVO, String mkSeq, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		
		map.put("mkSeq", Integer.parseInt(mkSeq));
		MarketListDomain marketListDomain =marketService.marketSelectOne(map);
		System.out.println("marketListDomain"+marketListDomain);
		List<MarketFileDomain> marketList =  marketService.marketSelectOneFile(map);
		
		for (MarketFileDomain list : marketList) {
			String path = list.getUpFilePath().replaceAll("\\\\", "/");
			list.setUpFilePath(path);
		}
		mav.addObject("mkdetail", marketListDomain);
		mav.addObject("files", marketList);

		//삭제시 사용할 용도
		session.setAttribute("files", marketList);

		return mav;
	}

}


