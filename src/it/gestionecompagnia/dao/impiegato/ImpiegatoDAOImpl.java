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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Impiegato input) throws Exception {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return 0;
	};

	@Override
	public List<Impiegato> findByExample(Impiegato input) throws Exception {
		return null;
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
