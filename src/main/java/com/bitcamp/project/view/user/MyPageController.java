package com.bitcamp.project.view.user;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bitcamp.project.service.MyAccountService;
import com.bitcamp.project.service.MyPostService;
import com.bitcamp.project.service.SignInService;
import com.bitcamp.project.service.UserInfoService;
import com.bitcamp.project.vo.BoardVO;
import com.bitcamp.project.vo.CommentVO;
import com.bitcamp.project.vo.HoldingStockVO;
import com.bitcamp.project.vo.PagingVO;
import com.bitcamp.project.vo.StockVO;
import com.bitcamp.project.vo.UserVO;

@Controller
public class MyPageController {

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private MyPostService myPostService;
	@Autowired
	private MyAccountService myAccountService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	BCryptPasswordEncoder bPasswordEncoder;

	// ㅇㅇㅇㅇㅇㅇㅇ
	@Autowired
	SignInService signInService;

	@GetMapping(value = "/withdrawal/check")
	public String withdrawal_check(@ModelAttribute("id") String id) {
		if (id.substring(id.length() - 1).equals("_"))
			return "redirect:/withdrawal";
		return "/withdrawal_PW";
	}
	
	@GetMapping(value = "/withdrawal")
	public String withdrawal(HttpSession session, @ModelAttribute("id") String id) {
//		String ID = ((UserVO)session.getAttribute("loginUser")).getId();

		if (id.substring(id.length() - 1).equals("_")) { // 끝글자 _ 확인

			String[] ID_s = id.split("_");

			switch (ID_s[ID_s.length - 1]) {
			case "kakao":
				System.out.println("카카오 연결끊기");
				signInService.withdrawal_Kakao((String) session.getAttribute("access_Token")); // 카카오 연결 해제 완료
				signInService.withdrawal(id); // id로 서비스 회원탈퇴
				session.invalidate();
				return "redirect:/mainPage";

			case "naver":
				System.out.println("네이버 연결끊기");
				session.invalidate();
				return "redirect:/mainPage";

			default:
				signInService.withdrawal(id); // id로 서비스 회원탈퇴
				session.invalidate();
				return "redirect:/mainPage";
			}

		}
		signInService.withdrawal(id); // id로 서비스 회원탈퇴
		session.invalidate();
		return "redirect:/mainPage";
	}

	@GetMapping(value = "/checkCharging")
	@ResponseBody
	public int checkCharging(HttpSession session) {
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		return userInfoService.checkCharging(loginUser.getId());
	}

	@GetMapping(value = "/myPage02")
	public String myPage02(HttpSession session, Model model, @ModelAttribute("nowPage1") String nowPage1/* 계좌용 */,
			@ModelAttribute("nowPage2") String nowPage2/* 날짜별 */, @ModelAttribute("nowPage3") String nowPage3/* 종류별 */,
			@ModelAttribute("accountSearch") String accountSearch, @ModelAttribute("tradeSearch") String tradeSearch,
			@ModelAttribute("startDate") String startDate, @ModelAttribute("endDate") String endDate,
			@ModelAttribute("type") String type) {
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
			loginUser = signInService.logIn(loginUser);
			session.setAttribute("loginUser", loginUser);
		
		
		if (type.equals(""))
			type = "rate";
		if (nowPage1.equals(""))
			nowPage1 = "1";
		if (nowPage2.equals(""))
			nowPage2 = "1";
		if (nowPage3.equals(""))
			nowPage3 = "1";
		HashMap<String, Object> hm1 = myAccountService.getMyStockList(loginUser, Integer.parseInt(nowPage1),
				accountSearch);
		HashMap<String, Object> hm2 = myAccountService.getMyTradeHistoryListByDate(loginUser,
				Integer.parseInt(nowPage2), startDate, endDate, tradeSearch);
//	   HashMap<String, Object> hm3 = myAccountService.getMyTradeHistoryListByStock(loginUser, Integer.parseInt(nowPage3), tradeSearch);
		HashMap<String, Object> hm4 = userInfoService.getRate(loginUser.getId());
		session.setAttribute("pv1", (PagingVO) hm1.get("pv1"));
		session.setAttribute("holdingStockList", (List<HoldingStockVO>) hm1.get("holdingStockList"));
		session.setAttribute("pv2", (PagingVO) hm2.get("pv2"));
		session.setAttribute("stockHistoryList", (List<StockVO>) hm2.get("stockHistoryList"));
//	   session.setAttribute("pv3", (PagingVO)hm3.get("pv3"));
//	   session.setAttribute("stockHistoryListByStock", (List<StockVO>)hm3.get("stockHistoryListByStock"));
		session.setAttribute("accuntSearch", accountSearch);
		session.setAttribute("tradeSearch", tradeSearch);
		session.setAttribute("startDate", startDate);
		session.setAttribute("endDate", endDate);
		model.addAttribute("type", type);
		session.setAttribute("accumAsset", hm4.get("accumAsset"));
		session.setAttribute("ranking", hm4.get("ranking"));

		return "mypage02";
	}

	@GetMapping(value = "/myPagePwCheck")
	public String myPageCheck(HttpSession session) {
		if (((UserVO) session.getAttribute("loginUser")).getId()
				.substring(((UserVO) session.getAttribute("loginUser")).getId().length() - 1).equals("_")) {
			return "redirect:/myPage01";
		} else {
			return "myPageCheckPw";
		}
	}

	@ResponseBody
	@PostMapping(value = "/myPagePwCheck")
	public String myPageCheckPost(@ModelAttribute("password") String password, HttpSession session) {
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (bPasswordEncoder.matches(password, loginUser.getPw())) {
			return Integer.toString(1);
		} else {
			return Integer.toString(0);
		}
	}

	@GetMapping(value = "/myPage01")
	public String myPage01(@ModelAttribute("nowPage1") String nowPage1/* 계좌용 */,
			@ModelAttribute("nowPage2") String nowPage2/* 날짜별 */, @ModelAttribute("nowPage3") String nowPage3/* 종류별 */,
			@ModelAttribute("accountSearch") String accountSearch, @ModelAttribute("tradeSearch") String tradeSearch,
			@ModelAttribute("startDate") String startDate, @ModelAttribute("endDate") String endDate) {
		if (nowPage1.equals(""))
			nowPage1 = "1";
		if (nowPage2.equals(""))
			nowPage2 = "1";
		if (nowPage3.equals(""))
			nowPage3 = "1";
		PagingVO pv1 = new PagingVO();
		PagingVO pv2 = new PagingVO();
		PagingVO pv3 = new PagingVO();

		return "mypage01";
	}

	@GetMapping(value = "/myPage03")
	public String myPage03(Model model, HttpSession session, @ModelAttribute("bnowPage") String bnowPage,
			@ModelAttribute("cnowPage") String cnowPage, @ModelAttribute("bSearchStyle") String bSearchStyle,
			@ModelAttribute("boardKeyword") String boardKeyword,
			@ModelAttribute("commentKeyword") String commentKeyword, @ModelAttribute("type") String type) {
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null)
			return "redirect:/mainPage";
		if (bnowPage == null || bnowPage.equals("")) {
			bnowPage = "1";
		}
		if (cnowPage == null || cnowPage.equals("")) {
			cnowPage = "1";
		}
		if (bSearchStyle.equals(""))
			boardKeyword = "";
		if(type.equals(""))
			type = "board";
		Map<String, Object> myPost = myPostService.myPostList(loginUser, Integer.parseInt(bnowPage),
				Integer.parseInt(cnowPage), bSearchStyle, boardKeyword, commentKeyword);
		session.setAttribute("myBoard", (List<BoardVO>) myPost.get("myBoard"));
		session.setAttribute("myComment", (List<CommentVO>) myPost.get("myComment"));
		session.setAttribute("boardPage", (PagingVO) myPost.get("boardPage"));
		session.setAttribute("commentPage", (PagingVO) myPost.get("commentPage"));
		session.setAttribute("bSearchStyle", bSearchStyle);
		session.setAttribute("boardKeyword", boardKeyword);
		session.setAttribute("commentKeyword", commentKeyword);
		model.addAttribute("type", type);
		return "mypage03";
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/myPage04")
	public ModelAndView myPage04(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String id = null;
		try {
			id = ((UserVO) session.getAttribute("loginUser")).getId();
		} catch (Exception e) {
			mav.addObject("msg", "회원만 사용가능합니다");
			mav.addObject("location", "/signInPage");
			mav.setViewName("notice");
			return mav;
		}
		DecimalFormat formatter = new DecimalFormat("###,###,###");

		List<Map> notice = userInfoService.getNotice(id);
		List<Map> tradeNotice = (List) notice.get(0);
		List<Map> commentNotice = (List) notice.get(1);
		List<Map> modifiedNotice = new ArrayList<>();

		try {
			for (int i = 0; i < tradeNotice.size(); i++) {
				tradeNotice.get(i).put("tdatetime",
						new Date(((Date) tradeNotice.get(i).get("tdatetime")).getTime() - (1000 * 60 * 60 * 9)));
				if (tradeNotice.get(i).get("category").equals("buy")) {
					tradeNotice.get(i).put("category", "매수 완료");
				} else if (tradeNotice.get(i).get("category").equals("sell")) {
					tradeNotice.get(i).put("category", "매도 완료");
				}
				tradeNotice.get(i).put("tprice", formatter.format(tradeNotice.get(i).get("tprice")));
				modifiedNotice.add(tradeNotice.get(i));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		mav.addObject("commentNotice", commentNotice);
		mav.addObject("modifiedNotice", modifiedNotice);
		mav.setViewName("mypage04");

		userInfoService.deleteNotice(id);

		return mav;
	}

	@PostMapping(value = "/updateUser")
	public String updateUser(@ModelAttribute("nickname") String nickname, @ModelAttribute("address") String address,
			@ModelAttribute("showEsetSetting") String showEset, HttpSession session) {
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		loginUser.setNickname(nickname);
		loginUser.setAddress(address);
		loginUser.setShowEsetSetting(Integer.parseInt(showEset));
		userInfoService.memberInfoUpdate(loginUser);
		session.setAttribute("loginUser", loginUser);
		return "redirect:/myPage01";
	}

	@GetMapping(value = "/mypageUpdatePassword")
	public String mypageUpdatePasswordView() {
		return "mypage-update-password";
	}

	@GetMapping(value = "/mypageUpdatePasswordCheck")
	@ResponseBody
	public int mypageUpdatePasswordCheck(@ModelAttribute("nowPassword") String nowPassword, HttpSession session,
			HttpServletRequest request, @ModelAttribute("password") String password) {

		Map<String, String> map = new HashMap<String, String>();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		if (bPasswordEncoder.matches(nowPassword, loginUser.getPw())) {
			String encPassword = passwordEncoder.encode(password);
			loginUser.setPw(encPassword);
			userInfoService.updatePassword(loginUser);

			session.setAttribute("loginUser", loginUser);
			return 1;
		} else {
			return 0;
		}
	}

	@GetMapping(value = "/delete/*")
	public String deleteMyPost(@ModelAttribute("delBoardList") String delBoardList,
			@ModelAttribute("delCommentList") String delCommentList, HttpServletRequest request) {
		System.out.println("delList = " + delBoardList);
		System.out.println("delList = " + delCommentList);
		String[] deleted;
		if (request.getRequestURI().equals("/delete/myBoard")) {
			deleted = delBoardList.split(",");
			System.out.println("del leng" + deleted.length);
			myPostService.deleteMyPost(deleted, "board");
			return "redirect:/myPage03";
		} else {
			System.out.println("comment");
			deleted = delCommentList.split(",");
			myPostService.deleteMyPost(deleted, "comment");
			return "redirect:/myPage03";
		}
	}

	@RequestMapping("/notice/json")
	public @ResponseBody String notice(HttpSession session) {
		String id = null;
		try {
			id = ((UserVO) session.getAttribute("loginUser")).getId();
		} catch (Exception e) {
			return null;
		}

//		JSONObject obj = new JSONObject();
		List<List> notice = userInfoService.getNotice(id);
		if ((notice.get(0).size() == 0) && (notice.get(1).size() == 0))
			return "NONE";
		else
			return "NOTICE";
	}

}
