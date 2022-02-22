package it.gestionecompagnia.dao.impiegato;

import java.util.Date;
import java.util.List;

import it.gestionecompagnia.dao.AbstractMySQLDAO;
import it.gestionecompagnia.model.Compagnia;
import it.gestionecompagnia.model.Impiegato;

public class ImpiegatoDAOImpl extends AbstractMySQLDAO implements ImpiegatoDAO{

	@Override
	public List<Impiegato> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return 0;
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
	
	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput){
		return null;
	};
	
	public int countByDataFormazioneCompagniaGreaterThan(Date dataInput) {
		return 0;
	};
	
	public List<Impiegato> findAllByCompagniaConFatturatoMaggioreDi(long fatturatoInput){
		return null;
	};
	
	public List<Impiegato> findAllErroriAssunzioni(){
		return null;
	}
}
