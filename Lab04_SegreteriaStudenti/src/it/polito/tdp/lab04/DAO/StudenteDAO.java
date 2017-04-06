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

public class StudenteDAO {

	public List<Studente> getTuttiGliStudenti() {

		final String sql = "SELECT * "+
						"FROM studente";
		
		String jdbcUrl = "jdbc:mysql://localhost/iscritticorsi?user=root";

		List<Studente> studenti = new LinkedList<Studente>();

		try {
			Connection conn = DriverManager.getConnection(jdbcUrl);
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Studente s = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
				
				studenti.add(s);
			}

			return studenti;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<String> getCorsiStudente(String studente) {
		// TODO
		final String sql = "SELECT codins "+
				"FROM iscrizione "+
				"WHERE matricola=? ";
		
		String jdbcUrl = "jdbc:mysql://localhost/iscritticorsi?user=root";

		List<String> codiciInsegnamenti = new LinkedList<String>();

		try {
			Connection conn = DriverManager.getConnection(jdbcUrl);
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, studente);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String s = new String(rs.getString("codins"));
				codiciInsegnamenti.add(s);
			}

			conn.close();

			return codiciInsegnamenti;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}

}
