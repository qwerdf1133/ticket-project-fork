package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Server에서 전달 받은 데이터를 각 무대 별 화면 제어 Controller로 전달하는 router(발송 담당) Class
 * run() - Server Data Receive
 * sendData() - Server Data Send
 * stopClient() - return resources 
 * 
 */
public class MainRouterThread extends Thread {

	public Receivable memberController, loginController, 
					  postController, 
					  reservationController, 
					  payController, payDoneController, payCheckBontroller;

	// Server에서 발신한 내용을 Receive
	@Override
	public void run() {
		
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new InputStreamReader(Main.socket.getInputStream()));
		} catch (IOException e) {
			stopClient();
			return;
		}
		
		while (true) {
			if (isInterrupted()) {
				break;
			}
			try {
				String readMessage = null;
				if((readMessage = reader.readLine()) == null) {
					throw new NullPointerException("Server 연결 끊김");
				}
				System.out.println(readMessage);
				String[] datas = readMessage.split("\\|");
				String order = datas[0];
				// first order| second order|data...
				// order == 0 회원 관련 요청 처리
				if(order.equals("0")) {
					if(datas[1].equals("0")) {
						// 0|0|data...
						// 로그인 관련 요청 처리에 대한 서버의 결과
						loginController.receiveData(readMessage);
					}else {
						// 0|1|data...
						// 회원가입 관련 요청 처리에 대한 서버의 결과
						memberController.receiveData(readMessage);
					}
				}else if(order.equals("1")) {
					// 1|data...
					// 예매 관련 요청 처리에 대한 서버의 결과
					reservationController.receiveData(readMessage);
				}else if(order.equals("2")) {
					// 2|0|data...
					// 결제 관련 요청 처리에 대한 서버의 결과
					if(datas[1].equals("0")) {
						payController.receiveData(readMessage);
					}else {
						// 2|1|data...
						// 결제 관련 완료 요청 처리에 대한 서버의 결과
						payDoneController.receiveData(readMessage);
					}
				}else if(order.equals("3")) {
					// 3|data...
					// 포스트 관련 요청 처리에 대한 서버의 결과
					postController.receiveData(readMessage);
				}
			} catch (Exception e) {
				e.printStackTrace();
				stopClient();
				break;
			}
		} // end receive while
	}

	public void sendData(String text) {
		try {
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(Main.socket.getOutputStream()),true);
			pw.println(text);
		} catch (IOException e) {
			e.printStackTrace();
			stopClient();
		}
	}

	// Client Server와 연결 종료
	public void stopClient() {
		this.interrupt();
		if (Main.socket != null && !Main.socket.isClosed()) {
			try {
				Main.socket.close();
			} catch (IOException e) {}
		}
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText(null);
			alert.setContentText("서버와 연결이 끊겼습니다.");
			alert.showAndWait();
			Platform.exit();
		});
	}
}
