package spring.mvcjsp.project.persistence;

import java.util.ArrayList;
import java.util.Map;

import spring.mvcjsp.project.vo.orderVO;
import spring.mvcjsp.project.vo.ordernoVO;
import spring.mvcjsp.project.vo.orderokVO;
import spring.mvcjsp.project.vo.orderokVO.*;
import spring.mvcjsp.project.vo.stockVO;

public interface hostDAO {
	// 중복확인 체크
	public int idCheck(String strId);
	
	// 로그인 처리(아이디 비밀번호 체크 -> 회원수정,회원탈퇴)
	public int idpwdcheck(Map<String, Object> map);
	
	//글 갯수 구하기
	public int getArticleCnt();
	
	// 상품목록
	public ArrayList<stockVO> getArticleList(Map<String, Object> map);
	
	//상품추가
	public int inventory_add(stockVO vo);
	
	//상품 삭제
	public int stockdelete(String[] num);
	public int stockdelete(int parseInt);
	
	//상품 수정 페이지
	public stockVO getArticle(int num);
	
	//상품수정 처리
	public int update(stockVO vo);
	
	//주문 승인 확정
	public int guestorderadd(orderokVO vo);
	public int hostby(int num);
	
	//환불목록
	public ArrayList<ordernoVO> guestcancellist(Map<String, Object> map);
	
	//환불처리
	public int ordercancel(String[] num);
	public int ordercancel(int parseInt);
	public int orderokno(String[] num);
	public int orderokno(int parseInt);
	
	//결산
	public int top();
	public int pants();
	public int shoes();
	public int sum();
	public ArrayList<orderokVO> orderoklist(Map<String, Object> map);
}
