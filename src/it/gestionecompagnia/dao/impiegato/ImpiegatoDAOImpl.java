package it.gestionecompagnia.dao.impiegato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gestionecompagnia.dao.AbstractMySQLDAO;
import it.gestionecompagnia.model.Compagnia;
import it.gestionecompagnia.model.Impiegato;

public class ImpiegatoDAOImpl extends AbstractMySQLDAO implements ImpiegatoDAO {

	public ImpiegatoDAOImpl(Connection connection) {
		super(connection);
	}

	@Override
	public List<Impiegato> list() throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Impiegato> result = new ArrayList<Impiegato>();
		Impiegato impiegatoTemp = null;

		try (Statement ps = connection.createStatement();
				ResultSet rs = ps
						.executeQuery("select * from impiegato i inner join compagnia c on i.compagnia_id = c.id")) {

			while (rs.next()) {
				Compagnia compagniaTemp = new Compagnia();
				compagniaTemp.setRagioneSociale(rs.getString("c.ragionesociale"));
				compagniaTemp.setFatturatoAnnuo(rs.getLong("c.fatturato"));
				compagniaTemp.setDataFondazione(rs.getDate("c.datafondazione"));
				compagniaTemp.setId(rs.getLong("c.ID"));

				impiegatoTemp = new Impiegato();
				impiegatoTemp.setNome(rs.getString("i.nome"));
				impiegatoTemp.setCognome(rs.getString("i.cognome"));
				impiegatoTemp.setCodiceFiscale(rs.getString("i.codicefiscale"));
				impiegatoTemp.setId(rs.getLong("ID"));
				impiegatoTemp.setDataDiNascita(rs.getDate("i.datanascita"));
				impiegatoTemp.setDataDiAssunzione(rs.getDate("i.dataassunzione"));
				impiegatoTemp.setCompagniaDiAppartenenza(compagniaTemp);
				result.add(impiegatoTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Impiegato get(Long idInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Impiegato result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from impiegato where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {

					result = new Impiegato();
					result.setNome(rs.getString("nome"));
					result.setCognome(rs.getString("cognome"));
					result.setCodiceFiscale(rs.getString("codicefiscale"));
					result.setId(rs.getLong("ID"));
					result.setDataDiNascita(rs.getDate("datanascita"));
					result.setDataDiAssunzione(rs.getDate("dataassunzione"));

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
	public int update(Impiegato input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() < 1 || input.getCompagniaDiAppartenenza() == null
				|| input.getCompagniaDiAppartenenza().getId() < 1) {
			throw new Exception("Valore di input non ammesso.");
		}

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE impiegato SET nome=?, cognome=?, codicefiscale=?, datanascita=?, dataassunzione=?, compagnia_id=? where id=?;")) {

			ps.setString(1, input.getNome());
			ps.setString(2, input.getCognome());
			ps.setString(3, input.getCodiceFiscale());
			// quando si fa il setDate serve un tipo java.sql.Date
			ps.setDate(4, new java.sql.Date(input.getDataDiNascita().getTime()));
			ps.setDate(5, new java.sql.Date(input.getDataDiAssunzione().getTime()));
			ps.setLong(6, input.getCompagniaDiAppartenenza().getId());
			ps.setLong(7, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Impiegato input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO impiegato (nome, cognome, codicefiscale, datanascita, dataassunzione, compagnia_id) VALUES (?, ?, ?, ?, ?, ?);")) {
			ps.setString(1, input.getNome());
			ps.setString(2, input.getCognome());
			ps.setString(3, input.getCodiceFiscale());
			// quando si fa il setDate serve un tipo java.sql.Date
			ps.setDate(4, new java.sql.Date(input.getDataDiNascita().getTime()));
			ps.setDate(5, new java.sql.Date(input.getDataDiAssunzione().getTime()));
			ps.setLong(6, input.getCompagniaDiAppartenenza().getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Impiegato input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() < 1 || input.getCompagniaDiAppartenenza() == null
				|| input.getCompagniaDiAppartenenza().getId() < 1) {
			throw new Exception("Valore di input non ammesso.");
		}

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM impiegato WHERE ID=?")) {
			ps.setLong(1, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	};

	@Override
	public List<Impiegato> findByExample(Impiegato input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Impiegato> result = new ArrayList<Impiegato>();
		Impiegato impiegatoTemp = null;

		String query = "select * from impiegato where 1=1 ";
		if (input.getNome() != null && !input.getNome().isEmpty()) {
			query += " and nome like '" + input.getNome() + "%' ";
		}
		if (input.getCognome() != null && !input.getCognome().isEmpty()) {
			query += " and cognome like '" + input.getCognome() + "%' ";
		}
		if (input.getCodiceFiscale() != null && !input.getCodiceFiscale().isEmpty()) {
			query += " and codicefiscale like '" + input.getCodiceFiscale() + "%' ";
		}
		if (input.getDataDiNascita() != null) {
			query += " and datanascita='" + new java.sql.Date(input.getDataDiNascita().getTime()) + "' ";
		}
		if (input.getDataDiAssunzione() != null) {
			query += " and dataassunzione='" + new java.sql.Date(input.getDataDiAssunzione().getTime()) + "' ";
		}

		try (Statement ps = connection.createStatement()) {
			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {
				impiegatoTemp = new Impiegato();
				impiegatoTemp.setNome(rs.getString("nome"));
				impiegatoTemp.setCognome(rs.getString("cognome"));
				impiegatoTemp.setCodiceFiscale(rs.getString("codicefiscale"));
				impiegatoTemp.setId(rs.getLong("ID"));
				impiegatoTemp.setDataDiNascita(rs.getDate("datanascita"));
				impiegatoTemp.setDataDiAssunzione(rs.getDate("dataassunzione"));
				result.add(impiegatoTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput) {
		return null;
	};

	public int countByDataFormazioneCompagniaGreaterThan(Date dataInput) {
		return 0;
	};

	public List<Impiegato> findAllByCompagniaConFatturatoMaggioreDi(long fatturatoInput) {
		return null;
	};

	public List<Impiegato> findAllErroriAssunzioni() {
		return null;
	}
}
