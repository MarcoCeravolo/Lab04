package it.polito.tdp.lab04.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	CorsoDAO cD;
	StudenteDAO sD;
	
	public Model() {
		super();
		cD = new CorsoDAO();
		sD = new StudenteDAO();
	}
	
	public List<Corso> tuttiICorsi(){
		return cD.getTuttiICorsi();
	}
	
	public List<Studente> tuttiGliStudenti(){
		return sD.getTuttiGliStudenti();
	}
	
	public List<Studente> studentiIscrittiAlCorso(Corso corso){
		ArrayList<Studente> elencoIscritti = new ArrayList<Studente>();
		for(Integer i : cD.getStudentiIscrittiAlCorso(corso)){
			for(Studente s : this.tuttiGliStudenti())
				if(i==s.getMatricola())
					elencoIscritti.add(s);
		}
		return elencoIscritti;
	}
	
	public List<Corso> corsiStudente(String studente){
		ArrayList<Corso> elencoCorsi = new ArrayList<Corso>();
		for(String s : sD.getCorsiStudente(studente)){
			for(Corso c : this.tuttiICorsi())
				if(s.compareTo(c.getCodins())==0)
					elencoCorsi.add(c);
		}
		return elencoCorsi;
	}
	
	public boolean checkIscrizione(Corso corso, String studente){
		return cD.checkIscrizione(corso, studente);
	}
	
	public boolean inscriviStudenteACorso(Studente studente, Corso corso){
		return cD.inscriviStudenteACorso(studente, corso);
	}
}
