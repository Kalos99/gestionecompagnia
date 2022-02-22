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
			
			testFindByIdCompagnia(compagniaDAOInstance);
			testFindByIdImpiegato(impiegatoDAOInstance);

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
	
	private static void testFindByIdCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testFindById inizio.............");
		System.out.println("");
		List<Compagnia> elencoVociPresenti = compagniaDAOInstance.list();
		if (elencoVociPresenti.size() < 1)
			throw new RuntimeException("testFindByIdCompagnia : FAILED, non ci sono voci sul DB");

		Compagnia primoDellaLista = elencoVociPresenti.get(0);

		Compagnia elementoCheRicercoColDAO = compagniaDAOInstance.get(primoDellaLista.getId());
		if (elementoCheRicercoColDAO == null || !elementoCheRicercoColDAO.getRagioneSociale().equals(primoDellaLista.getRagioneSociale()))
			throw new RuntimeException("testFindByIdCompagnia : FAILED, le ragioni sociali non corrispondono");

		System.out.println(".......testFindByIdCompagnia fine: PASSED.............");
		System.out.println("");
	}
	
	private static void testFindByIdImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception{
		System.out.println(".......testFindByIdImpiegato inizio.............");
		System.out.println("");
		List<Impiegato> elencoImpiegatiPresenti = impiegatoDAOInstance.list();
		if (elencoImpiegatiPresenti.size() < 1)
			throw new RuntimeException("testFindByIdImpiegato : FAILED, non ci sono articoli sul DB");

		Impiegato primoImpiegatoDellaLista = elencoImpiegatiPresenti.get(0);

		Impiegato impiegatoCheRicercoColDAO = impiegatoDAOInstance.get(primoImpiegatoDellaLista.getId());
		if (impiegatoCheRicercoColDAO == null
				|| !impiegatoCheRicercoColDAO.getNome().equals(primoImpiegatoDellaLista.getNome()))
			throw new RuntimeException("testFindByIdImpiegato : FAILED, i nomi non corrispondono");

		System.out.println(".......testFindByIdImpiegato fine: PASSED.............");
		System.out.println("");
	}
}