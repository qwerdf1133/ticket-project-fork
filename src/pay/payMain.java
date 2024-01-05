package pay;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class payMain extends Application {
	
	Stage stage;
	
	@Override
	public void start(Stage stage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("payMain.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setTitle("레미제라블 결제화면");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
			
			// 결제 화면 창에서 닫기 버튼을 눌렀을 때 나오는 얼럿 메소드 호출 
			// 예를 누르면 창 닫기, 아니오를 누르면 그대로 진행
			stage.setOnCloseRequest(event -> {
				event.consume();
				Cancel(stage);
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	// 창 닫기를 눌렀을 때 나오는 얼럿 메소드
	public void Cancel(Stage stage){
	    Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle("결제 창 나가기");				
	    alert.setHeaderText("정말로 결제 창에서 나가시겠습니까?");
	    alert.setContentText("작성한 정보는 저장되지 않습니다.");
	    
	    if(alert.showAndWait().get() == ButtonType.OK) {
	  	  System.out.println("결제 취소");
	  	  stage.close();
	    }else {
	    	System.out.println("결제 진행");
	    }
}
	
}
