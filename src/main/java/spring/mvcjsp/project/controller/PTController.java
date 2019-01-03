
package spring.mvcjsp.project.controller;
import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import spring.mvcjsp.project.service.PTserviceImpl;
import spring.mvcjsp.project.service.hostserviceImpl;

@Controller
public class PTController  {
	@Autowired
	PTserviceImpl service;
	@Autowired
	hostserviceImpl service2;
	
	//메인페이지
	@RequestMapping("main")
	public String main(HttpServletRequest req, Model model) {
		System.out.println("main");
		
		return "guest/main";
	}
	//회원가입페이지 이동
	@RequestMapping("join")
	public String join(HttpServletRequest req, Model model) {
		System.out.println("join");
	
		return "common/guestjoin";
	}
	//중복처리
	@RequestMapping("confirmId")
	public String confirmId(HttpServletRequest req, Model model) {
		System.out.println("confirmId");
		
		service.confirmId(req, model);
		return "common/confirmId";
	}
	//로그인페이지이동
	@RequestMapping("login")
	public String login(HttpServletRequest req, Model model) {
		System.out.println("login");
		
		return "common/login";
	}
	//로그인처리
	@RequestMapping("guestlogin")
	public String guestlogin(HttpServletRequest req, Model model) {
		System.out.println("guestlogin");
		
		service.loginPro(req, model);
		return "guest/main";
	}
	//로그아웃
	@RequestMapping("logout")
	public String logout(HttpServletRequest req, Model model) {
		System.out.println("logout");
		
		req.getSession().invalidate();
		
		return "guest/main";
	}
	//회원가입처리
	@RequestMapping("uesrjoin")
	public String uesrjoin(HttpServletRequest req, Model model) {
		System.out.println("uesrjoin");
		
		service.userjoin(req, model);
		return "common/userjoin";
	}
	//마의페이지
	@RequestMapping("guestmafage")
	public String guestmafage(HttpServletRequest req, Model model) {
		System.out.println("guestmafage");
		
		return "guest/guestmyfage";
	}
	//회원수정시 비밀번호입력  페이지
	@RequestMapping("mafage")
	public String mafage(HttpServletRequest req, Model model) {
		System.out.println("myfage");
		
		return "guest/mafage";
	}
	//회원수정 상세 페이지
	@RequestMapping("mafagemain")
	public String mafagemain(HttpServletRequest req, Model model) {
		System.out.println("myfagemain");
		
		service.modifyView(req, model);
		return "guest/mafagemain";
	}
	//회원탈퇴 페이지 이동
	@RequestMapping("guestdelete")
	public String guestdelete(HttpServletRequest req, Model model) {
		System.out.println("guestdelete");
		
		return "guest/geustdelete";
	}
	//회원탈퇴 처리
	@RequestMapping("guestdeleteok")
	public String guestdeleteok(HttpServletRequest req, Model model) {
		System.out.println("guestdeleteok");
		
		service.deletePro(req, model);
		return "guest/guestdeletok";
	}
	//글목록
	@RequestMapping("board")
	public String board(HttpServletRequest req, Model model) {
		System.out.println("boardList");
		
		service.boardList(req, model);
		return "board/boardList";
	}
	//글상세페이지
	@RequestMapping("contentForm")
	public String contentForm(HttpServletRequest req, Model model) {
		System.out.println("contentForm");
			
		service.contentForm(req, model);
		return "board/contentForm";
	}
	//글수정 전...비밀번호입력
	@RequestMapping("modifyForm")
	public String modifyForm(HttpServletRequest req, Model model) {
		System.out.println("modifyForm");
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		req.setAttribute("num", num);
		req.setAttribute("pageNum", pageNum);	
		
		return "board/modifyForm";
	}
	//글수정 상세페이지
	@RequestMapping("modifyView")
	public String modifyView(HttpServletRequest req, Model model) {
		System.out.println("modifyView");
		
		service.modify(req, model);
		return "board/modify";
	}
	//글수정 처리
	@RequestMapping("modifyPro")
	public String modifyPro(HttpServletRequest req, Model model) {
		System.out.println("modifyPro");
		
		service.modifyPro(req, model);
		return "board/modifyPro";
	}
	//글쓰기 페이지 이동
	@RequestMapping("writeForm")
	public String writeForm(HttpServletRequest req, Model model) {
		System.out.println("writeForm");
		// 제목글(답변글이 아닌 경우)
		int num = 0;
		int ref = 1; // 그룹화 아이디
		int ref_step = 0; // 글순서(행)
		int ref_level = 0; // 글레벨(들여쓰기)
		int pageNum = 0;
	
		// 답변글
		// contentForm.jsp에서 get방식으로 넘긴 값 num, ref, ref_step, ref_level
		if(req.getParameter("num") != null) {

			num = Integer.parseInt(req.getParameter("num"));
			ref = Integer.parseInt(req.getParameter("ref"));
			ref_step = Integer.parseInt(req.getParameter("ref_step"));
			ref_level = Integer.parseInt(req.getParameter("ref_level"));
		}
		pageNum = Integer.parseInt(req.getParameter("pageNum"));

		model.addAttribute("num", num);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("ref", ref);
		model.addAttribute("ref_step", ref_step);
		model.addAttribute("ref_level", ref_level);
	
		return "board/writeForm";
	}
	//글쓰기처리페이지
	@RequestMapping("writePro")
	public String writePro(HttpServletRequest req, Model model) {
		System.out.println("writePro");
		
		service.writePro(req, model);
		return "board/writePro";
	}
	//글삭제 전 비밀번호체크페이지
	@RequestMapping("deleteForm")
	public String deleteForm(HttpServletRequest req, Model model) {
		System.out.println("deleteForm");
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		model.addAttribute("num", num);
		model.addAttribute("pageNum", pageNum);
		
		return "board/deleteForm";
	}
	
	//글삭제 전 비밀번호체크페이지
	@RequestMapping("deletePro")
	public String deletePro(HttpServletRequest req, Model model) {
		System.out.println("deletePro");
	
		service.delete(req, model);
		return "board/deletePro";
	}
	
	//TOP 카테고리 이동
	@RequestMapping("top")
	public String top(HttpServletRequest req, Model model) {
		System.out.println("top");
		
		service.toplist(req, model);
		return "host/TOP";
	}
	//PANTS 카테고리 이동
	@RequestMapping("pants")
	public String pants(HttpServletRequest req, Model model) {
		System.out.println("pants");
		
		service.pantslist(req, model);
		return "host/PANTS";
	}
	//SHOES 카테고리 이동
	@RequestMapping("shoes")
	public String shoes(HttpServletRequest req, Model model) {
		System.out.println("shoes");
		
		service.shoselist(req, model);
		return "host/SHOES";
	}
	//제품상세페이지
	@RequestMapping("detailpage")
	public String detailpage(HttpServletRequest req, Model model) {
		System.out.println("detailpage");
		
		service.detailpage(req, model);
		return "guest/detailpage";
	}
	//즉시구매
	@RequestMapping("cartgo")
	public String cartgo(HttpServletRequest req, Model model) {
		System.out.println("cartgo");
		
		service.cartgo(req, model);
		return "guest/cartgoPro";
	}
	//주문목록
	@RequestMapping("orderlist")
	public String orderlist(HttpServletRequest req, Model model) {
		System.out.println("orderlist");
		
		service.orderlist(req, model);
		return "guest/orderlist";
	}
	//주문목록 선택삭제
	@RequestMapping("orderno")
	public String cartdeleteu(HttpServletRequest req, Model model) {
		System.out.println("cartdeleteu");
		
		service.orderdelete(req, model);
		return "guest/ordernoPro";
	}
	//장바구니담기
	@RequestMapping("cartadd")
	public String cartadd(HttpServletRequest req, Model model) {
		System.out.println("cartadd");
		
		service.cartadd(req, model);
		return "guest/cartPro";
	}
	//장바구니 목록
	@RequestMapping("cartlist")
	public String cartlist(HttpServletRequest req, Model model) {
		System.out.println("cartlist");
		
		service.cartlist(req, model);
		return "guest/cartlist";
	}
	//장바구니 삭제
	@RequestMapping("cartdelete")
	public String cartdelete(HttpServletRequest req, Model model) {
		System.out.println("cartdelete");
		
		service.cartdelete(req, model);
		return "guest/cartdeletePro";
	}
	//장바구니 구매
	@RequestMapping("order")
	public String order(HttpServletRequest req, Model model) {
		System.out.println("order");
		
		service.order(req, model);
		return "guest/orderPro";
	}

	//환불신청
	@RequestMapping("ordercancel")
	public String ordercancel(HttpServletRequest req, Model model) {
		System.out.println("ordercancel");
		
		service.ordercancel(req, model);
		return "guest/ordercancelPro";
	}
	
	//이메일 인증 코드 보내기
    @RequestMapping(value="emailChk", method=RequestMethod.GET)
    public String emailChk(HttpServletRequest req, Model model) {
        System.out.println("emailChk");
        
        service.emailChk(req, model);
        
        return "guest/emailChk";
    }
    
	// ===================관리자 페이지==================
	
	//관리자로그인이동
	@RequestMapping("hostlogin")
	public String hostlogin(HttpServletRequest req, Model model) {
		System.out.println("hostlogin");
	
		return "common/hostlogin";
	}
	//관리자로그인후 메인페이지 이동
	@RequestMapping("hostmain")
	public String hostlist(HttpServletRequest req, Model model) {
		System.out.println("hostmain");
		
		service2.loginPro(req, model);
		return "host/hostmain";
	}
	//관리자 재고관리 페이지 이동
	@RequestMapping("hostlist")
	public String hostmain(HttpServletRequest req, Model model) {
		System.out.println("hostlist");
		
		service2.stocklist(req, model);
		return "host/hostlist";
	}
	//관리자 재고추가 페이지 이동
	@RequestMapping("stockadd")
	public String stockadd(HttpServletRequest req, Model model) {
		System.out.println("stockadd");
		
		return "host/stockadd";
	}
	//관리자재고관리 - 추가 - 처리    
    @RequestMapping(value="add", method=RequestMethod.POST)
    public String inventory_addPro(MultipartHttpServletRequest req, Model model) {
        System.out.println("add");
        
        service2.inventory_add(req, model);
        return "host/stockPro";
    }
    //관리자 재고삭제 페이지 이동
  	@RequestMapping("delete")
  	public String delete(HttpServletRequest req, Model model) {
  		System.out.println("delete");
  		
  		service2.stockdelete(req, model);
  		return "host/deletePro";
  	}
  	//관리자 재고수정상세페이지 이동
  	@RequestMapping("update")
  	public String update(HttpServletRequest req, Model model) {
  		System.out.println("update");
  		
  		service2.stockview(req, model);
  		return "host/update";	
  	}
  	
  	//관리자 재고수정처리 페이지 이동
  	@RequestMapping("updatee")
  	public String updatee(HttpServletRequest req, Model model) {
  		System.out.println("updatee");
  		
  		service2.stockupdate(req, model);
  		return "host/updatePro";
  	}
  	//관리자 주문승인목록
  	@RequestMapping("hostorderlist")
  	public String hostorderlist(HttpServletRequest req, Model model) {
  		System.out.println("hostorderlist");
  		
  		service.orderlist(req, model);
  		return "host/hostorderlist";
  	}
  	//관리자 주문승인처리
  	@RequestMapping("guestorderadd")
  	public String guestorderadd(HttpServletRequest req, Model model) {
  		System.out.println("guestorderadd");
  		
  		service.orderok(req, model);
  		return "host/orderok";
  	}
  	//관리자 환불신청 목록
  	@RequestMapping("hostcancel")
  	public String hostcancel(HttpServletRequest req, Model model) {
  		System.out.println("hostcancel");
  		
  		service2.guestcancel(req, model);
  		return "host/hostcancellist";
  	}
  	//관리자 환불신청 목록
  	@RequestMapping("orderdelete")
  	public String orderdelete(HttpServletRequest req, Model model) {
  		System.out.println("orderdelete");
  		
  		service2.orderdelete(req, model);
  		return "host/orderdelete";
  	}
  	//관리자 결솨소사솨솨소사ㅗ아ㅗ아ㅗ아ㅗ오나완ㅇ
  	@RequestMapping("sum")
  	public String sum(HttpServletRequest req, Model model) {
  		System.out.println("sum");
  		
  		service2.sum(req, model);
  
  		return "host/sum";
  	}
}