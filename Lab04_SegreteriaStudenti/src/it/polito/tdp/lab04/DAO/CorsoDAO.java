package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {

	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * "+
						   "FROM corso";
		
		String jdbcUrl = "jdbc:mysql://localhost/iscritticorsi?user=root";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = DriverManager.getConnection(jdbcUrl);
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				// Crea un nuovo JAVA Bean Corso
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				// Aggiungi il nuovo Corso alla lista
				corsi.add(c);
			}

			conn.close();
			
			return corsi;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(String codins) {
		// TODO
		final String sql = "SELECT codins, crediti, nome, pd "+
							"FROM corso "+
							"WHERE codins=?";
		
		//String jdbcUrl = "jdbc:mysql://localhost/iscritticorsi?user=root";
		
		Corso result = null;

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, codins);

			ResultSet rs = st.executeQuery();

			if(rs.next()){
				Corso corso = new Corso (rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				result = corso;
			}
			else
				return null;
			
			conn.close();
			
			return result;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Integer> getStudentiIscrittiAlCorso(Corso corso) {
		// TODO
		final String sql = "SELECT matricola "+
				"FROM iscrizione "+
				"WHERE codins=? ";
		
		String jdbcUrl = "jdbc:mysql://localhost/iscritticorsi?user=root";

		List<Integer> matricoleStudentiIscritti = new LinkedList<Integer>();

		try {
			Connection conn = DriverManager.getConnection(jdbcUrl);
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, corso.getCodins());

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Integer i = new Integer(rs.getString("matricola"));
				matricoleStudentiIscritti.add(i);
			}

			conn.close();

			return matricoleStudentiIscritti;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}
	
	public boolean checkIscrizione(Corso corso, String studente){
		final String sql = "SELECT * "+
						"FROM iscrizione "+
						"WHERE matricola=? AND codins=?";
		
		String jdbcUrl = "jdbc:mysql://localhost/iscritticorsi?user=root";
		
		boolean iscritto = false;
		
		try {
			Connection conn = DriverManager.getConnection(jdbcUrl);
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, studente);
			st.setString(2, corso.getCodins());

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				iscritto = true;
			}

			conn.close();

			return iscritto;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Data una matricola ed il codice insegnamento,
	 * iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		// TODO
		final String sql = "INSERT INTO 'iscritticorsi', 'iscrizione' ('matricola', 'codins')";

		String jdbcUrl = "jdbc:mysql://localhost/iscritticorsi?user=root";

		try {
			Connection conn = DriverManager.getConnection(jdbcUrl);
			PreparedStatement st = conn.prepareStatement(sql);
	
			st.setString(1, Integer.toString(studente.getMatricola()));
			st.setString(2, corso.getCodins());

			int result = st.executeUpdate();
			
			conn.close();
			
			if (result==1){
				return true;
			}
			else
				return false;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
}
