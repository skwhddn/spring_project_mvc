package spring.mvcjsp.project.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import spring.mvcjsp.project.persistence.PTDAOImpl;
import spring.mvcjsp.project.persistence.hostDAO;
import spring.mvcjsp.project.persistence.hostDAOImpl;
import spring.mvcjsp.project.vo.cartlistVO;
import spring.mvcjsp.project.vo.orderVO;
import spring.mvcjsp.project.vo.ordernoVO;
import spring.mvcjsp.project.vo.orderokVO;
import spring.mvcjsp.project.vo.stockVO;
import spring.mvcjsp.project.vo.sumlist;

@Service
public class hostserviceImpl implements hostservice {
	@Autowired
	hostDAOImpl dao;
	PTDAOImpl dao2;
	// 로그인
	@Override
	public void loginPro(HttpServletRequest req, Model model) {
		String strId = req.getParameter("id");
		String strPwd = req.getParameter("pwd");
		System.out.println("strid : " + strId);
		System.out.println("strPwd : " + strPwd);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("strId", strId);
		map.put("strpwd", strPwd);
		
		System.out.println("map : " + map);
		int selectCnt = dao.idpwdcheck(map);
		
		System.out.println("selectCnt : " + selectCnt);
		if (selectCnt == 1) {

			req.getSession().setAttribute("memid", strId);
		} else {
			req.getSession().setAttribute("memid", null);
		}
		model.addAttribute("cnt", selectCnt);
		System.out.println("strId : " + strId);
		System.out.println("strpwd : " + strPwd);
	}
	
	//재고목록
	@Override
	public void stocklist(HttpServletRequest req, Model model) {
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

		// 5단계. 글갯수 구하기
		cnt = dao.getArticleCnt();
		System.out.println("cnt : " + cnt); // 테이블에 30건을 insert 할것
		
		pageNum = req.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1"; // 첫페이지를 1페이지로 설정
		}
		
		if (cnt > 0) {
			// 게시글 목록 조회
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", start);
			map.put("end", end);
			ArrayList<stockVO> dtos = dao.getArticleList(map);
			model.addAttribute("dtos", dtos);
		}

		model.addAttribute("cnt", cnt); // 글갯수
		model.addAttribute("number", number); // 글번호
		model.addAttribute("pageNum", pageNum); // 페이지 번호
		System.out.println("pageNum : " + pageNum);
		if (cnt > 0) {
			model.addAttribute("startPage", startPage); // 시작 페이지
			model.addAttribute("endPage", endPage); // 마지막페이지
			model.addAttribute("pageBlock", pageBlock); // 출력할 페이지 갯수
			model.addAttribute("pageCount", pageCount); // 페이지 갯수
			model.addAttribute("currentPage", currentPage); // 현재 페이지
		}

		// 6단계. request나 session에 처리 결과를 저장(jsp에 전달하기 위함)
		req.setAttribute("cnt", cnt);
		
	}
	
	//파일업로드
	@Override
	public void inventory_add(HttpServletRequest req, Model model) {
        MultipartFile file = ((MultipartRequest) req).getFile("img");
        
        String saveDir = req.getRealPath("/resources/images/"); //저장 경로(C:\Dev\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\SPRING_BMS_Project\resources\images\)
        
        String realDir="C:\\Users\\kosmo-22\\git\\MVC_Spring_Project\\SPRING_MVCJSP_PROJECT\\src\\main\\webapp\\resources\\images\\stocklist\\"; // 저장 경로
        
        try {
            file.transferTo(new File(saveDir+file.getOriginalFilename()));
            
            FileInputStream fis = new FileInputStream(saveDir + file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(realDir + file.getOriginalFilename());
            
            int data = 0;
            
            while((data = fis.read()) != -1) {
                fos.write(data);
            }
            fis.close();
            fos.close();
 
            int num = Integer.parseInt(req.getParameter("num"));
            String fileName = file.getOriginalFilename();
            String subject = req.getParameter("subject");
            String color = req.getParameter("color");
            int count = Integer.parseInt(req.getParameter("count"));
            String price = req.getParameter("price");
            String kind = req.getParameter("kind");
            
            
            stockVO vo = new stockVO();
            vo.setNum(num);
            vo.setImg(fileName);
            vo.setSubject(subject);
            vo.setColor(color);
            vo.setAge(count);
            vo.setPrice(price);
            vo.setKind(kind);
            vo.setReg_date(new Timestamp(System.currentTimeMillis()));
            
            int iCnt = dao.inventory_add(vo);
            
            model.addAttribute("cnt", iCnt);
            
        } catch(IOException e) {
            e.printStackTrace();
        }
        
		
	}
	
	//재고삭제
	@Override
	public void stockdelete(HttpServletRequest req, Model model) {
		String[] num = req.getParameterValues("num");
		System.out.println("num : " + num);
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		System.out.println("pageNum : " + pageNum);
		
		int deleteCnt = dao.stockdelete(num);
		System.out.println("deleteCnt : " + deleteCnt);
		
		model.addAttribute("deleteCnt", deleteCnt);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("num", num);
		
	}
	//재고 수정 상세페이지
	@Override
	public void stockview(HttpServletRequest req, Model model) {
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		
		stockVO dto = dao.getArticle(num);
		
		req.setAttribute("dto", dto);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("num", num);
		
	}
	//재고 업데이트 처리
	@Override
	public void stockupdate(HttpServletRequest req, Model model) {
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		System.out.println("num : " + num);
		System.out.println("pageNum : " + pageNum);
		
		stockVO vo = new stockVO();
				
		vo.setNum(num);
		vo.setSubject(req.getParameter("subject"));
		vo.setImg(req.getParameter("img"));
		vo.setColor(req.getParameter("color"));
		vo.setAge(Integer.parseInt(req.getParameter("age")));
		vo.setPrice(req.getParameter("price"));
				
		int updateCnt = dao.update(vo);
		
		System.out.println("num : " + num);
		System.out.println("pageNum : " + pageNum);
		System.out.println("updateCnt : " + updateCnt);
		req.setAttribute("num", num);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("updateCnt", updateCnt);
	}
	
	//주문승인처리
	@Override
	public void guestorderadd(HttpServletRequest req, Model model) {
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
		
		insertCnt = dao.guestorderadd(vo);
		
		if(insertCnt == 1) {
			dao.hostby(num);
		}
		model.addAttribute("pageNum" + pageNum);
		model.addAttribute("insertCnt" + insertCnt);
	}
	
	//환불목록
	@Override
	public void guestcancel(HttpServletRequest req, Model model) {
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
			ArrayList<ordernoVO> dtos = dao.guestcancellist(map);
			model.addAttribute("dtos", dtos);
			System.out.println("dtos : " + dtos);
		}
		model.addAttribute("cnt", cnt); 
		model.addAttribute("pageNum", pageNum); 
	}
	
	//환불처리
	@Override
	public void orderdelete(HttpServletRequest req, Model model) {
		String[] num = req.getParameterValues("num");
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		System.out.println("num : " + num);
		int deleteCnt = dao.ordercancel(num);
		System.out.println("num : " + num);
		System.out.println("deleteCnt : " + deleteCnt);
		if(deleteCnt == 1) {
			dao.orderokno(num);
		}
		model.addAttribute("deleteCnt", deleteCnt);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("num", num);	
	}
	
	//결산
	@Override
	public void sum(HttpServletRequest req, Model model) {
		int top = 0;
		int pants = 0;
		int shoes = 0;
		int sum = 0;
		
		String strid = (String)req.getSession().getAttribute("memid");
		top = dao.top();
		System.out.println("top값 : " + top);
		pants = dao.pants();
		System.out.println("pants값 : " + pants);
		shoes = dao.shoes();
		System.out.println("shoes값 : " + shoes);
		sum = dao.sum();
		System.out.println("sum" + sum);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("strid", strid);
		ArrayList<orderokVO> dtos = dao.orderoklist(map);
		model.addAttribute("dtos", dtos);
		System.out.println("dtos : " + dtos);
		
		model.addAttribute("sum", sum);
		model.addAttribute("top", top);
		model.addAttribute("pants", pants);
		model.addAttribute("shoes", shoes);
	}
}