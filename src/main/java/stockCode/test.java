//
//package stockCode;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import com.bitcamp.project.vo.Info;
//
//public class test {
////	public Info parse(String code) {
//	public static void main(String[] args) {
//		
//		//long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기
//		
//		
//		
//		Map<String, Info> info = new HashMap<String, Info>();
//		try {
////			String url = "https://finance.naver.com/item/main.nhn?code=" + code;
//			String url = "https://finance.naver.com/item/main.nhn?code=" + "068270";
//			Document doc = null; // Document에는 페이지의 전체 소스가 저장된다
//			doc = Jsoup.connect(url).get();
//			Elements viewLists = doc.select("#middle");
//			
//			String stockName = null;
//			String currentPrice = null;
//			String before = null;
//			String upDown = null;
//			
//			for (Element viewList : viewLists) {
//				stockName = viewList.selectFirst("strong").text();
//				
//				String currentPriceUp = viewList.select(".no_today").text();
//				String beforeUp = viewList.select(".no_exday").text();
////				String currentPriceDown = viewList.select(".no_today .no_down").text();
////				String beforeDown = viewList.select(".no_exday .no_down").text();
//				String up_c = viewList.select("#tab_con2 .f_down").text();
//				String down_c = viewList.select("#tab_con2 .f_up").text();
//				String[] up_ = up_c.split(" ");
//				String[] down_ = down_c.split(" ");
//				String[] up = new String[10];
//				String[] down = new String[10];
//				System.out.println(Arrays.toString(down_));
//			
//				int n = 0;
//				int n1 = 0;
//					for (int i = 0; i < up_.length-1; i++) {
//						
//						if(i % 2 != 0) {
//							up[n] = up_[i];
//							n++;
//						}
//						if(i % 2 == 0) {
//							down[n] = down_[i];
//							n1++;
//						}
//				}
//				
//				System.out.println(Arrays.toString(up));
//				System.out.println(Arrays.toString(down));
////				System.out.println(currentPriceUp);
////				System.out.println(currentPriceDown);
////				System.out.println(beforeUp);
////				System.out.println(beforeDown);
//				// 상승 하락 보합
//				
//				String[] cPrice = currentPriceUp.split(" ");
//				currentPrice = cPrice[0].replace(",", "");
//				
//				String[] bFUD = beforeUp.split(" ");
//				//System.out.println(Arrays.deepToString(bFUD));
//				
//				before = "+" + bFUD[1];
//				upDown = bFUD[5].replace(".", "") + "%";
//				// 상승인 경우
////				if (currentPriceUp != null) {
////					String[] cPrice = currentPriceUp.split(" ");
////					currentPrice = cPrice[0].replace(",", "");
////
////					String[] bFUD = beforeUp.split(" ");
////					//System.out.println(Arrays.deepToString(bFUD));
////
////					before = "+" + bFUD[1];
////					upDown = bFUD[5].replace(".", "") + "%";
////					
////				// 하락인 경우
////				} else if(currentPriceDown != null) {
////					String[] cPrice = currentPriceDown.split(" ");
////					currentPrice = cPrice[0].replace(",", "");
////
////					String[] bFUD = beforeDown.split(" ");
////					//System.out.println(Arrays.deepToString(bFUD));
////
////					before = "-" + bFUD[1];
////					upDown = bFUD[5].replace(".", "") + "%";
////			
////					if (before.contains(",")) {
////						before = before.replace(",", "");
////					}
////				}
////				
//				
//			}
////
////			System.out.println(stockName);
////			System.out.println(currentPrice);
////			System.out.println(before);
////			System.out.println(upDown);
//			
//			
//			Info inf = new Info();
//			inf.setStockName(stockName);
//			inf.setCurrentPrice(currentPrice);
//			inf.setBefore(before);
//			inf.setUpDown(upDown);
//			
////			System.out.println(inf);
////			long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
////			long secDiffTime = (afterTime - beforeTime)/1000; //두 시간에 차 계산
////			System.out.println("시간차이(m) : "+secDiffTime);
//			
//			
////			return inf;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
////		return null;
//	}
//}
//
////
////package stockCode;
////
////import java.util.Arrays;
////import java.util.HashMap;
////import java.util.Map;
////
////import org.jsoup.Jsoup;
////import org.jsoup.nodes.Document;
////import org.jsoup.nodes.Element;
////import org.jsoup.select.Elements;
////
////public class test {
//////	public Info parse(String code) {
////	public static void main(String[] args) {
////		
////		//long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기
////        
////		        
////		
////		Map<String, Info> info = new HashMap<String, Info>();
////		try {
////			String url = "http://finance.daum.net/api/trend/market_capitalization?page=51&perPage=30";
////
//////			String url = "https://finance.naver.com/item/main.nhn?code=" + "005930";
////			Document doc = null; // Document에는 페이지의 전체 소스가 저장된다
////			doc = Jsoup.connect(url).get();
////			Elements viewLists = doc.select("#middle");
////
////			String stockName = null;
////			String currentPrice = null;
////			String before = null;
////			String upDown = null;
////
////			for (Element viewList : viewLists) {
////				stockName = viewList.selectFirst("strong").text();
////
////				String currentPrice_ = viewList.select(".no_today .no_up").text();
////				String before_ = viewList.select(".no_exday").text();
////
////				String[] beforeAfter = before_.split(" ");
////				
////				System.out.println(currentPrice_);
////				
////				
////				upDown = beforeAfter[6] + "%";
////				if(beforeAfter[1].equals("상승")) {
////
////					before = "+" + beforeAfter[2];
////				}
////				else if(beforeAfter[1].equals("하락")) {
////					before = "-" + beforeAfter[2];
////				}
////				else if(beforeAfter[1].equals("보합")) {
////					before = beforeAfter[2];
////					upDown = beforeAfter[5] + "%";
////				}
////				
////				System.out.println(before);
////				System.out.println(upDown);
////			}
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////	}
////}
////
////
////
////