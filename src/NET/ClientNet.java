package NET;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientNet {

	public static void main(String[] args) {
		
		Scanner sc = null;
		Socket socket =null;
		PrintWriter printWriter =null;
		
		try {
			
			System.out.println("연결 요청중... ");
			socket = new Socket("10.100.205.177", 5001);
			System.out.println("서버 연결 완료");
			
			printWriter = new PrintWriter(socket.getOutputStream());
			printWriter.write("H e l l o");
			printWriter.flush();
			
			InputStream is = socket.getInputStream();
			InputStreamReader reader = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(reader);
			String result = br.readLine();
			System.out.println(result);
			
		} catch (UnknownHostException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
	
	}

	}
}
	