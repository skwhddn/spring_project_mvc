package spring.mvcjsp.project.persistence;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.mvcjsp.project.vo.BoardVO;
import spring.mvcjsp.project.vo.orderVO;
import spring.mvcjsp.project.vo.ordernoVO;
import spring.mvcjsp.project.vo.orderokVO;
import spring.mvcjsp.project.vo.stockVO;

@Repository
public class hostDAOImpl implements hostDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	//아이디체크
	@Override
	public int idCheck(String strId) {
		int cnt = 0;
		hostDAO dao = sqlSession.getMapper(hostDAO.class);
		cnt = dao.idCheck(strId);
		System.out.println("cnt : " + cnt);
		return cnt;
	}
	
	//아이디 or 비밀번호 체크
	@Override
	public int idpwdcheck(Map<String, Object> map) {
		int selectCnt = 0;
		hostDAO dao = sqlSession.getMapper(hostDAO.class);
		int idChkCnt = dao.idCheck((String) map.get("strId"));
		//로그인한 id에 해당하는 데이터가 있으면
		System.out.println("idChkCnt" + idChkCnt);
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
	
	//관리자 재고 갯수
	@Override
	public int getArticleCnt() {
		hostDAO dao = sqlSession.getMapper(hostDAO.class);
		int selectCnt = dao.getArticleCnt();
		return selectCnt;
	}
	
	//관리자 재고 목록
	@Override
	public ArrayList<stockVO> getArticleList(Map<String, Object> map) {
		hostDAO dao = sqlSession.getMapper(hostDAO.class);
		ArrayList<stockVO> dtos = dao.getArticleList(map);
		return dtos;
	}
	
	//관리자 재고 추가
	@Override
	public int inventory_add(stockVO vo) {
		System.out.println(vo);
        int cnt = 0;
        hostDAO dao = sqlSession.getMapper(hostDAO.class);
        cnt = dao.inventory_add(vo);
        System.out.println(cnt==1 ? "추가 성공":"추가 실패");
        return cnt;
	}
	
	//관리자 재고 삭제
	@Override
	public int stockdelete(String[] num) {
		int deleteCnt = 0;
		for (int i = 0; i < num.length; i++) {
		hostDAO dao = sqlSession.getMapper(hostDAO.class);
		deleteCnt = dao.stockdelete(Integer.parseInt(num[i]));
		}
		return deleteCnt;
	}
	
	//상품상세페이지
	@Override
	public stockVO getArticle(int num) {
		hostDAO dao = sqlSession.getMapper(hostDAO.class);
		stockVO dtos = dao.getArticle(num);
		return dtos;
	}
	
	//먼지모르겠습니다..
	@Override
	public int stockdelete(int parseInt) {
		
		return 0;
	}
	
	//상품수정처리
	@Override
	public int update(stockVO vo) {
		hostDAO dao = sqlSession.getMapper(hostDAO.class);
		int updateCnt = dao.update(vo);
		return updateCnt;
	}
	
	//주문승인
	@Override
	public int guestorderadd(orderokVO vo) {
		int insertCnt = 0;
		System.out.println("num" + vo.getNum());
		System.out.println("num" + vo.getColor());
		System.out.println("num" + vo.getAge());
		System.out.println("num" + vo.getM_id());
		System.out.println("num" + vo.getImg());
		System.out.println("num" + vo.getPrice());
		System.out.println("num" + vo.getSubject());
		
		hostDAO dao = sqlSession.getMapper(hostDAO.class);
		insertCnt = dao.guestorderadd(vo);
		return insertCnt;
	}
	
	//주문승인시 주문요청목록 삭제
	@Override
	public int hostby(int num) {
		int deleteCnt = 0;
		hostDAO dao = sqlSession.getMapper(hostDAO.class);
		deleteCnt = dao.hostby(num);
		return deleteCnt;
	}

	//환불목록
	@Override
	public ArrayList<ordernoVO> guestcancellist(Map<String, Object> map) {
		hostDAO dao = sqlSession.getMapper(hostDAO.class);
		ArrayList<ordernoVO> dtos = dao.guestcancellist(map);
		return dtos;
	}
	
	//환불처리
	@Override
	public int ordercancel(String[] num) {
		int deleteCnt = 0;
		System.out.println("String[] num : " + num);
		for (int i = 0; i < num.length; i++) {
		hostDAO dao = sqlSession.getMapper(hostDAO.class);
		deleteCnt = dao.ordercancel(Integer.parseInt(num[i]));
		}
		return deleteCnt;
	}
	@Override
	public int ordercancel(int parseInt) {
		return 0;
	}
	
	//환불후 환불요청목록삭제
	@Override
	public int orderokno(String[] num) {
		int deleteCnt = 0;
		for (int i = 0; i < num.length; i++) {
		hostDAO dao = sqlSession.getMapper(hostDAO.class);
		deleteCnt = dao.orderokno(Integer.parseInt(num[i]));
		}
		return deleteCnt;
	}

	@Override
	public int orderokno(int parseInt) {
		return 0;
	}
	
	//결산
	@Override
	public int top() {
		int top = 0;
		hostDAO dao = sqlSession.getMapper(hostDAO.class);
		top = dao.top();
		return top;
	}

	@Override
	public int pants() {
		int pants = 0;
		hostDAO dao = sqlSession.getMapper(hostDAO.class);
		pants = dao.pants();
		return pants;
	}

	@Override
	public int shoes() {
		int shoes = 0;
		hostDAO dao = sqlSession.getMapper(hostDAO.class);
		shoes = dao.shoes();
		return shoes;
	}
	
	@Override
	public int sum() {
		int sum = 0;
		hostDAO dao = sqlSession.getMapper(hostDAO.class);
		sum = dao.sum();
		return sum;
	}
	//주문완료목록
	@Override
	public ArrayList<orderokVO> orderoklist(Map<String, Object> map) {
		PTDAO dao = sqlSession.getMapper(PTDAO.class);
		ArrayList<orderokVO> dtos = dao.orderoklist(map);
		return dtos;
	}
}
