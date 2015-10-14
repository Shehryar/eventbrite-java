package demo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.aif.eventbrite.Attendees;
import org.aif.eventbrite.Event;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AIFApplication extends Application {

    private static final String EVENTATTENDEES_URL = "https://www.eventbriteapi.com/v3/events/19005878093/attendees/?token=P64DEPWLQ2UEEEGOY7TL";
	private static final Logger log = LoggerFactory.getLogger(AIFApplication.class);

    public static void main(String args[]) {
    	
    	/*SpringApplication.run(AIFApplication.class);*/
    	
    	launch(args);
    	
    	
    }

    @Override
    public void start(Stage primaryStage) {
    	   	
    	primaryStage.setTitle("Prototype");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);

		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);

		Button btn = new Button("Run");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);

		final Text actiontarget = new Text();
		grid.add(actiontarget, 1, 6);

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				
				/*SpringApplication.run(AIFApplication.class);*/
				
				RestTemplate restTemplate = new RestTemplate();
		        
		        //Get All Attendees
		        Event eventInfoForThursday = restTemplate.getForObject(EVENTATTENDEES_URL, Event.class);
		        
		        //Create Excel File
		        generateExcelFile(eventInfoForThursday);
		        
		        //TODO Send Email
			}
		});

		Scene scene = new Scene(grid, 300, 275);
		primaryStage.setScene(scene);
		primaryStage.show();
    }
    
    /*@Override
    public void run(String... strings) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        
        //Get All Attendees
        Event eventInfoForThursday = restTemplate.getForObject(EVENTATTENDEES_URL, Event.class);
        
        //Create Excel File
        generateExcelFile(eventInfoForThursday);
        
        //TODO Send Email
    }*/

	private void generateExcelFile(Event eventInfoForThursday) {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("Attendees");
		
		//Create Title
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("Name");
		row.createCell(1).setCellValue("Last Name");
		row.createCell(2).setCellValue("First Name");
		row.createCell(3).setCellValue("Email Address");
		row.createCell(4).setCellValue("Status");
		row.createCell(5).setCellValue("CheckedIn?");
		
		//Populate ExcelFile
		int i = 0;
		for(Attendees attendees : eventInfoForThursday.getAttendees()) {
			row = sheet.createRow(++i);
			row.createCell(0).setCellValue(attendees.getProfile().getName());
			row.createCell(1).setCellValue(attendees.getProfile().getFirst_name());
			row.createCell(2).setCellValue(attendees.getProfile().getLast_name());
			row.createCell(3).setCellValue(attendees.getProfile().getEmail());
			row.createCell(4).setCellValue(attendees.getStatus());
			row.createCell(5).setCellValue(attendees.getChecked_in());
		}
		
		//Write to file
		FileOutputStream fileOut = null;
		try {
			String fileName = getFileName();
			fileOut = new FileOutputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
		    wb.write(fileOut);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		try {
		    fileOut.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}

	}

	private String getFileName() {
		StringBuffer fileName = new StringBuffer();
		
		Calendar cal = Calendar.getInstance();
		
		fileName.append("WorkBook_");
		fileName.append(cal.get(Calendar.MONTH)).append("_");
		fileName.append(cal.get(Calendar.DAY_OF_MONTH)).append("_");
		fileName.append(cal.get(Calendar.YEAR)).append("_");
		fileName.append(cal.get(Calendar.HOUR)).append("_");
		fileName.append(cal.get(Calendar.MINUTE)).append("_");
		fileName.append(cal.get(Calendar.SECOND)).append(".xls");

		return fileName.toString();

	}
}