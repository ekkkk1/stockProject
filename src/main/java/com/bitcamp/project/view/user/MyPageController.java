package com.bitcamp.project.view.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.bitcamp.project.service.MyPostService;
import com.bitcamp.project.service.UserInfoService;
import com.bitcamp.project.vo.BoardVO;
import com.bitcamp.project.vo.CommentVO;
import com.bitcamp.project.vo.UserVO;

@Controller
public class MyPageController {
	
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private MyPostService myPostService;
	
	@GetMapping(value="/myPage01")
	public String myPage01() {
		return "mypage01";
	}
	
	@GetMapping(value="/myPage02")
	public String myPage02() {
		return "mypage02";
	}

	@GetMapping(value="/myPage03")
	public String myPage03(HttpSession session) {
		UserVO loginUser = (UserVO)session.getAttribute("loginUser");
		Map<String, Object> myPost = myPostService.myPostList(loginUser);
		if(myPost.get("myBoard")!=null)
			session.setAttribute("myBoard", (List<BoardVO>)myPost.get("myBoard"));
		if(myPost.get("myComment")!=null)
			session.setAttribute("myComment",(List<CommentVO>)myPost.get("myComment"));
		return "mypage03";
	}
	
	@PostMapping(value="/updateUser")
	public String updateUser(@ModelAttribute("pw") String pw, @ModelAttribute("address") String address,
							@ModelAttribute("tel") String tel, @ModelAttribute("showEsetSetting") String showEset, HttpSession session) {
		UserVO loginUser = (UserVO)session.getAttribute("loginUser");
		loginUser.setPw(pw);
		loginUser.setAddress(address);
		loginUser.setTel(tel);
		loginUser.setShowEsetSetting(Integer.parseInt(showEset));
		userInfoService.memberInfoUpdate(loginUser);
		session.setAttribute("loginUser", loginUser);
		return "mypage01";
	}
	
}
