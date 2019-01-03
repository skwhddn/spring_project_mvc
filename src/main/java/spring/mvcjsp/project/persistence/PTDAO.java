package spring.mvcjsp.project.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import spring.mvcjsp.project.vo.BoardVO;
import spring.mvcjsp.project.vo.cartlistVO;
import spring.mvcjsp.project.vo.orderVO;
import spring.mvcjsp.project.vo.ordernoVO;
import spring.mvcjsp.project.vo.orderokVO;
import spring.mvcjsp.project.vo.projectVO;
import spring.mvcjsp.project.vo.stockVO;

public interface PTDAO {
	// 중복확인 체크
	public int idCheck(String strId);
	
	// 로그인 처리(아이디 비밀번호 체크 -> 회원수정,회원탈퇴)
	public int idpwdcheck(Map<String, Object> map);
	
	// 회원가입 처리
	public int insertMember(projectVO vo);
	
	//회원정보 상세 페이지
	public projectVO getMemberInfo(String strId);
	
	//회원탈퇴 처리
	public int deleteMember(String strid);
	
	//회원정보 수정 처리
	public int updateMember(projectVO vo);
	
	//글 갯수 구하기
	public int getArticleCnt();
	
	//게시글 목록 조회 (큰바구니 ArrayList에 담겟다)
	public ArrayList<BoardVO> getArticleList(Map<String, Object> map);
	
	//조회수 증가
	public void addReadCnt(int num);
		
	//상세페이지
	public BoardVO getArticle(int num);
	
	//비밀번호 확인(게시글 수정, 게시글 삭제)
	public int pwdCheck(Map<String, Object> map);
	
	//글수정 처리페이지
	public int updateBoard(BoardVO vo);
	
	//답변글이 아닌경우
	public int getMaxNum();
	//답변글인경우
	public void updateReply(BoardVO vo);
	//글작성 / 답글 처리 페이지
	public int insertBoard(BoardVO vo);
	
	//글삭제
	public int checkReply(BoardVO dto);
	public int delete(BoardVO dto);
	public int updateRef_step(BoardVO dto);
	public int deleteBoard(int num);
	
	//상의 리스트
	public ArrayList<stockVO> getTopList(Map<String, Object> map);
	
	//하의 리스트
	public ArrayList<stockVO> getPantsList(Map<String, Object> map);
	
	//신발 리스트
	public ArrayList<stockVO> getShoseList(Map<String, Object> map);
	
	//상품 상세페이지
	public stockVO detailpage(int num);
	
	//즉시구매
	public int cartgo(orderVO vo);
	
	//주문목록
	public ArrayList<orderVO> getorderlist(Map<String, Object> map);
	public ArrayList<orderokVO> orderoklist(Map<String, Object> map);
	//장바구니 담기
	public int cart(cartlistVO vo);
	
	//장바구니 목록
	public ArrayList<cartlistVO> getcartlist(Map<String, Object> map);
	
	//장바구니 삭제
	public int cartdelete(String[] num);
	public int cartdelete(int parseInt);
	
	//장바구니 구매
	public int order(orderVO vo);
	public int cartby(int num);
	
	//주문삭제
	public int orderno(String[] num);
	public int orderno(int parseInt);
	
	//주문승인
	public int orderok(orderokVO vo);
	public int hostorderdelete(int num);
	
	
	//이메일인증
    public void sendmail(String email, String key);
    
	//환불요청목록
	public int ordercancel(ordernoVO vo);
	public int guestordercancel(int num);
}
