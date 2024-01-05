package NET;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerNet {
	
	public static void main(String[] args) {
	ServerSocket server= null;
	PrintWriter printWriter =null;
	Socket socket = null;
	Scanner sc = null;
	
	try { 
		server = new ServerSocket(5001);
		System.out.println("서버 시작 : " + server);
		System.out.println("클라이언트 연결 대기중");
		
		socket = server.accept();
		System.out.println("클라이언트 접속 : " + socket);
		
		InputStream is = socket.getInputStream();
		InputStreamReader reader = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(reader);
		String result = br.readLine();
		System.out.println(result);
		
		printWriter = new PrintWriter(socket.getOutputStream());
		printWriter.write("클라이언트 연결\n");
		printWriter.flush();
		
		sc.close();
		
	} catch (IOException e) {
		e.printStackTrace();
		
	}finally {
			if(server !=null)
				try {
					server.close();
				} catch (IOException e) {}
			if(server !=null)
				try {
					socket.close();
				} catch (IOException e) {}
			
	}
	
	
	}
}
