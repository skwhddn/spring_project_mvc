package spring.mvcjsp.project.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

public interface hostservice {
	//로그인
	public void loginPro(HttpServletRequest req, Model model);
	
	//재고목록
	public void stocklist(HttpServletRequest req, Model model);
	
	//재고추가
	public void inventory_add(HttpServletRequest req, Model model);

	//재고삭제
	public void stockdelete(HttpServletRequest req, Model model);
	
	//재고수정 상세페이지
	public void stockview(HttpServletRequest req, Model model);
	
	//재고수정
	public void stockupdate(HttpServletRequest req, Model model);
	
	//주문승인
	public void guestorderadd(HttpServletRequest req, Model model);
	
	//환불목록
	public void guestcancel(HttpServletRequest req, Model model);
	
	//환불처리
	public void orderdelete(HttpServletRequest req, Model model);
	
	//결산
	public void sum(HttpServletRequest req, Model model);
	
}	
