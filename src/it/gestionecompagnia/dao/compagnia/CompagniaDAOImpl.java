package it.gestionecompagnia.dao.compagnia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gestionecompagnia.dao.AbstractMySQLDAO;
import it.gestionecompagnia.model.Compagnia;

public class CompagniaDAOImpl extends AbstractMySQLDAO implements CompagniaDAO {

	public CompagniaDAOImpl(Connection connection) {
		super(connection);
	}

	@Override
	public List<Compagnia> list() throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Compagnia> result = new ArrayList<Compagnia>();
		Compagnia compagniaTemp = null;

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from compagnia")) {

			while (rs.next()) {
				compagniaTemp = new Compagnia();
				compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
				compagniaTemp.setFatturatoAnnuo(rs.getLong("fatturato"));
				compagniaTemp.setDataFondazione(rs.getDate("datafondazione"));
				compagniaTemp.setId(rs.getLong("ID"));
				result.add(compagniaTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Compagnia get(Long idInput) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Compagnia result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from compagnia where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Compagnia();
					result.setRagioneSociale(rs.getString("ragionesociale"));
					result.setFatturatoAnnuo(rs.getLong("fatturato"));
					result.setDataFondazione(rs.getDate("datafondazione"));
					result.setId(rs.getLong("ID"));
				} else {
					result = null;
				}
			} // niente catch qui

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int update(Compagnia input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection
				.prepareStatement("UPDATE compagnia SET ragionesociale=?, fatturato=?, datafondazione=? where id=?;")) {
			ps.setString(1, input.getRagioneSociale());
			ps.setLong(2, input.getFatturatoAnnuo());
			// quando si fa il setDate serve un tipo java.sql.Date
			ps.setDate(3, new java.sql.Date(input.getDataFondazione().getTime()));
			ps.setLong(4, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Compagnia input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO compagnia (ragionesociale, fatturato, datafondazione) VALUES (?, ?, ?);")) {
			ps.setString(1, input.getRagioneSociale());
			ps.setLong(2, input.getFatturatoAnnuo());

			// quando si fa il setDate serve un tipo java.sql.Date
			ps.setDate(3, new java.sql.Date(input.getDataFondazione().getTime()));
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Compagnia input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM compagnia WHERE ID=?")) {
			ps.setLong(1, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Compagnia> findByExample(Compagnia input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Compagnia> result = new ArrayList<Compagnia>();
		Compagnia compagniaTemp = null;

		String query = "select * from compagnia where 1=1 ";
		if (input.getRagioneSociale() != null && !input.getRagioneSociale().isEmpty()) {
			query += " and ragionesociale like '" + input.getRagioneSociale() + "%' ";
		}
		if (input.getFatturatoAnnuo() != null) {
			query += " and fatturato like '" + input.getFatturatoAnnuo() + "%' ";
		}

		if (input.getDataFondazione() != null) {
			query += " and datafondazione='" + new java.sql.Date(input.getDataFondazione().getTime()) + "' ";
		}

		try (Statement ps = connection.createStatement()) {
			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {
				compagniaTemp = new Compagnia();
				compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
				compagniaTemp.setFatturatoAnnuo(rs.getLong("fatturato"));
				compagniaTemp.setDataFondazione(rs.getDate("datafondazione"));
				compagniaTemp.setId(rs.getLong("ID"));
				result.add(compagniaTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	};

	public List<Compagnia> findAllByDataAssunzioneMaggioreDi(Date dataInput) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Compagnia> result = new ArrayList<Compagnia>();
		Compagnia compagniaTemp = null;

		try (PreparedStatement ps = connection.prepareStatement(
				"select distinct (c.id), c.ragionesociale, c.fatturato, c.datafondazione from compagnia c inner join impiegato i on c.id = i.compagnia_id where i.dataassunzione >= ?")) {
			{
				ps.setDate(1, new java.sql.Date(dataInput.getTime()));
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						compagniaTemp = new Compagnia();
						compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
						compagniaTemp.setFatturatoAnnuo(rs.getLong("fatturato"));
						compagniaTemp.setDataFondazione(rs.getDate("datafondazione"));
						compagniaTemp.setId(rs.getLong("ID"));
						result.add(compagniaTemp);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public List<Compagnia> findAllByRagioneSocialeContiene(String input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Compagnia> result = new ArrayList<Compagnia>();
		Compagnia compagniaTemp = null;

		try (PreparedStatement ps = connection
				.prepareStatement("select * from compagnia where ragionesociale like ?")) {
			{
				ps.setString(1, "%" + input + "%");
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						compagniaTemp = new Compagnia();
						compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
						compagniaTemp.setFatturatoAnnuo(rs.getLong("fatturato"));
						compagniaTemp.setDataFondazione(rs.getDate("datafondazione"));
						compagniaTemp.setId(rs.getLong("ID"));
						result.add(compagniaTemp);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	};

	public List<Compagnia> findAllByCodFisImpiegatoContiene(String input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Compagnia> result = new ArrayList<Compagnia>();
		Compagnia compagniaTemp = null;

		try (PreparedStatement ps = connection.prepareStatement(
				"select distinct (c.id), c.ragionesociale, c.fatturato, c.datafondazione from compagnia c inner join impiegato i on c.id = i.compagnia_id where i.codicefiscale like ?")) {
			{
				ps.setString(1, "%" + input + "%");
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						compagniaTemp = new Compagnia();
						compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
						compagniaTemp.setFatturatoAnnuo(rs.getLong("fatturato"));
						compagniaTemp.setDataFondazione(rs.getDate("datafondazione"));
						compagniaTemp.setId(rs.getLong("ID"));
						result.add(compagniaTemp);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
}