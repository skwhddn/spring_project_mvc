package spring.mvcjsp.project.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import spring.mvcjsp.project.persistence.PTDAO;
import spring.mvcjsp.project.persistence.PTDAOImpl;
import spring.mvcjsp.project.vo.BoardVO;
import spring.mvcjsp.project.vo.cartlistVO;
import spring.mvcjsp.project.vo.orderVO;
import spring.mvcjsp.project.vo.ordernoVO;
import spring.mvcjsp.project.vo.orderokVO;
import spring.mvcjsp.project.vo.projectVO;
import spring.mvcjsp.project.vo.stockVO;

@Service
public class PTserviceImpl implements PTservice {
	@Autowired
	PTDAOImpl dao;
	
	//중복처리
	@Override
	public void confirmId(HttpServletRequest req, Model model) {
		String strId = req.getParameter("id");

		int selectCnt = dao.idCheck(strId);

		System.out.println("selectCnt:" + selectCnt);

		model.addAttribute("selectCnt", selectCnt);
		model.addAttribute("id", strId);
	}
	
	//로그인처리
	@Override
	public void loginPro(HttpServletRequest req, Model model) {
		String strId = req.getParameter("id");
		String strPwd = req.getParameter("pwd");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("strId", strId);
		map.put("strpwd", strPwd);
		
		int selectCnt = dao.idpwdcheck(map);

		if (selectCnt == 1) {

			req.getSession().setAttribute("memid", strId);
		} else {
			req.getSession().setAttribute("memid", null);
		}
		model.addAttribute("cnt", selectCnt);
		System.out.println("cnt : " + selectCnt);
	}
	
	//회원가입
	@Override
	public void userjoin(HttpServletRequest req, Model model) {
		projectVO vo = new projectVO();

		vo.setId(req.getParameter("id"));
		vo.setPwd(req.getParameter("pwd"));
		vo.setName(req.getParameter("name"));

		String address = "";
		String postnm = req.getParameter("postnum");
		String addr1 = req.getParameter("addr1");
		String addr2 = req.getParameter("addr2");
		address = addr1 + addr2 + postnm;
		vo.setAddress(address);

		String hp = "";
		String hp1 = req.getParameter("hp1");
		String hp2 = req.getParameter("hp2");
		String hp3 = req.getParameter("hp3");

		if (!hp1.equals("") && !hp2.equals("") && !hp3.equals("")) {
			hp = hp1 + "-" + hp2 + "-" + hp3;
		}
		vo.setHp(hp);

		String email = "";
		String email1 = req.getParameter("email1");
		String email2 = req.getParameter("email2");
		email = email1 + "@" + email2;
		vo.setEmail(email);

		vo.setReg_date(new Timestamp(System.currentTimeMillis()));

		int insertCnt = dao.insertMember(vo);
		System.out.println("insertCnt : " + insertCnt);
		model.addAttribute("insertCnt", insertCnt);
		
		
	}
	//회원수정 상세페이지
	@Override
	public void modifyView(HttpServletRequest req, Model model) {
		String strId = (String)req.getSession().getAttribute("memid");
		String strpwd = req.getParameter("pwd");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("strId", strId);
		map.put("strpwd", strpwd);
		System.out.println("strId : " + strId);
		System.out.println("strpwd : " + strpwd);
		int selectCnt = dao.idpwdcheck(map);	
		System.out.println("selectCnt : " + selectCnt);
			
		projectVO dto = new projectVO();
		if(selectCnt == 1) {  
		dto = dao.getMemberInfo(strId);
		System.out.println("dto : " + dto);
		model.addAttribute("vo", dto);
		}
		model.addAttribute("selectCnt", selectCnt);
	}
	
	//회원수정처리
	@Override
	public void guestPro(HttpServletRequest req, Model model) {
		projectVO vo = new projectVO();
		vo.setId((String) (req.getSession().getAttribute("memid")));
		vo.setPwd(req.getParameter("pwd"));
		String hp ="";
		String hp1 = req.getParameter("hp1");
		String hp2 = req.getParameter("hp2");
		String hp3 = req.getParameter("hp3");
		if(hp1 != null && hp2 != null && hp3 != null) {
			hp = hp1+"-"+hp2+"-"+hp3;
		}
		vo.setHp(hp);
		String email1 = req.getParameter("email1");
		String email2 = req.getParameter("email2");
		vo.setEmail(email1 + "@" +email2);
		int updateCnt = dao.updateMember(vo);
		model.addAttribute("updateCnt", updateCnt);
	}

	// 회원탈퇴 처리
	@Override
	public void deletePro(HttpServletRequest req, Model model) {

		String strId = (String) req.getSession().getAttribute("memid");
		String strpwd = req.getParameter("pwd");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("strId", strId);
		map.put("strpwd", strpwd);
		System.out.println("strId : " + strId);
		System.out.println("strpwd : " + strpwd);
		
		int selectCnt = dao.idpwdcheck(map);
		System.out.println("selectCnt : " + selectCnt);

		int deleteCnt = 0;
		if(selectCnt == 1) {
			deleteCnt = dao.deleteMember(strId);
		}
		System.out.println("deleteCnt : " + deleteCnt);
		model.addAttribute("deleteCnt", deleteCnt);
		model.addAttribute("selectCnt", selectCnt);
	}
	//게시글 목록
	@Override
	public void boardList(HttpServletRequest req, Model model) {
		//3단계. 화면으로 부터 입력받은 값을 받아온다.
		//게시판 관련
		int pageSize = 5; 		//한 페이지당 출력할 글 갯수
		int pageBlock = 3; 		//한 블럭당 페이지 갯수
		
		int cnt = 0; 			// 글갯수
		int start = 0; 			// 현재페이지 시작 글번호
		int end = 0; 			// 현재 페이지 마지막 글번호
		int number = 0; 		// 출력용 글번호
		String pageNum = null; 	// 페이지 번호
		int currentPage = 0;   	// 현재 페이지
		
		int pageCount = 0;		// 페이지 갯수
		int	startPage = 0;		// 시작 페이지
		int endPage = 0;		// 마지막 페이지
	
		//5단계. 글갯수 구하기
		cnt = dao.getArticleCnt();
		System.out.println("cnt : " + cnt); // 테이블에 건을 insert 할것
		
		pageNum = req.getParameter("pageNum");
		if(pageNum == null) {
			pageNum = "1"; // 첫페이지를 1페이지로 설정
		}
		// 글 30건 기준
		currentPage = Integer.parseInt(pageNum); //현재 페이지: 1
		System.out.println("currentPage" + currentPage);
		
		// 페이지 갯수 6 = (30/5) + (0)
		pageCount = (cnt / pageSize) + (cnt % pageSize > 0 ? 1 : 0); //페이지 갯수 + 나머지있으면 1
		
		// 1 = (1 - 1) * 5 + 1
		start = (currentPage - 1) * pageSize + 1; // 현재 페이지 글번호
		
		// 5 = 1 + 5 - 1 ;
		end = start + pageSize -1; // 현재 페이지 끝번호
		
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		if(end > cnt) end = cnt;
		
		//30 = 30 - (
		number = cnt - (currentPage - 1) * pageSize; // 출력용 글번호
		
		System.out.println("number : " + number);
		System.out.println("pageSize : " + pageSize);
		
		if(cnt > 0) {
			// 게시글 목록 조회
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", start);
			map.put("end", end);
			ArrayList<BoardVO> dtos = dao.getArticleList(map);
			model.addAttribute("dtos", dtos); //큰 바구니 : 게시글목록 cf)작은 바구니 : 게시글 1건
		}
		
		//현재 1페이지 = (1/3) * 3 + 1
		startPage =(currentPage / pageBlock) * pageBlock + 1; // 시작페이지
		if(currentPage % pageBlock == 0) startPage -= pageBlock; // 나머지 계산
		System.out.println("startPage : " + startPage);
		
		//끝페이지 3 = 1 + 3 - 1
		endPage = startPage + pageBlock -1; // 마지막페이지
		if(endPage > pageCount) endPage = pageCount;
		System.out.println("endPage : " + endPage);
		
		model.addAttribute("cnt", cnt); //글갯수
		model.addAttribute("number", number); // 글번호
		model.addAttribute("pageNum", pageNum); // 페이지 번호
		
		if(cnt > 0) {
			model.addAttribute("startPage", startPage); // 시작 페이지
			model.addAttribute("endPage", endPage);	// 마지막페이지
			model.addAttribute("pageBlock", pageBlock); // 출력할 페이지 갯수
			model.addAttribute("pageCount", pageCount); // 페이지 갯수
			model.addAttribute("currentPage", currentPage); // 현재 페이지
		}

		//6단계. request나 session에 처리 결과를 저장(jsp에 전달하기 위함)
		model.addAttribute("cnt", cnt);
	}
	
	//글페이지
	@Override
	public void contentForm(HttpServletRequest req, Model model) {
		//화면으로부터 입렵받은 값을 받아온다.
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		int number = Integer.parseInt(req.getParameter("number"));
		
		System.out.println("num" + num);
		System.out.println("pageNum" + pageNum);
		System.out.println("number" + number);
		
		//조회수 증가
		dao.addReadCnt(num);
		
		//상세페이지 조회
		BoardVO dto = dao.getArticle(num);

		model.addAttribute("dto", dto);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("number", number);
	}
	
	//글수정 상세페이지
	@Override
	public void modify(HttpServletRequest req, Model model) {
		//화면으로 부터 입력받은 값 받아온다.
		
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		String pwd = req.getParameter("pwd");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", num);
		map.put("pwd", pwd);
		
		int selectCnt=0;
		selectCnt = dao.pwdCheck(map);
		System.out.println("selectCnt : " + selectCnt);
		
		//selectCnt가 1일때 num과 일치하는 게시글 1건을 읽어온다.
		if(selectCnt == 1) {
			BoardVO dto = dao.getArticle(num);
			model.addAttribute("dto", dto);
		}
		//처리 결과를 저장
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("num", num);
		model.addAttribute("selectCnt", selectCnt);
		
	}
	
	//글수정 처리
	@Override
	public void modifyPro(HttpServletRequest req, Model model) {
		//화면으로 부터 입력받은 값 받아온다.
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		
		//바구니생성
		BoardVO vo = new BoardVO();
		
		//화면에서 입력한 값(제목,내용,비밀번호)들을 바구니에 답는다.
		vo.setNum(num);
		vo.setSubject(req.getParameter("subject"));
		vo.setContent(req.getParameter("content"));
		vo.setPwd(req.getParameter("pwd"));
		

		
		//글수정처리
		int updateCnt = dao.updateBoard(vo);
		
		//처리 결과를 저장
		req.setAttribute("num", num);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("updateCnt", updateCnt);
	}
	
	//글쓰기/답변쓰기처리
	@Override
	public void writePro(HttpServletRequest req, Model model) {
		int num = Integer.parseInt(req.getParameter("num"));
		int ref = Integer.parseInt(req.getParameter("ref"));
		int ref_step = Integer.parseInt(req.getParameter("ref_step"));
		int ref_level = Integer.parseInt(req.getParameter("ref_level"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		String strId = (String) req.getSession().getAttribute("memid");
		
		//바구니 생성
		BoardVO dto = new BoardVO();
		//바구니에 담는다.
		dto.setNum(Integer.parseInt(req.getParameter("num")));
		dto.setM_id(strId);
		dto.setPwd(req.getParameter("pwd"));
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));
		dto.setReg_date(new Timestamp(System.currentTimeMillis()));
		dto.setRef(ref);
		dto.setRef_level(ref_level);
		dto.setRef_step(ref_step);
		dto.setIp(req.getRemoteAddr());
		
		
		int insertCnt = dao.insertBoard(dto);

		
		model.addAttribute("insertCnt", insertCnt);
		model.addAttribute("num", num);
		model.addAttribute("ref", ref);
		model.addAttribute("ref_step", ref_step);
		model.addAttribute("ref_level", ref_level);
		model.addAttribute("pageNum", pageNum);
		
	}
	
	//글삭제
	@Override
	public void delete(HttpServletRequest req, Model model) {
		
		//화면으로 입력받는 값
		int num = Integer.parseInt(req.getParameter("num"));	
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		String pwd = req.getParameter("pwd");
		
		System.out.println("num22 : " + num);
		System.out.println("pageNum22 : " + pageNum);
		System.out.println("pwd22 : " + pwd);
		
		//num 과 pwd map에 저장 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", num);
		map.put("pwd", pwd);
		int selectCnt = dao.pwdCheck(map);
		System.out.println("selectCnt : " + selectCnt);
		
		if(selectCnt == 1) {
		int deleteCnt = dao.deleteBoard(num);
		model.addAttribute("deleteCnt", deleteCnt);
		}
		//처리결과저장
		model.addAttribute("selectCnt", selectCnt);
		model.addAttribute("pageNum", pageNum);
	}
	
	//TOP 리스트 목록
	@Override
	public void toplist(HttpServletRequest req, Model model) {
		
		
		int pageSize = 5; // 한 페이지당 출력할 글 갯수
		int pageBlock = 3; // 한 블럭당 페이지 갯수

		int cnt = 0; // 상품갯수
		int start = 0; // 현재페이지 시작 글번호
		int end = 0; // 현재 페이지 마지막 글번호
		int number = 0; // 출력용 상품번호
		String pageNum = null; // 페이지 번호
		int currentPage = 0; // 현재 페이지

		int pageCount = 0; // 페이지 갯수
		int startPage = 0; // 시작 페이지
		int endPage = 0; // 마지막 페이지
		
		
		pageNum = req.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1"; // 첫페이지를 1페이지로 설정
		}
		
		
		if (cnt == 0) {
			// 상의 목록 조회
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", start);
			map.put("end", end);
			
			ArrayList<stockVO> dtos = dao.getTopList(map);
			req.setAttribute("dtos", dtos);
			
			System.out.println("dtos : " + dtos);
		}

		req.setAttribute("cnt", cnt); // 글갯수
		req.setAttribute("number", number); // 글번호
		req.setAttribute("pageNum", pageNum); // 페이지 번호

		if (cnt == 0) {
			req.setAttribute("startPage", startPage); // 시작 페이지
			req.setAttribute("endPage", endPage); // 마지막페이지
			req.setAttribute("pageBlock", pageBlock); // 출력할 페이지 갯수
			req.setAttribute("pageCount", pageCount); // 페이지 갯수
			req.setAttribute("currentPage", currentPage); // 현재 페이지
		}

		// 6단계. request나 session에 처리 결과를 저장(jsp에 전달하기 위함)
		req.setAttribute("cnt", cnt);
		System.out.println("cnt" + cnt);
	}
	
	//PANTS 리스트 목록
	@Override
	public void pantslist(HttpServletRequest req, Model model) {
		int cnt = 0; // 상품갯수
		int start = 0; // 현재페이지 시작 글번호
		int end = 0; // 현재 페이지 마지막 글번호
		String pageNum = null; // 페이지 번호

		pageNum = req.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1"; // 첫페이지를 1페이지로 설정
		}
		if (cnt == 0) {
			// 바지 목록 조회
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", start);
			map.put("end", end);
			ArrayList<stockVO> dtos = dao.getPantsList(map);
			req.setAttribute("dtos", dtos);
		}
		req.setAttribute("cnt", cnt); // 글갯수
		req.setAttribute("pageNum", pageNum); // 페이지 번호

		//처리 결과를 저장(jsp에 전달하기 위함)
		req.setAttribute("cnt", cnt);
	}
	
	//SHOES 리스트 목록
	@Override
	public void shoselist(HttpServletRequest req, Model model) {
		int cnt = 0; // 상품갯수
		int start = 0; // 현재페이지 시작 글번호
		int end = 0; // 현재 페이지 마지막 글번호
		String pageNum = null; // 페이지 번호
		
		pageNum = req.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1"; // 첫페이지를 1페이지로 설정
		}
		
		if (cnt == 0) {
			// 신발 목록 조회
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", start);
			map.put("end", end);
			ArrayList<stockVO> dtos = dao.getShoseList(map);
			req.setAttribute("dtos", dtos);
		}

		req.setAttribute("cnt", cnt);
		req.setAttribute("pageNum", pageNum);

		if (cnt < 0) {
		}
		//결과를 저장(jsp에 전달하기 위함)
		req.setAttribute("cnt", cnt);
	}
	
	//제품상세페이지
	@Override
	public void detailpage(HttpServletRequest req, Model model) {
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));

		stockVO dto = dao.detailpage(num);
		
		req.setAttribute("dto", dto);
		req.setAttribute("num", num);
		req.setAttribute("pageNum", pageNum);
	}
	
	//즉시구매
	@Override
	public void cartgo(HttpServletRequest req, Model model) {
		String strId = (String) req.getSession().getAttribute("memid");
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		
		System.out.println("num" + num);
		orderVO vo = new orderVO();
		
		vo.setM_id(strId);
		vo.setNum(num);
		vo.setAge(Integer.parseInt(req.getParameter("number")));
		vo.setColor(req.getParameter("color"));
		vo.setPrice(req.getParameter("price"));
		vo.setImg(req.getParameter("img"));
		vo.setSubject(req.getParameter("subject"));
		
		int insertCnt = dao.cartgo(vo);
		
		req.getSession().setAttribute("memid", strId);
		model.addAttribute("num", num);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("insertCnt", insertCnt);
		
	}
	
	//주문목록
	@Override
	public void orderlist(HttpServletRequest req, Model model) {
		String strid = (String)req.getSession().getAttribute("memid");
		int cnt = 0; 
		String pageNum = null; 
		pageNum = req.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}
		if (cnt == 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("strid", strid);
			ArrayList<orderVO> dtos = dao.getorderlist(map);
			model.addAttribute("dtos", dtos);
			System.out.println("dtos : " + dtos);
			
			ArrayList<orderokVO> vo = dao.orderoklist(map);
			model.addAttribute("vo", vo);
			System.out.println("vo : " + vo);
		}
		model.addAttribute("cnt", cnt); 
		model.addAttribute("pageNum", pageNum); 
	}
	//장바구니 담기
	@Override
	public void cartadd(HttpServletRequest req, Model model) {
		String strId = (String) req.getSession().getAttribute("memid");
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		int number = Integer.parseInt(req.getParameter("number"));
		String color = req.getParameter("color");
		String price = req.getParameter("price");
		String img = req.getParameter("img");
		String subject = req.getParameter("subject");
		
		
		System.out.println("img : " + img);
		System.out.println("number : " + number);
		cartlistVO vo = new cartlistVO();
		
		vo.setM_id(strId);
		vo.setNum(num);
		vo.setAge(number);
		vo.setColor(color);
		vo.setPrice(price);
		vo.setImg(img);
		vo.setSubject(subject);
	
		int insertCnt = dao.cart(vo);
		
		//6단계 request 나 session에 처리 결과를 저장
		req.getSession().setAttribute("memid", strId);
		model.addAttribute("num", num);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("insertCnt", insertCnt);
		
	}
	
	//장바구니목록
	@Override
	public void cartlist(HttpServletRequest req, Model model) {
		String strid = (String)req.getSession().getAttribute("memid");
		int cnt = 0; 
		String pageNum = null; 
		pageNum = req.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}
		if (cnt == 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("strid", strid);
			ArrayList<cartlistVO> dtos = dao.getcartlist(map);
			model.addAttribute("dtos", dtos);
			System.out.println("dtos : " + dtos);
		}
		model.addAttribute("cnt", cnt); 
		model.addAttribute("pageNum", pageNum); 
	}
	//장바구니삭제
	@Override
	public void cartdelete(HttpServletRequest req, Model model) {
		String[] num = req.getParameterValues("num");
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		int deleteCnt = dao.cartdelete(num);
		
		model.addAttribute("deleteCnt", deleteCnt);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("num", num);
		
	}
	
	//장바구니 개별구매
	@Override
	public void order(HttpServletRequest req, Model model) {
		int insertCnt = 0;
		int num = Integer.parseInt(req.getParameter("num"));
		String strid = (String) req.getSession().getAttribute("memid");
		int age = Integer.parseInt(req.getParameter("age"));
		String color = req.getParameter("color");
		String img = req.getParameter("img");
		String price = req.getParameter("price");
		String subject = req.getParameter("subject");
		
		System.out.println("num : " + num);
		System.out.println("strid : " + strid);
		System.out.println("age : " + age);
		System.out.println("color : " + color);
		System.out.println("img : " + img);
		System.out.println("price : " + price);
		System.out.println("subject : " + subject);
		
		orderVO vo = new orderVO();
		vo.setNum(num);
		vo.setM_id(strid);
		vo.setAge(age);
		vo.setColor(color);
		vo.setImg(img);
		vo.setPrice(price);
		vo.setSubject(subject);
		
		insertCnt = dao.order(vo);
		
		if(insertCnt == 1) {
			dao.cartby(num);
		}
		
		model.addAttribute("insertCnt" + insertCnt);
	}
	
	//주문취소
	@Override
	public void orderdelete(HttpServletRequest req, Model model) {
		String[] num = req.getParameterValues("num");
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));

		int deleteCnt = dao.orderno(num);
		System.out.println("num : " + num);
		System.out.println("deleteCnt : " + deleteCnt);
		
		model.addAttribute("deleteCnt", deleteCnt);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("num", num);
	}

	//주문승인
	@Override
	public void orderok(HttpServletRequest req, Model model) {
		int insertCnt = 0;
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		int num = Integer.parseInt(req.getParameter("num"));
		String strid = (String) req.getSession().getAttribute("memid");
		int age = Integer.parseInt(req.getParameter("age"));
		String color = req.getParameter("color");
		String img = req.getParameter("img");
		String price = req.getParameter("price");
		String subject = req.getParameter("subject");

		orderokVO vo = new orderokVO();
		vo.setNum(num);
		vo.setM_id(strid);
		vo.setAge(age);
		vo.setColor(color);
		vo.setImg(img);
		vo.setPrice(price);
		vo.setSubject(subject);
		
		insertCnt = dao.orderok(vo);
		
		if(insertCnt == 1) {
			dao.hostorderdelete(num);
		}
		model.addAttribute("pageNum" + pageNum);
		model.addAttribute("insertCnt" + insertCnt);
	}
	
	//이메일인증
	@Override
    public void emailChk(HttpServletRequest req, Model model) {
        String email = req.getParameter("email1");
        req.getSession().setAttribute("email1", email);
        
        System.out.println("emailChk : " + email);
        
        StringBuffer temp = new StringBuffer();
        Random rnd = new Random();
        
        for(int i = 0; i < 6; i++) {
            int rIndex = rnd.nextInt(2);
            switch(rIndex) {
            case 0:
                // A-Z
                temp.append((char) ((int) (rnd.nextInt(26)) + 65));
                break;
            case 1:
                // 0-9
                temp.append((rnd.nextInt(10)));
                break;
            }
        }
        
        String key = temp.toString();// StringBuffer 형식인 Key를 String으로 변환
        System.out.println("key : " + key);
        req.getSession().setAttribute("key", key);
        model.addAttribute("cnt", 1);
        
        dao.sendmail(email, key);
    }
	//환불 신청
	@Override
	public void ordercancel(HttpServletRequest req, Model model) {
		int insertCnt = 0;
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		int num = Integer.parseInt(req.getParameter("num"));
		String strid = (String) req.getSession().getAttribute("memid");
		int age = Integer.parseInt(req.getParameter("age"));
		String color = req.getParameter("color");
		String img = req.getParameter("img");
		String price = req.getParameter("price");
		String subject = req.getParameter("subject");

		ordernoVO vo = new ordernoVO();
		vo.setNum(num);
		vo.setM_id(strid);
		vo.setAge(age);
		vo.setColor(color);
		vo.setImg(img);
		vo.setPrice(price);
		vo.setSubject(subject);
		
		insertCnt = dao.ordercancel(vo);
		
		System.out.println("insertCnt : " + insertCnt);

		model.addAttribute("pageNum" + pageNum);
		model.addAttribute("insertCnt" + insertCnt);
		
	}
}