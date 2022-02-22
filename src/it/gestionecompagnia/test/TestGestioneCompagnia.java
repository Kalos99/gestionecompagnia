package it.gestionecompagnia.test;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.gestionecompagnia.connection.MyConnection;
import it.gestionecompagnia.dao.Constants;
import it.gestionecompagnia.dao.compagnia.CompagniaDAO;
import it.gestionecompagnia.dao.compagnia.CompagniaDAOImpl;
import it.gestionecompagnia.dao.impiegato.ImpiegatoDAO;
import it.gestionecompagnia.dao.impiegato.ImpiegatoDAOImpl;
import it.gestionecompagnia.model.Compagnia;
import it.gestionecompagnia.model.Impiegato;

public class TestGestioneCompagnia {
	public static void main(String[] args) {
		CompagniaDAO compagniaDAOInstance = null;
		ImpiegatoDAO impiegatoDAOInstance = null;
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			// ecco chi 'inietta' la connection: il chiamante
			compagniaDAOInstance = new CompagniaDAOImpl(connection);
			impiegatoDAOInstance = new ImpiegatoDAOImpl(connection);
			

			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");
			System.out.println("");
			System.out.println("In tabella impiegato ci sono " + compagniaDAOInstance.list().size() + " elementi.");
			System.out.println("");

			testInserimentoCompagnia(compagniaDAOInstance);
			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");
			System.out.println("");
			
			testInserimentoImpiegato(compagniaDAOInstance, impiegatoDAOInstance);
			System.out.println("In tabella impiegato ci sono " +impiegatoDAOInstance.list().size() + " elementi.");
			System.out.println("");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static void testInserimentoCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception{
		System.out.println(".......testInserimentoCompagnia inizio.............");
		System.out.println("");
		Date dataPerInserimento = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2000");
		int quanteCompagnieInserite = compagniaDAOInstance.insert(new Compagnia("Solving Team", (long)7000000, dataPerInserimento));
		if (quanteCompagnieInserite < 1)
			throw new RuntimeException("testInserimentoCompagnia : FAILED");

		System.out.println(".......testInserimentoCompagnia fine: PASSED.............");
		System.out.println("");
	}
	
	private static void testInserimentoImpiegato(CompagniaDAO compagniaDAOInstance, ImpiegatoDAO impiegatoDAOInstance) throws Exception{
		System.out.println(".......testInserimentoImpiegato inizio.............");
		System.out.println("");
		List<Compagnia> elencoCompagniePresenti = compagniaDAOInstance.list();
		if (elencoCompagniePresenti.size() < 1)
			throw new RuntimeException("testInsertArticolo : FAILED, non ci sono compagnie sul DB");

		Compagnia primaCompagniaDellaLista = elencoCompagniePresenti.get(0);
		Date dataPerInserimento1 = new SimpleDateFormat("dd-MM-yyyy").parse("07-08-1985");
		Date dataPerInserimento2 = new SimpleDateFormat("dd-MM-yyyy").parse("08-03-2021");
		int quantiImpiegatiInseriti = impiegatoDAOInstance.insert(new Impiegato("Mario", "Rossi", " RSSMRA85M07I480N", dataPerInserimento1, dataPerInserimento2, primaCompagniaDellaLista));
		if (quantiImpiegatiInseriti < 1)
			throw new RuntimeException("testInserimentoImpiegato : FAILED");

		System.out.println(".......testInserimentoImpiegato fine: PASSED.............");
		System.out.println("");
	}
}
