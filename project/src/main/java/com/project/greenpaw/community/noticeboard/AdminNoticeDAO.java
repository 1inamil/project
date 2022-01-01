package com.project.greenpaw.community.noticeboard;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.project.greenpaw.community.common.BoardTO;
import com.project.greenpaw.community.common.PageTO;

@Repository
public class AdminNoticeDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<BoardTO> boardRowMapper = BeanPropertyRowMapper.newInstance(BoardTO.class);

	///일괄 공개체크
	public ArrayList<BoardTO> getList(String categorySeq, int page, int perPage) {

		ArrayList<BoardTO> datas = new ArrayList<BoardTO>();

		String sql = "select * from board where category_seq=? order by seq desc limit ?, ?";
		Object[] args = {categorySeq, (page-1) *perPage, perPage};
		datas = (ArrayList<BoardTO>) jdbcTemplate.query(sql, args, boardRowMapper);
		
		return datas;
	}
	
	//일괄삭제 메소드
	public int deleteAll(int[] deleteSeq) {

		int result = 0;
		String params = "";
		
		for(int i=0; i<deleteSeq.length; i++) {
			params +=deleteSeq[i];
			//마지막이 아닐때만 뒤에 , 넣어줌 
			if(i < deleteSeq.length-1) { params += "," ; }
		}
		
		String sql ="delete from board where seq in("+params+")";
		result = jdbcTemplate.update(sql);

		return result;
	}
	
	//inputbox에 넣을 숫자...
	public ArrayList<String> getNoticeSeq(String categorySeq, int page, int perPage) {

		ArrayList<String> datas = new ArrayList<String>();

		String sql = "select seq from board where category_seq=? order by seq desc limit ?, ?";
		Object[] args = {categorySeq, (page - 1) * perPage, perPage};
		datas = (ArrayList<String>)jdbcTemplate.queryForList(sql, args, String.class);
		
		return datas;
	}
	
	//일괄공개,일괄비공개
	public int privateNoticeAll(int[] privateSeqs,int[] allList){
		
		List<String> privateList = new ArrayList<String>();
		for(int i=0; i<=privateSeqs.length; i++) {
			privateList.add(String.valueOf(privateSeqs[i]));
		}
		
		List<String> allLists = new ArrayList<String>();
		for(int i=0; i<=allList.length; i++) {
			privateList.add(String.valueOf(allList[i]));
		}
		
		//List<String>으로 감
		return privateNoticeAll(privateList,allLists);
	}
	
	//String으로 감
	public int privateNoticeAll(List<String> privateSeqs,List<String> allList){
			
		String privateSeqsStr = String.join(",", privateSeqs);
		String allListStr = String.join(",", allList);
		
		return privateNoticeAll(privateSeqsStr,allListStr);
	}
	
	public int privateNoticeAll(String privateSeqsStr, String allListStr){
		
		int result = 0;
		/*System.out.println("---------------------------");
		System.out.println("privateSeqsStr: "+ privateSeqsStr);
		System.out.println("allListStr: "+ allListStr);*/
		
		//공개 sql
		String sqlOpen ="update board set is_private=0 where seq in("+privateSeqsStr+")";

		//비공개 sql
		String sqlClose ="update board set is_private=1 where seq in("+allListStr+")";
		
		//공개 실행
		result += jdbcTemplate.update(sqlOpen);
			
		//비공개 실행
		if(!allListStr.equals("")) {
			result += jdbcTemplate.update(sqlClose);
		}

		return result;
	}
	
	
	//글쓰기
	public int writeOk(BoardTO post) {

		int flag = 0;
		String sql = "insert into board values (0, ?, ?, ?, ?, " + "?, ?, ?, ?, " + "0, 0, now(), now(), ?) ";
		Object[] args = {post.getCategorySeq(), post.getSubTitle(), post.getSaleStatus(), post.getFamilyMemberType(),
				post.getTitle(), post.getContent(), post.getThumbUrl(), post.getNickname(), post.isPrivate()};

		flag = jdbcTemplate.update(sql, args);

		return flag;
	}
	
	//이미지파일명 가져오기
	public ArrayList<String> getImgList(int seq) {
		
		String content = "";
		ArrayList<String> ImgList=new ArrayList<String>();
		
		String sql ="select content from board where seq=?";
		Object[] args = {seq};			
		content = jdbcTemplate.queryForObject(sql, args, String.class);

		//이미지 경로 추출하는 정규식
		Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
		Matcher matcher = pattern.matcher(content);
			
		//url추출
		while(matcher.find()) {
			String fileurl = matcher.group(1);
			ImgList.add(fileurl);
		}
		return ImgList;
	}
	
	
	// 페이지 개수 
	public PageTO boardList(PageTO pageTO, String category, String field, String keyword, String subtitle) {

		if (field == "type" || field.equals("type")) {
			field = "family_member_type";
		}

		int cpage = pageTO.getCpage();
		int recordPerPage = pageTO.getRecordPerPage();
		int blockPerPage = pageTO.getBlockPerPage();

		ArrayList<BoardTO> boardLists = new ArrayList<BoardTO>();

		String sql = "select category_seq, sub_title, sale_status, family_member_type, title, content, thumb_url, "
				+"nickname, like_count, hit, date_format(created_at, '%Y-%m-%d') created_at, updated_at,is_private "
				+"from board where category_seq=? and "
				+ field + " like ? and sub_title like ? order by seq desc limit ?, ?";

		Object[] args = {category, "%" + keyword + "%", "%" + subtitle + "%", (cpage-1)*recordPerPage, recordPerPage};

		boardLists = (ArrayList<BoardTO>)jdbcTemplate.query(sql, args, boardRowMapper);
		pageTO.setPageList(boardLists);
		
		String countSql = "select count(*) from board where category_seq=? and " 
				+ field + " like ? and sub_title like ?";
		Object[] countArgs = {category, "%" + keyword + "%", "%" + subtitle + "%" };

		int count = jdbcTemplate.queryForObject(countSql, countArgs, Integer.class);
		
		pageTO.setTotalRecord(count);
		pageTO.setTotalPage(((pageTO.getTotalRecord() - 1) / recordPerPage) + 1);

		pageTO.setStartBlock(((cpage - 1) / blockPerPage) * blockPerPage + 1);
		pageTO.setEndBlock(((cpage - 1) / blockPerPage) * blockPerPage + blockPerPage);
		
		if (pageTO.getEndBlock() >= pageTO.getTotalPage()) {
				pageTO.setEndBlock(pageTO.getTotalPage());
		}

			return pageTO;
	}
		
		
	//페이지개수(날짜 검색 시)
	public PageTO boardList(PageTO PageTO, String category,String startDate,String endDate) {

		int cpage = PageTO.getCpage();
		int recordPerPage = PageTO.getRecordPerPage();
		int blockPerPage = PageTO.getBlockPerPage();

		ArrayList<BoardTO> boardLists = new ArrayList<BoardTO>();

		String sql = "select category_seq, sub_title, sale_status, family_member_type, title, content, thumb_url, "
				+"nickname, like_count, hit, date_format(created_at, '%Y-%m-%d') created_at, updated_at,is_private "
				+"from board where category_seq=? and created_at between ? and ? order by seq desc limit ?, ?";
		Object[] args = {category, startDate, endDate, (cpage-1)*recordPerPage, recordPerPage};
		
		boardLists = (ArrayList<BoardTO>) jdbcTemplate.query(sql, args, boardRowMapper);
		PageTO.setPageList(boardLists);
		
		String countSql = "select count(*) from board where category_seq=? and created_at between ? and ?";
		Object[] countArgs = {category, startDate, endDate};
		int count = jdbcTemplate.queryForObject(countSql, countArgs, Integer.class);

		PageTO.setTotalRecord(count);
		PageTO.setTotalPage(((PageTO.getTotalRecord() - 1) / recordPerPage) + 1);
	
		PageTO.setStartBlock(((cpage - 1) / blockPerPage) * blockPerPage + 1);
		PageTO.setEndBlock(((cpage - 1) / blockPerPage) * blockPerPage + blockPerPage);
		
		if (PageTO.getEndBlock() >= PageTO.getTotalPage()) {
			PageTO.setEndBlock(PageTO.getTotalPage());
		}

		return PageTO;
	}

	// 검색
	public ArrayList<BoardTO> getSearchList(String categorySeq, String field, String keyword, PageTO pages) {

		if (field == "type" || field.equals("type")) { field = "family_member_type"; }

		ArrayList<BoardTO> datas = new ArrayList<BoardTO>();
		String sql = "select * from board where category_seq=? and " + field
				+ " like ? order by seq desc limit ?, ?";
				
		Object[] args = {categorySeq, "%" + keyword + "%", (pages.getCpage() - 1) * pages.getRecordPerPage(), pages.getRecordPerPage()}; 
		datas = (ArrayList<BoardTO>) jdbcTemplate.query(sql,  args, boardRowMapper);
		
		return datas;
	}

	// 정렬기능
	public ArrayList<BoardTO> listSort(String categorySeq, String field, String keyword, String sort, PageTO pages) {

		if (sort == null && sort.equals("")) { sort = "seq";}

		ArrayList<BoardTO> datas = new ArrayList<BoardTO>();

		String sql = "select * from board where category_seq=? and " + field + " like ? order by "
						+ sort + " desc limit ?, ?";
		Object[] args = {categorySeq,"%" + keyword + "%", (pages.getCpage() - 1) * pages.getRecordPerPage(), pages.getRecordPerPage()};
		datas = (ArrayList<BoardTO>) jdbcTemplate.query(sql,  args, boardRowMapper);
		
		return datas;
		
	}

	// 말머리만 클릭시
	public ArrayList<BoardTO> getSubTitleList(String category, String subtitle, PageTO pages) {

		ArrayList<BoardTO> datas = new ArrayList<BoardTO>();

		String sql = "select * from board where category_seq=? and sub_title=? order by seq desc limit ?, ?";
		Object[] args = {category, subtitle, (pages.getCpage() - 1) * pages.getRecordPerPage(), pages.getRecordPerPage()};
		datas = (ArrayList<BoardTO>)jdbcTemplate.query(sql, args, boardRowMapper);
		
		return datas;
	}
		
	//날짜검색
	public ArrayList<BoardTO> dateSearch(String startDate , String endDate, PageTO pages){

		ArrayList<BoardTO> datas = new ArrayList<BoardTO>();

		String sql = "select * from board where category_seq='7' and created_at between ? and ? order by seq desc limit ?, ?";
		Object[] args = {startDate, endDate, (pages.getCpage() - 1) * pages.getRecordPerPage(), pages.getRecordPerPage()};
		datas = (ArrayList<BoardTO>)jdbcTemplate.query(sql, args, boardRowMapper);

		return datas;
			
	}
		
	//수정
	public int modifyOk(BoardTO to) {

		int flag = 2;
		
		String sql = "update board set title=?, sub_title=?, family_member_type=?,"
				+ " content=?, updated_at=now(),is_private=? where seq=?";

		Object[] args = {to.getTitle(), to.getSubTitle(), to.getFamilyMemberType(), to.getContent(),
				to.isPrivate(), to.getSeq()};

		flag = jdbcTemplate.update(sql, args);

		return flag;
	}
		
	//삭제
	public int deleteOk(BoardTO to) {
			
		int flag = 2; //데이터 오류

		String sql = "delete from board where seq=?";
		flag = jdbcTemplate.update(sql, to.getSeq());
			
		return flag; 
			
	}
	
}
