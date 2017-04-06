/**
 * Sample Skeleton for 'SegreteriaStudenti.fxml' Controller Class
 */

package it.polito.tdp.lab04.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaStudentiController {
	
	Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnReset"
    private Button btnReset; // Value injected by FXMLLoader

    @FXML // fx:id="cmbCorsi"
    private ComboBox<Corso> cmbCorsi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaIscrittiCorso"
    private Button btnCercaIscrittiCorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtMatricola"
    private TextField txtMatricola; // Value injected by FXMLLoader

    @FXML // fx:id="btnRadice"
    private Button btnRadice; // Value injected by FXMLLoader

    @FXML // fx:id="txtNome"
    private TextField txtNome; // Value injected by FXMLLoader

    @FXML // fx:id="txtCognome"
    private TextField txtCognome; // Value injected by FXMLLoader
    
    @FXML // fx:id="btnCerca"
    private Button btnCerca; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaCorsi"
    private Button btnCercaCorsi; // Value injected by FXMLLoader

    @FXML // fx:id="btnIscrivi"
    private Button btnIscrivi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void doCerca(ActionEvent event) {
    	Corso corso = cmbCorsi.getValue();
    	String matricola = txtMatricola.getText();
    	for(int i=0; i<matricola.length(); i++){
			char daTestare = matricola.charAt(i);
			if(!Character.isDigit(daTestare)){
				txtResult.setText("Errore: inserire solo caratteri numerici!");
				return;
			}
		}
    	boolean iscritto =model.checkIscrizione(corso, matricola);
    	if(iscritto == true)
    		txtResult.setText("Lo studente è iscritto al corso!");
    	else
    		txtResult.setText("Lo studente non è iscritto al corso!");
    }

    @FXML
    void doCercaCorsi(ActionEvent event) {
    	
    	List <Studente> elencoStudenti = model.tuttiGliStudenti();
    	String matricola = txtMatricola.getText();
    	for(int i=0; i<matricola.length(); i++){
			char daTestare = matricola.charAt(i);
			if(!Character.isDigit(daTestare)){
				txtResult.setText("Errore: inserire solo caratteri numerici!");
				return;
			}
		}
    	boolean trovato=false;
    	for(Studente s : elencoStudenti){
    		if(s.getMatricola()==Integer.parseInt(matricola))
    			trovato=true;
    	}
    	if(trovato==true){
    		List <Corso> corsiStudente = model.corsiStudente(matricola);
    		if(corsiStudente.isEmpty()){
    			txtResult.setText("Studente non iscritto ad alcun corso!");
    			return;
    		}
    		
    		String risultato = "";
    		for(Corso c : corsiStudente){
    			risultato+=c.toString()+"\n";
    		}
    		txtResult.setText(risultato);
    	}
    	else
    		txtResult.setText("Errore: matricola non esistente!");
    	
    }

    @FXML
    void doCercaIscrittiCorso(ActionEvent event) {
    	
    	if(cmbCorsi.getValue()!=null){
    		List <Studente> elencoStudentiIscritti = model.studentiIscrittiAlCorso(cmbCorsi.getValue());
    		if(elencoStudentiIscritti.isEmpty()){
    			txtResult.setText("Nessuno studente iscritto al corso!");
    			return;
    		}
    		String risultato = "";
    		for(Studente s : elencoStudentiIscritti){
    			risultato+=s.toString()+"\n";
    		}
    		txtResult.setText(risultato);
    		
    	}
    	else
    		txtResult.setText("Errore: selezionare un corso!");
    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	List <Studente> elencoStudenti = model.tuttiGliStudenti();
    	Corso corso = cmbCorsi.getValue();
    	String matricola = txtMatricola.getText();
    	Studente studente = null;
    	
    	for(int i=0; i<matricola.length(); i++){
			char daTestare = matricola.charAt(i);
			if(!Character.isDigit(daTestare)){
				txtResult.setText("Errore: inserire solo caratteri numerici!");
				return;
			}
		}
    	
    	boolean trovato=false;
    	for(Studente s : elencoStudenti){
    		if(s.getMatricola()==Integer.parseInt(matricola)){
    			trovato=true;
    			studente=s;
    		}
    	}
    	if(trovato==true){
    		if(model.checkIscrizione(corso, matricola)==false){
    			boolean iscrizione = model.inscriviStudenteACorso(studente, corso);
    			if(iscrizione==true)
    				txtResult.setText("Studente iscritto al corso!");
    		}
    		else
    			txtResult.setText("Studente già iscritto a questo corso!");
    	}
    	else
    		txtResult.setText("Errore: matricola non esistente!");
    }

    @FXML
    void doRadice(ActionEvent event) {
    	List <Studente> elencoStudenti = model.tuttiGliStudenti();
    	
    	for (Studente s : elencoStudenti){
    		String matricola = txtMatricola.getText();
        	for(int i=0; i<matricola.length(); i++){
    			char daTestare = matricola.charAt(i);
    			if(!Character.isDigit(daTestare)){
    				txtResult.setText("Errore: inserire solo caratteri numerici!");
    				return;
    			}
    		}
    		if(s.getMatricola()==Integer.parseInt(matricola)){
    			txtNome.setText(s.getNome());
    			txtCognome.setText(s.getCognome());
    		}
    	}
    
    }

    @FXML
    void doReset(ActionEvent event) {
    	txtMatricola.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	txtResult.clear();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert cmbCorsi != null : "fx:id=\"cmbCorsi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCercaIscrittiCorso != null : "fx:id=\"btnCercaIscrittiCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnRadice != null : "fx:id=\"btnRadice\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        
    }
    
    public void setModel(Model model) {
		this.model = model;
		
		cmbCorsi.getItems().addAll(model.tuttiICorsi());
	}
}
