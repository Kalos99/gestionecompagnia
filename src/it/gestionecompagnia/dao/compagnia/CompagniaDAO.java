package it.gestionecompagnia.dao.compagnia;

import java.util.Date;
import java.util.List;

import it.gestionecompagnia.dao.IBaseDAO;
import it.gestionecompagnia.model.Compagnia;

public interface CompagniaDAO extends IBaseDAO<Compagnia>{

	public List<Compagnia> findAllByDataAssunzioneMaggioreDi(Date dataInput) throws Exception;

	public List<Compagnia> findAllByRagioneSocialeContiene(String input) throws Exception; 

	public List<Compagnia> findAllByCodFisImpiegatoContiene(String input) throws Exception;

}