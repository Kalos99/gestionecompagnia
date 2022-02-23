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
			
			testUpdateCompagnia(compagniaDAOInstance);
			testUpdateImpiegato(impiegatoDAOInstance);
			
			testDeleteCompagnia(compagniaDAOInstance);
			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");
			System.out.println("");
			
			testDeleteImpiegato(compagniaDAOInstance, impiegatoDAOInstance);
			System.out.println("In tabella impiegato ci sono " + impiegatoDAOInstance.list().size() + " elementi.");
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
			throw new RuntimeException("testFindByIdImpiegato : FAILED, non ci sono impiegati sul DB");

		Impiegato primoImpiegatoDellaLista = elencoImpiegatiPresenti.get(0);

		Impiegato impiegatoCheRicercoColDAO = impiegatoDAOInstance.get(primoImpiegatoDellaLista.getId());
		if (impiegatoCheRicercoColDAO == null
				|| !impiegatoCheRicercoColDAO.getNome().equals(primoImpiegatoDellaLista.getNome()))
			throw new RuntimeException("testFindByIdImpiegato : FAILED, i nomi non corrispondono");

		System.out.println(".......testFindByIdImpiegato fine: PASSED.............");
		System.out.println("");
	}
	
	private static void testUpdateCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception{
		System.out.println(".......testUpdateCompagnia inizio.............");
		System.out.println("");
		List<Compagnia> compagniePresenti = compagniaDAOInstance.list();
		if (compagniePresenti.size() < 1)
			throw new RuntimeException("testUpdateCompagnia : FAILED, non ci sono compagnie sul DB");

		String nuovaRagioneSociale = "K-Bits";
		Long nuovoFatturatoAnnuo = (long)5500000;
		Date nuovaDataFondazione = new SimpleDateFormat("dd-MM-yyyy").parse("05-05-2000");
		Compagnia compagniaDaAggiornare = compagniePresenti.get(compagniePresenti.size()-1);
		compagniaDaAggiornare.setRagioneSociale(nuovaRagioneSociale);
		compagniaDaAggiornare.setFatturatoAnnuo(nuovoFatturatoAnnuo);
		compagniaDaAggiornare.setDataFondazione(nuovaDataFondazione);

		int quantiAggiornati = compagniaDAOInstance.update(compagniaDaAggiornare);
		System.out.println(quantiAggiornati);
		if (quantiAggiornati != 1)
			throw new RuntimeException("testUpdateCompagnia : FAILED, aggiornamento non riuscito");

		// ricarico articolo dalla base dati per essere sicuro che abbia fatto modifiche
		Compagnia compagniaRicaricata = compagniaDAOInstance.get(compagniaDaAggiornare.getId());
		if (compagniaRicaricata == null)
			throw new RuntimeException("testUpdateCompagnia : FAILED, compagnia non ricaricata");

		if (!compagniaRicaricata.getRagioneSociale().equals(nuovaRagioneSociale)
				|| !compagniaRicaricata.getFatturatoAnnuo().equals(nuovoFatturatoAnnuo))
			throw new RuntimeException("testUpdateCompagnia : FAILED, i nuovi valori non coincidono");

		System.out.println(".......testUpdateCompagnia fine: PASSED.............");
		System.out.println("");
	}
	
	private static void testUpdateImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception{
		System.out.println(".......testUpdateImpiegato inizio.............");
		System.out.println("");
		List<Impiegato> impiegatiPresenti = impiegatoDAOInstance.list();
		if (impiegatiPresenti.size() < 1)
			throw new RuntimeException("testUpdateImpiegato : FAILED, non ci sono impiegati sul DB");

		String nuovoNome = "Giuseppe";
		String nuovoCognome = "Giacomelli";
		String nuovoCodiceFiscale = "GCMGPP90L05C933A";
		Date nuovaDataDiNascita = new SimpleDateFormat("dd-MM-yyyy").parse("05-07-1990");
		Date nuovaDataDiAssunzione = new SimpleDateFormat("dd-MM-yyyy").parse("09-11-2021");
		Impiegato impiegatoDaAggiornare = impiegatiPresenti.get(impiegatiPresenti.size()-1);
		impiegatoDaAggiornare.setNome(nuovoNome);
		impiegatoDaAggiornare.setCognome(nuovoCognome);
		impiegatoDaAggiornare.setCodiceFiscale(nuovoCodiceFiscale);
		impiegatoDaAggiornare.setDataDiNascita(nuovaDataDiNascita);
		impiegatoDaAggiornare.setDataDiAssunzione(nuovaDataDiAssunzione);

		int quantiAggiornati = impiegatoDAOInstance.update(impiegatoDaAggiornare);
		System.out.println(quantiAggiornati);
		if (quantiAggiornati != 1)
			throw new RuntimeException("testUpdateImpiegato : FAILED, aggiornamento non riuscito");

		// ricarico articolo dalla base dati per essere sicuro che abbia fatto modifiche
		Impiegato impiegatoRicaricato = impiegatoDAOInstance.get(impiegatoDaAggiornare.getId());
		if (impiegatoRicaricato == null)
			throw new RuntimeException("testUpdateImpiegato : FAILED, articolo non ricaricato");

		if (!impiegatoRicaricato.getNome().equals(nuovoNome) || !impiegatoRicaricato.getCognome().equals(nuovoCognome) || !impiegatoRicaricato.getCodiceFiscale().equals(nuovoCodiceFiscale))
			throw new RuntimeException("testUpdateImpiegato : FAILED, i nuovi valori non coincidono");

		System.out.println(".......testUpdateImpiegato fine: PASSED.............");
		System.out.println("");
	}
	
	private static void testDeleteCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception{
		System.out.println(".......testDeleteCompagnia inizio.............");
		System.out.println("");
		// me ne creo uno al volo
		int quantiElementiInseriti = compagniaDAOInstance.insert(new Compagnia("Falliti", (long)0, new Date()));
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testDeleteCompagnia : FAILED, compagnia da rimuovere non inserita");

		List<Compagnia> elencoVociPresenti = compagniaDAOInstance.list();
		int numeroElementiPresentiPrimaDellaRimozione = elencoVociPresenti.size();
		if (numeroElementiPresentiPrimaDellaRimozione < 1)
			throw new RuntimeException("testDeleteCompagnia : FAILED, non ci sono voci sul DB");

		Compagnia ultimaDellaLista = elencoVociPresenti.get(numeroElementiPresentiPrimaDellaRimozione - 1);
		compagniaDAOInstance.delete(ultimaDellaLista);

		// ricarico per vedere se sono scalati di una unità
		int numeroElementiPresentiDopoDellaRimozione = compagniaDAOInstance.list().size();
		if (numeroElementiPresentiDopoDellaRimozione != numeroElementiPresentiPrimaDellaRimozione - 1)
			throw new RuntimeException("testDeleteCompagnia : FAILED, la rimozione non è avvenuta");

		System.out.println(".......testDeleteCompagnia fine: PASSED.............");
		System.out.println("");
	}
	
	private static void testDeleteImpiegato(CompagniaDAO compagniaDAOInstance, ImpiegatoDAO impiegatoDAOInstance) throws Exception{
		System.out.println(".......testDeleteCompagnia inizio.............");
		System.out.println("");
		// me ne creo uno al volo
		List<Compagnia> elencoCompagniePresenti = compagniaDAOInstance.list();
		if (elencoCompagniePresenti.size() < 1)
			throw new RuntimeException("testInsertArticolo : FAILED, non ci sono compagnie sul DB");

		Compagnia primaCompagniaDellaLista = elencoCompagniePresenti.get(0);
		int quantiElementiInseriti = impiegatoDAOInstance.insert(new Impiegato("Pippo", "Paperino", "PPRPPP80A01H501V", new Date(), new Date(), primaCompagniaDellaLista));
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testDeleteCompagnia : FAILED, impiegato da rimuovere non inserito");

		List<Impiegato> elencoVociPresenti = impiegatoDAOInstance.list();
		int numeroElementiPresentiPrimaDellaRimozione = elencoVociPresenti.size();
		if (numeroElementiPresentiPrimaDellaRimozione < 1)
			throw new RuntimeException("testDeleteCompagnia : FAILED, non ci sono voci sul DB");

		Impiegato ultimoDellaLista = elencoVociPresenti.get(numeroElementiPresentiPrimaDellaRimozione - 1);
		impiegatoDAOInstance.delete(ultimoDellaLista);

		// ricarico per vedere se sono scalati di una unità
		int numeroElementiPresentiDopoDellaRimozione = impiegatoDAOInstance.list().size();
		if (numeroElementiPresentiDopoDellaRimozione != numeroElementiPresentiPrimaDellaRimozione - 1)
			throw new RuntimeException("testDeleteCompagnia : FAILED, la rimozione non è avvenuta");

		System.out.println(".......testDeleteCompagnia fine: PASSED.............");
		System.out.println("");
	}
}