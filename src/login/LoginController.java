package login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	@FXML
	private TextField txtId, txtPw;
	@FXML
	private Button btnLogin, btnMember;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> {
			btnLogin.requestFocus();
		});

		btnLogin.setOnAction(e -> login());
		
		btnMember.setOnAction(e->{
			
			try {
				// 회원 페이지 이동
				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("Member.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				stage.setScene(scene);
				stage.setTitle("Login page");
				stage.show();
				
				// Login Stage close
				Stage loginStage = (Stage)btnMember.getScene().getWindow();
				loginStage.close();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		});

	}

	public void login() {
		MyDB db = new MyDB();
		if (txtId.getText().isEmpty() || txtPw.getText().isEmpty()) {
			System.out.println("아이디 혹은 비밀번호를 입력하지 않았습니다!");
			txtId.clear();
			txtPw.clear();
			txtId.requestFocus();
			return;
		}

		for (Member_DO m : db.memberList) {
			if (m.getmId().equals(txtId.getText()) && m.getmPw().equals(txtPw.getText())) {
				System.out.println("로그인 성공");
				alert("로그인 성공");
				// page 이동
				try {
					Stage stage = new Stage();
					Parent root;
					root = FXMLLoader.load(getClass().getResource("Resevation.fxml"));
					stage.setScene(new Scene(root));
					stage.setTitle("예약하기");
					stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return;
			}
		}
		alert("로그인 실패");
		// 로그인 실패
		txtId.clear();
		txtPw.clear();
		txtId.requestFocus();
	}
	
	
	public void alert(String text){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.showAndWait();
	}
}






