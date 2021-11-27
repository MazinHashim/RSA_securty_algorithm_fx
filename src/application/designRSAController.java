package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class designRSAController implements Initializable{
	
	private double x,y;
	private byte[] encrypted,decrypted;
	private RSA rsa;
	
    @FXML
    private JFXTextField encrytext,decrytext;

    @FXML
    private JFXTextArea encryArea,decryArea;
	
    @FXML
    private AnchorPane enPane,dePane;
    
    @FXML
    private void encryptionPane(ActionEvent event) {
    	visibility(enPane, dePane);
    }
    
    @FXML
    private void decryptionPane(ActionEvent event) {
    	visibility(dePane, enPane);
    }
    @FXML
    private void encryptionAction(ActionEvent event) {
    	decryArea.setText("");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("C:\\Users\\Mazin\\Desktop"));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("TXT Files","*.txt"));
		File selected = fileChooser.showOpenDialog(null);
		
		String plaintext = readFromFile(selected);
		
		encrypted = rsa.encrypt(plaintext.getBytes());
		
		String encr = new String(encrypted);
		writeInToFile(encr,selected);
		encryArea.setText(encr);
    }
    
    @FXML
    private void decryptionAction(ActionEvent event) {
    	encryArea.setText("");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("C:\\Users\\Mazin\\Desktop"));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("TXT Files","*.txt"));
		File selected = fileChooser.showOpenDialog(null);
		
		//String ciphertext = readFromFile(selected);
		
		decrypted = rsa.decrypt(encrypted);
		
		String decr = new String(decrypted);
		writeInToFile(decr,selected);
		decryArea.setText(decr);
    }
    @FXML
    private void dragged(MouseEvent event) {
    	Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.setX(event.getScreenX()-x);
		stage.setY(event.getScreenY()-y);
    }
    @FXML
    private void pressed(MouseEvent event) {
    	x = event.getSceneX();
		y = event.getSceneY();
    }
    @FXML
    private void closeWindow(ActionEvent event) {
    	Platform.exit();
    	System.exit(0);
    }
    
    public String readFromFile(File name){
		String readed = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(name));
			String line = null;
			StringBuilder strb = new StringBuilder();
			while((line = reader.readLine()) != null){
				strb.append(line);
			}
			readed = strb.toString();
			reader.close();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "The file "+name.getName()+" could not be found","Open File",JOptionPane.ERROR_MESSAGE);
		}
		return readed;
	}
	public void writeInToFile(String text,File name){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(name));
			writer.write(text);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    private void visibility(AnchorPane needed, AnchorPane notneeded) {
		needed.setVisible(true);
		notneeded.setVisible(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rsa = new RSA();
		
	}
}
