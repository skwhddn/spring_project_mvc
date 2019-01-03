package spring.mvcjsp.project.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;
import spring.mvcjsp.project.vo.BoardVO;
import spring.mvcjsp.project.vo.cartlistVO;
import spring.mvcjsp.project.vo.orderVO;
import spring.mvcjsp.project.vo.ordernoVO;
import spring.mvcjsp.project.vo.orderokVO;
import spring.mvcjsp.project.vo.projectVO;
import spring.mvcjsp.project.vo.stockVO;

@Repository
public class PTDAOImpl implements PTDAO {
	@Autowired
    JavaMailSender mailSender; // xml에 등록한 bean autowired
	@Autowired
	SqlSession sqlSession;
	
	//중복확인
	@Override
	public int idCheck(String strId) {
		int cnt = 0;
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		cnt = dao.idCheck(strId);
		System.out.println("cnt : " + cnt);
		return cnt;
	}
	
	//로그인처리
	@Override
	public int idpwdcheck(Map<String, Object> map) {
		int selectCnt = 0;
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		int idChkCnt = dao.idCheck((String) map.get("strId"));
		
		//로그인한 id에 해당하는 데이터가 있으면
		if(idChkCnt == 1) {
			int pwdChkCnt = dao.idpwdcheck(map);
			
			//패스워드가 일치하면 selectCnt = 1
			if(pwdChkCnt == 1) {
				selectCnt = 1;
				
			//없으면 -1
			}else {
				selectCnt = -1;
			}
		}
		
		return selectCnt;
	}
	
	//회원가입처리
	@Override
	public int insertMember(projectVO vo) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		int insertCnt = dao.insertMember(vo);
		return insertCnt;
	}
	
	//회원수정상세페이지
	@Override
	public projectVO getMemberInfo(String strId) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		System.out.println("strId : " + strId);
		projectVO dto = dao.getMemberInfo(strId);
		System.out.println("dto : " + dto);
		return dto; 
	}
	//회원수정처리
	@Override
	public int updateMember(projectVO vo) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		int updateCnt = dao.updateMember(vo);
		return updateCnt;
	}
	
	//회원탈퇴
	@Override
	public int deleteMember(String strId) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		int dto = dao.deleteMember(strId);
		return dto; 
	}
	
	//게시글 갯수
	@Override
	public int getArticleCnt() {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		int selectCnt = dao.getArticleCnt();
		return selectCnt;
	}
	
	//게시글 목록
	@Override
	public ArrayList<BoardVO> getArticleList(Map<String, Object> map) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		ArrayList<BoardVO> dtos = dao.getArticleList(map);
		return dtos;
	}
	
	//조회수증가
	@Override
	public void addReadCnt(int num) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		dao.addReadCnt(num);
	}
	
	//글상세페이지
	@Override
	public BoardVO getArticle(int num) {
		BoardVO dto = new BoardVO();
		
		System.out.println("num : " + num);
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		dto = dao.getArticle(num);
		System.out.println("dto : " + dto);
		return dto;
	}
	
	//글수정상세페이지 및 패스워드체크
	@Override
	public int pwdCheck(Map<String, Object> map) {
		int selectCnt = 0;
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		selectCnt = dao.pwdCheck(map);
		System.out.println("selectCnt : " + selectCnt);
		return selectCnt;
	}
	
	//글수정 처리
	@Override
	public int updateBoard(BoardVO vo) {
		int updateCnt = 0;
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		updateCnt = dao.updateBoard(vo);
		System.out.println("updateCnt : " + updateCnt);
		return updateCnt;
	}
	
	//답변글이 아닌경우(제목글)
	@Override
	public int getMaxNum() {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		int maxNum = dao.getMaxNum(); //SELECT max(num) FROM mvc_board
		System.out.println("maxNum : " + maxNum);
		return maxNum;
	}
	
	//답변글인경우
	@Override
	public void updateReply(BoardVO vo) {
		/*UPDATE mvc_board SET ref_step = ref_step+1 WHERE ref=? AND ref_step > ?*/
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		dao.updateReply(vo);
	}
	
	//게시판추가
	@Override
	public int insertBoard(BoardVO vo) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		
		int insertCnt = 0;
		
		//답변글이 아닌경우(제목글)
		int num = vo.getNum();
		int ref = vo.getRef();
		int ref_step = vo.getRef_step();
		int ref_level = vo.getRef_level();
		if(num ==0) {
			int cnt = dao.getArticleCnt();
			
			 //최신글부터 가져온다 (최신글부터 뿌려주므로 게시글 작성시 글번호는 최신글번호 이어야 한다.)
			if(cnt  > 0) {
				ref = getMaxNum()+1;
				System.out.println("첫글이 아닌 경우");
			//첫글이 아닌경우
			}else {
				cnt = 1;
				System.out.println("첫글인 경우");
			}
		}else {
			//삽입할 글 보다 아래쪽 글들을 UPDATE
			updateReply(vo);
			ref_step++;
			ref_level++;
		}
		//주의사항
		vo.setRef(ref);
		vo.setRef_step(ref_step);
		vo.setRef_level(ref_level);
		insertCnt = dao.insertBoard(vo);
		
	return insertCnt;
	
	}

	//답글이 존재하는지 여부 //1. checkReply(dto)
	@Override
	public int checkReply(BoardVO dto) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		int selectCnt = dao.checkReply(dto);
		return selectCnt;
	}
	
	@Override
	public int delete(BoardVO dto) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		int deleteCnt = dao.delete(dto);
		return deleteCnt;
	}
	
	@Override
	public int updateRef_step(BoardVO dto) {
		int updateCnt = 0;
		return updateCnt;
	}
	@Override
	public int deleteBoard(int num) {
		int deleteCnt = 0;
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		//1. 상세조회
		BoardVO dto = dao.getArticle(num);
			
			//넘겨받은 num에 해당하는 키값이 존재하면
			if(dto.getNum() != 0) {
				//답글이 존재하는지 여부 //1. checkReply(dto)
				int replyCnt = dao.checkReply(dto);
				
				//답글이 존재하는 경우
				if(replyCnt != 0) {
					deleteCnt = dao.delete(dto);
					System.out.println("답글이 존재하여 deleteCnt : " + deleteCnt);
					if(deleteCnt > 0) {
						int updateCnt = dao.updateRef_step(dto);
						System.out.println("updateCnt : " + updateCnt);
					}
				}else {
					//delete(dto)
					/*sql = "DELETE mvc_board WHERE num = ?";*/
					deleteCnt = dao.deleteBoard(num);
					System.out.println("답글이 존재하지 않아 삭제. deleteCnt : " + deleteCnt);
				}
			}
		
		
		return deleteCnt;
	}
	
	//상품 카테고리 상품출력 시작
	@Override
	public ArrayList<stockVO> getTopList(Map<String, Object> map) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		ArrayList<stockVO> dtos = dao.getTopList(map);
		return dtos;
	}
	@Override
	public ArrayList<stockVO> getPantsList(Map<String, Object> map) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		ArrayList<stockVO> dtos = dao.getPantsList(map);
		return dtos;
	}
	@Override
	public ArrayList<stockVO> getShoseList(Map<String, Object> map) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		ArrayList<stockVO> dtos = dao.getShoseList(map);
		return dtos;
	}
	//상품 카테고리 상품출력 끝
	
	//상품 상세페이지
	@Override
	public stockVO detailpage(int num) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		stockVO dto = dao.detailpage(num);
		return dto;
	}
	
	//즉시구매
	@Override
	public int cartgo(orderVO vo) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		int insertCnt = dao.cartgo(vo);
		return insertCnt;
	}
	
	//주문목록
	@Override
	public ArrayList<orderVO> getorderlist(Map<String, Object> map) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		ArrayList<orderVO> dtos = dao.getorderlist(map);
		return dtos;
	}
	
	//장바구니담기
	@Override
	public int cart(cartlistVO vo) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		int insertCnt = dao.cart(vo);
		return insertCnt;
	}
	
	//장바구니목록
	@Override
	public ArrayList<cartlistVO> getcartlist(Map<String, Object> map) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		ArrayList<cartlistVO> dtos = dao.getcartlist(map);
		return dtos;
	}
	
	//장바구니삭제
	@Override
	public int cartdelete(String[] num) {
		int deleteCnt = 0;
		System.out.println("cartdelete : " + num);
		for (int i = 0; i < num.length; i++) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		deleteCnt = dao.cartdelete(Integer.parseInt(num[i]));
		}
		return deleteCnt;
	}
	//이건저도...
	@Override
	public int cartdelete(int parseInt) {
		return 0;
	}
	
	//개별구매
	@Override
	public int order(orderVO vo) {
		int insertCnt = 0;
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		insertCnt = dao.order(vo);
		return insertCnt;
	}
	
	//장바구니 구매시 장바구니 목록 삭제
	@Override
	public int cartby(int num) {
		int deleteCnt =0;
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		deleteCnt = dao.cartby(num);
		System.out.println("deleteCnt" + deleteCnt);
		return deleteCnt;
	}
	
	
	//주문취소
	@Override
	public int orderno(String[] num) {
		int deleteCnt = 0;
		for (int i = 0; i < num.length; i++) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		deleteCnt = dao.orderno(Integer.parseInt(num[i]));
		}
		return deleteCnt;
	}
	
	@Override
	public int orderno(int parseInt) {
		return 0;
	}
	
	//주문완료목록
	@Override
	public ArrayList<orderokVO> orderoklist(Map<String, Object> map) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		ArrayList<orderokVO> dtos = dao.orderoklist(map);
		return dtos;
	}
	
	
	//주문승인
	@Override
	public int orderok(orderokVO vo) {
		System.out.println("num" + vo.getNum());
		System.out.println("age" + vo.getAge());
		System.out.println("subject" + vo.getSubject());
		System.out.println("price" + vo.getPrice());
		System.out.println("m_id" + vo.getM_id());
		System.out.println("color" + vo.getColor());
		
		int insertCnt = 0;
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		insertCnt = dao.orderok(vo);
		return insertCnt;
	}
	//주문승인후 삭제
	@Override
	public int hostorderdelete(int num) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		int deleteCnt = dao.hostorderdelete(num);
		return deleteCnt;
	}
	//환불신청
	@Override
	public int ordercancel(ordernoVO vo) {
		int insertCnt = 0;
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		insertCnt = dao.ordercancel(vo);
		return insertCnt;
	}
	
	//주문완료목록삭제
	@Override
	public int guestordercancel(int num) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		int deleteCnt = dao.guestordercancel(num);
		return deleteCnt;
	}

	
	//이메일인증
	@Override
	public void sendmail(String email, String key) {
		 try{
	            //SimpleMailMessage message = new SimpleMailMessage();
	            //message.setFrom("admin@nambi.com");
	            //message.setTo(email);
	            //message.setSubject(" 아이디 찾기 인증메일");
	            //message.setText("링크를 눌러 인증을 완료하세요. <br><br>" + "http://localhost:8080/bms/findMyId?key="+key);
	            //mailSender.send(message);
			 
			 	System.out.println("Daokey : " + key);
	              MimeMessage message = mailSender.createMimeMessage();
	            String txt = "여기서 사면 옷 잘입는 다고 못함 쇼핑몰 회원가입 인증 메일입니다. 인증번호를 입력하여 회원가입을 완료하세요." 
	  			+ "<br>인증번호 : " + key;
	            message.setSubject("회원가입 인증 메일입니다.");
	            message.setText(txt, "UTF-8", "html");
	            message.setFrom(new InternetAddress("admin@mss.com"));
	            message.addRecipient(RecipientType.TO, new InternetAddress(email));
	            mailSender.send(message);
	        }catch(Exception e){
	            e.printStackTrace();
	        }   
	    }
}