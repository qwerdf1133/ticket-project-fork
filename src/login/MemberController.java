package login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class MemberController implements Initializable {

	@FXML
	private TextField txtId, txtName, txtPw, txtRe, txtPhone;
	@FXML
	private Button btnJoin, buttonGo;
	@FXML
	private CheckBox check;

	MyDB db;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		db = new MyDB();
		System.out.println("호출시 자동 실행");
		btnJoin.setOnAction(e -> join());

        buttonGo.setOnAction(e->{
			
			try {
				// 회원 페이지 이동
				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				stage.setScene(scene);
				stage.setTitle("Join page");
				stage.show();
				
				// Member Stage close
				Stage memberStage = (Stage)btnJoin.getScene().getWindow();
				memberStage.close();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		});

	}
		
	

	public void join() {

		String id = txtId.getText();
		String name = txtName.getText();
		String pw = txtPw.getText();
		String rPw = txtRe.getText();
		String phone =txtPhone.getText();
		
		if(id.length() == 0) {
			alert("아이디를 입력해주세요.");
			// 빈문자열
			txtId.requestFocus();
			return;
		}
		if(name.length() == 0) {
			alert("이름을 입력해주세요.");
			txtName.requestFocus();
			return;
		}
		if(pw.length() == 0) {
			alert("비밀번호를 입력해주세요.");
			txtPw.requestFocus();
			return;
		}
		if(rPw.length() == 0) {
			alert("비밃번호를 확인해주세요.");
			txtRe.requestFocus();
			return;
		}
		if(phone.length() == 0) {
			alert("전화번호를 입력해주세요.");
			txtPhone.requestFocus();
			return;
		}

		// 아이디가 존재하거나 비밀번호가 일치하지 않음
		for (Member_DO member : db.memberList) {
			if (member.getmId().equals(id)) {
				System.out.println("이미 사용중인 아이디입니다.");
				alert("이미 사용중인 아이디입니다.");
				return;
			}
		}

		if (!rPw.equals(pw) ) {
			System.out.println("비밀번호가 일치하지 않습니다.");
			alert("비밀번호가 일치하지 않습니다.");
			return;
		}

		Member_DO m = new Member_DO(id, name, pw, phone);
		db.memberList.add(m);
		

		//db.memberList.put(id, name, pw, phone);
		System.out.println(m);
		System.out.println("회원가입 완료");
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText("회원가입 완료");
		alert.showAndWait();
		
	
		// 화면 전환 -> 로그인
		
		
	}
	
	
	public void alert(String text){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.showAndWait();
	}

}
