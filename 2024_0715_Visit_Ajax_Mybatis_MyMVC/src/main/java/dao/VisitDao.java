package dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import Service.MyBatisConnector;
import db.vo.VisitVo;

public class VisitDao {
	
	//Mybatis객체
	SqlSessionFactory factory;
	
	// single-ton pattern : 객체 1개만 생성해서 이용하자
	static VisitDao single = null;

	public static VisitDao getInstance() {

		// 없으면 생성해라
		if (single == null)
			single = new VisitDao();

		return single;
	}

	// 외부에서 객체 생성하지 말아라 -> 다른 클래스에서 Connection conn = new DBService().getConnection(); 적기만 해도 에러
	private VisitDao() {
		factory = MyBatisConnector.getInstance().getSqlSessionFactory();

	}
	
	// 목록조회
	public List<VisitVo> selectList() {

		List<VisitVo> list = null;

		//1.SqlSession얻어오기
		SqlSession sqlSession = factory.openSession();	// Connection획득
		
		
		//2.작업수행				namespace
		list = sqlSession.selectList("visit.visit_list");
		
		//3.닫기: conn.close()과정포함
		sqlSession.close();

		return list;
	}
	
	public List<VisitVo> selectList(Map<String, Object> map) {

		List<VisitVo> list = null;

		//1.SqlSession얻어오기
		SqlSession sqlSession = factory.openSession(); // Connection획득
		
		//2.작업수행                  namespace 
		list = sqlSession.selectList("visit.visit_list_condition" , map);
		
		//3.닫기: conn.close()과정포함 
		sqlSession.close();
		

		return list;
	}//end:selectList(map)
	

	public int selectRowTotal(Map<String, Object> map) {

		int total = 0;
		//1.SqlSession 얻어오기
		SqlSession sqlSession = factory.openSession();

		//2.작업수행
		total = sqlSession.selectOne("visit.visit_row_total", map);

		//3.닫기
		sqlSession.close();
		
		return total;
	}
	
	public int insert(VisitVo vo) {

		int res = 0;

		//String sql = "insert into visit values(seq_visit_idx.nextVal, ?, ?, ?, ?, sysdate)";

		//1.SqlSession얻어오기
		SqlSession sqlSession = factory.openSession();	// Connection획득		
		
		//2.작업수행				namespace
		res = sqlSession.insert("visit.visit_list_insert", vo);
		
		if(res==1)
			sqlSession.commit();
		
		//3.닫기: conn.close()과정포함
		sqlSession.close();
		

		return res;
	}//end:delete()

	public int delete(int idx) {

		int res = 0;
		
		//1.SqlSession얻어오기						true <- auto commit
		SqlSession sqlSession = factory.openSession(true);	// Connection획득		
		
		//2.작업수행				namespace
		res = sqlSession.delete("visit.visit_delete", idx);
		
		if(res==1)
			sqlSession.commit();
		
		//3.닫기: conn.close()과정포함
		sqlSession.close();
		

		return res;
	}//end:delete()
	
	
	public VisitVo selectOne(int idx) {

		VisitVo vo = null;

		//1.SqlSession얻어오기						true <- auto commit
		SqlSession sqlSession = factory.openSession(true);	// Connection획득		
		
		//2.작업수행				namespace
		vo = sqlSession.selectOne("visit.visit_one", idx);
		
		
		//3.닫기: conn.close()과정포함
		sqlSession.close();
		

		return vo;
	}

	public int update(VisitVo vo) {

		int res = 0;
		
		//1.SqlSession얻어오기						true <- auto commit
		SqlSession sqlSession = factory.openSession(true);	// Connection획득		
		
		//2.작업수행				namespace
		res = sqlSession.update("visit.visit_update", vo);
		
		if(res==1)
			sqlSession.commit();
		
		//3.닫기: conn.close()과정포함
		sqlSession.close();

		return res;
	}//end:update()

	
}