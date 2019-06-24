package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries(Map<Integer, Country> cmap) {
		String sql = "SELECT ccode, StateAbb, StateNme "
				+ "FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Country ctemp = new Country(rs.getInt("ccode"), 
						rs.getString("StateAbb"), 
						rs.getString("StateNme"));
				result.add(ctemp);
				cmap.put(ctemp.getCCode(), ctemp);
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno, Map<Integer, Country> cmap) {
		String sql = "SELECT c.state1no AS id1, c.state2no AS id2 " + 
				"FROM contiguity c " + 
				"WHERE c.year <= ? " + 
				"AND c.conttype = 1";
		List<Border> result = new ArrayList<Border>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			while (rs.next())
				result.add(new Border(cmap.get(rs.getInt("id1")),
						cmap.get(rs.getInt("id2"))));
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
}
