package spring.mvcjsp.project.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

public interface PTservice {
	//중복확인
	public void confirmId(HttpServletRequest req, Model model);
	
	//로그인
	public void loginPro(HttpServletRequest req, Model model);
	
	//회원가입
	public void userjoin(HttpServletRequest req, Model model);
	
	//회원정보 수정 상세 페이지
	public void modifyView(HttpServletRequest req, Model model);
	
	//회원정보 수정 처리
	public void guestPro(HttpServletRequest req, Model model);
	
	//회원탈퇴
	public void deletePro(HttpServletRequest req, Model model);
	
	// 글목록
	public void boardList(HttpServletRequest req, Model model);
	
	// 상세페이지
	public void contentForm(HttpServletRequest req, Model model);
	
	// 글 수정 상세페이지
	public void modify(HttpServletRequest req, Model model);
	
	// 글 수정 처리페이지
	public void modifyPro(HttpServletRequest req, Model model);
	
	//글작성 /답글 처리 페이지
	public void writePro(HttpServletRequest req, Model model);
	
	//글삭제
	public void delete(HttpServletRequest req, Model model);
	
	//상의리스트
	public void toplist(HttpServletRequest req, Model model);
	
	//하의리스트
	public void pantslist(HttpServletRequest req, Model model);
		
	//신발리스트
	public void shoselist(HttpServletRequest req, Model model);
	
	//제품 상세 페이지 
	public void detailpage(HttpServletRequest req, Model model);
	
	//즉시구매
	public void cartgo(HttpServletRequest req, Model model);
	
	//주문목록뿌리기
	public void orderlist(HttpServletRequest req, Model model);
	
	//장바구니 담기
	public void cartadd(HttpServletRequest req, Model model);
	
	//장바구니목록
	public void cartlist(HttpServletRequest req, Model model);
	
	//장바구니삭제
	public void cartdelete(HttpServletRequest req, Model model);
	
	//장바구니 개별 구매
	public void order(HttpServletRequest req, Model model);
	
	//주문취소
	public void orderdelete(HttpServletRequest req, Model model);
	
	//주문승인
	public void orderok(HttpServletRequest req, Model model);
	
	//이메일인증
	public void emailChk(HttpServletRequest req, Model model);
	
	//환불신청
	public void ordercancel(HttpServletRequest req, Model model);
}
