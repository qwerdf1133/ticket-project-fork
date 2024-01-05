package login;

import java.util.ArrayList;
import java.util.List;

public class MyDB {

	public List<Member_DO> memberList;
	
	public MyDB() {
		memberList = new ArrayList<>();
		
		memberList.add(new Member_DO("bobo17502", "33332222", "정순구", "010-4143-0555"));
		memberList.add(new Member_DO("horang000", "00001234", "정호랑", "010-5678-0222"));
		memberList.add(new Member_DO("nunu01", "nn447890", "정호두", "010-3434-0999"));
		memberList.add(new Member_DO("primary01", "1234qwer", "정국수", "010-2323-0666"));
		memberList.add(new Member_DO("gogogo99", "0990jjkk", "정망고", "010-3456-0111"));
		memberList.add(new Member_DO("nanaball", "5555678", "정루루", "010-8282-8888"));
		memberList.add(new Member_DO("eaaaaaan", "22220000", "정이안", "010-4477-7777"));
	}
}
