package com.bitcamp.project.service;

import java.util.Map;

import com.bitcamp.project.vo.UserVO;

public interface MyPostService {
	public void myListSearch();
	public void myCommentList();
	public Map<String, Object> myPostList(UserVO loginUser, int nowPage, int cnowPage, String bSearchStyle, String boardKeyword, String commentKeyword);
	public void deleteMyPost(String deleted[], String type);
}
