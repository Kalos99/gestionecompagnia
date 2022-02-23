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
			
			testFindByExampleCompagnia(compagniaDAOInstance);
			testFindByExampleImpiegato(compagniaDAOInstance, impiegatoDAOInstance);
			testFindAllByDataAssunzioneMaggioreDi(compagniaDAOInstance, impiegatoDAOInstance);
			testFindAllByRagioneSocialeContiene(compagniaDAOInstance);
			testFindAllByCodFisImpiegatoContiene(compagniaDAOInstance, impiegatoDAOInstance);
			testFindAllByCompagnia(compagniaDAOInstance, impiegatoDAOInstance);
			testCountByDataFormazioneCompagniaGreaterThan(compagniaDAOInstance, impiegatoDAOInstance);

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
	
	private static void testFindByExampleCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testFindByExampleCompagnia inizio.............");
		System.out.println("");

		Date dataFondazione1 = new SimpleDateFormat("dd-MM-yyyy").parse("16-04-2002");
		Date dataFondazione2 = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2003");

		// me ne creo un paio che fanno al caso mio così almeno due li troverò
		Compagnia nuovaCompagnia = new Compagnia("FataInformatica", (long)6000000, dataFondazione1);
		Compagnia altraNuovaCompagnia = new Compagnia("Solving Squad", (long)7500000, dataFondazione2);

		int quantiElementiInseriti = compagniaDAOInstance.insert(nuovaCompagnia);
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testFindByExampleCompagnia : FAILED, compagnia non inserita");

		quantiElementiInseriti = compagniaDAOInstance.insert(altraNuovaCompagnia);
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testFindByExampleCompagnia : FAILED, compagnia non inserita");
		
		// ora provo ad estrarre quelli che rispattano i parametri dell'example
		List<Compagnia> elencoCompagnieConExample = compagniaDAOInstance.findByExample(altraNuovaCompagnia);
		for (Compagnia compagniaItem : elencoCompagnieConExample) {
			if(!compagniaItem.getRagioneSociale().equals(altraNuovaCompagnia.getRagioneSociale()))
				throw new RuntimeException(
						"testFindByExampleCompagnia : FAILED, le compagnie non rispettano le caratteristiche dell'example"
								+ compagniaItem.getId());
		}

		System.out.println(".......testFindByExampleCompagnia fine: PASSED.............");
		System.out.println("");
	}
	
	private static void testFindByExampleImpiegato(CompagniaDAO compagniaDAOInstance, ImpiegatoDAO impiegatoDAOInstance) throws Exception{
		System.out.println(".......testFindByExampleImpiegato inizio.............");
		System.out.println("");
		
		List<Compagnia> elencoCompagniePresenti = compagniaDAOInstance.list();
		if (elencoCompagniePresenti.size() < 1)
			throw new RuntimeException("testInsertArticolo : FAILED, non ci sono compagnie sul DB");

		Compagnia ultimaCompagniaDellaLista = elencoCompagniePresenti.get(elencoCompagniePresenti.size()-1);

		Date dataNascita = new SimpleDateFormat("dd-MM-yyyy").parse("16-03-1990");
		Date dataAssunzione = new SimpleDateFormat("dd-MM-yyyy").parse("20-01-2022");

		// me ne creo uno che fa al caso mio così da poterne trovare almeno uno
		Impiegato nuovoImpiegato = new Impiegato("Massimo", "Marianella", "MRNMSM90C16H501X", dataNascita, dataAssunzione, ultimaCompagniaDellaLista);

		int quantiElementiInseriti = impiegatoDAOInstance.insert(nuovoImpiegato);
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testFindByExampleImpiegato : FAILED, impiegato non inserito");
		
		// ora provo ad estrarre quelli che rispattano i parametri dell'example
		List<Impiegato> elencoImpiegatiConExample = impiegatoDAOInstance.findByExample(nuovoImpiegato);
		for (Impiegato impiegatoItem : elencoImpiegatiConExample) {
			if(!impiegatoItem.getNome().equals(nuovoImpiegato.getNome()) || !impiegatoItem.getCognome().equals(nuovoImpiegato.getCognome()))
				throw new RuntimeException(
						"testFindByExampleImpiegato : FAILED, gli impiegati non rispettano le caratteristiche dell'example"
								+ nuovoImpiegato.getId());
		}

		System.out.println(".......testFindByExampleImpiegato fine: PASSED.............");
		System.out.println("");
	}
	
	private static void testFindAllByDataAssunzioneMaggioreDi(CompagniaDAO compagniaDAOInstance, ImpiegatoDAO impiegatoDAOInstance) throws Exception {
		System.out.println(".......testFindAllByDataAssunzioneMaggioreDi inizio.............");
		System.out.println("");
		
		Date dataFondazione = new SimpleDateFormat("dd-MM-yyyy").parse("25-01-1995");

		// creo degli elementi che fanno al caso mio così sono certo di avere dei risultati positivi
		Compagnia nuovaCompagnia = new Compagnia("Conrad", (long)10000000, dataFondazione);
		int quantiElementiInseriti = compagniaDAOInstance.insert(nuovaCompagnia);
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testFindAllByDataAssunzioneMaggioreDi : FAILED, compagnia non inserita");
		
		List<Compagnia> elencoCompagniePresenti = compagniaDAOInstance.list();
		Compagnia compagniaRicaricata = elencoCompagniePresenti.get(elencoCompagniePresenti.size()-1);
		if (compagniaRicaricata == null)
			throw new RuntimeException("testFindAllByDataAssunzioneMaggioreDi : FAILED, compagnia non ricaricata");
		
		Date dataNascita = new SimpleDateFormat("dd-MM-yyyy").parse("16-07-1995");
		Date dataAssunzione = new SimpleDateFormat("dd-MM-yyyy").parse("20-02-2022");

		// me ne creo uno che fa al caso mio così da poterne trovare almeno uno
		Impiegato nuovoImpiegato = new Impiegato("Manuela", "Marino", " MRNMNL95L56B486Z", dataNascita, dataAssunzione, compagniaRicaricata);

		int quantiElementiInseriti1 = impiegatoDAOInstance.insert(nuovoImpiegato);
		if (quantiElementiInseriti1 < 1)
			throw new RuntimeException("testFindAllByDataAssunzioneMaggioreDi : FAILED, impiegato non inserito");

		// ora provo ad estrarre tutti quelli che soddisfano la condizione desiderata
		Date dataPerQuery = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2022");
		System.out.println(compagniaDAOInstance.findAllByDataAssunzioneMaggioreDi(dataPerQuery));
//		if(compagniaDAOInstance.findAllByDataAssunzioneMaggioreDi(dataPerQuery) == null) {
//			throw new RuntimeException("testFindAllByDataAssunzioneMaggioreDi : FAILED, ricerca non riuscita");
//		};

		System.out.println(".......testFindAllByDataAssunzioneMaggioreDi fine: PASSED.............");
		System.out.println("");
	}
	
	private static void testFindAllByRagioneSocialeContiene(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testFindAllByRagioneSocialeContiene inizio.............");
		System.out.println("");
		
		Date dataFondazione = new SimpleDateFormat("dd-MM-yyyy").parse("13-07-1991");

		// creo degli elementi che fanno al caso mio così sono certo di avere dei risultati positivi
		Compagnia nuovaCompagnia = new Compagnia("Solving origins", (long)15000000, dataFondazione);
		int quantiElementiInseriti = compagniaDAOInstance.insert(nuovaCompagnia);
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testFindAllByRagioneSocialeContiene : FAILED, compagnia non inserita");

		// ora provo ad estrarre tutti quelli che soddisfano la condizione desiderata
		String stringaPerQuery = "Solving";
		List<Compagnia> compagnieCheSoddisfanoLaCondizione = compagniaDAOInstance.findAllByRagioneSocialeContiene(stringaPerQuery);
		for(Compagnia compagniaItem : compagnieCheSoddisfanoLaCondizione) {
			if(!compagniaItem.getRagioneSociale().contains(stringaPerQuery)) {
				throw new RuntimeException("testFindAllByRagioneSocialeContiene : FAILED, le compagnie trovate non soddisfano la condizione");
			}
		}

		System.out.println(".......testFindAllByRagioneSocialeContiene fine: PASSED.............");
		System.out.println("");
	}
	
	private static void testFindAllByCodFisImpiegatoContiene(CompagniaDAO compagniaDAOInstance, ImpiegatoDAO impiegatoDAOInstance) throws Exception {
		System.out.println(".......testFindAllByCodFisImpiegatoContiene inizio.............");
		System.out.println("");
		
		Date dataFondazione = new SimpleDateFormat("dd-MM-yyyy").parse("25-09-2004");

		// creo degli elementi che fanno al caso mio così sono certo di avere dei risultati positivi
		Compagnia nuovaCompagnia = new Compagnia("Data Engineers", (long)12500000, dataFondazione);
		int quantiElementiInseriti = compagniaDAOInstance.insert(nuovaCompagnia);
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testFindAllByCodFisImpiegatoContiene : FAILED, compagnia non inserita");
		
		List<Compagnia> elencoCompagniePresenti = compagniaDAOInstance.list();
		Compagnia compagniaRicaricata = elencoCompagniePresenti.get(elencoCompagniePresenti.size()-1);
		if (compagniaRicaricata == null)
			throw new RuntimeException("testFindAllByCodFisImpiegatoContiene : FAILED, compagnia non ricaricata");
		
		Date dataNascita = new SimpleDateFormat("dd-MM-yyyy").parse("14-02-1994");
		Date dataAssunzione = new SimpleDateFormat("dd-MM-yyyy").parse("20-12-2018");

		// me ne creo uno che fa al caso mio così da poterne trovare almeno uno
		Impiegato nuovoImpiegato = new Impiegato("Mattia", "Marino", " MRNMTT94B14B486M", dataNascita, dataAssunzione, compagniaRicaricata);

		int quantiElementiInseriti1 = impiegatoDAOInstance.insert(nuovoImpiegato);
		if (quantiElementiInseriti1 < 1)
			throw new RuntimeException("testFindAllByCodFisImpiegatoContiene : FAILED, impiegato non inserito");

		// ora provo ad estrarre tutti quelli che soddisfano la condizione desiderata
		String stringaPerQuery = "MRNM";
		if(compagniaDAOInstance.findAllByCodFisImpiegatoContiene(stringaPerQuery) == null) {
			throw new RuntimeException("testFindAllByCodFisImpiegatoContiene : FAILED, ricerca non riuscita");
		};

		System.out.println(".......testFindAllByCodFisImpiegatoContiene fine: PASSED.............");
		System.out.println("");
	}	
	
	private static void testFindAllByCompagnia(CompagniaDAO compagniaDAOInstance, ImpiegatoDAO impiegatoDAOInstance) throws Exception{
		System.out.println(".......testFindAllByCompagnia inizio.............");
		System.out.println("");
		
		Date dataFondazione = new SimpleDateFormat("dd-MM-yyyy").parse("27-05-2014");
		Compagnia nuovaCompagnia = new Compagnia("Cybertech", (long)4500000, dataFondazione);
		int quantiElementiInseriti = compagniaDAOInstance.insert(nuovaCompagnia);
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testFindAllByCompagnia : FAILED, compagnia non inserita");
		
		List<Compagnia> elencoCompagniePresenti = compagniaDAOInstance.list();
		Compagnia compagniaRicaricata = elencoCompagniePresenti.get(elencoCompagniePresenti.size()-1);
		if (compagniaRicaricata == null)
			throw new RuntimeException("testFindAllByCompagnia : FAILED, compagnia non ricaricata");
		
		Date dataNascita = new SimpleDateFormat("dd-MM-yyyy").parse("21-09-1992");
		Date dataAssunzione = new SimpleDateFormat("dd-MM-yyyy").parse("13-05-2020");

		// me ne creo uno che fa al caso mio così da poterne trovare almeno uno
		Impiegato nuovoImpiegato = new Impiegato("Giuseppe", "Cimino", "CMNGPP92P21H703Q", dataNascita, dataAssunzione, compagniaRicaricata);

		int quantiElementiInseriti1 = impiegatoDAOInstance.insert(nuovoImpiegato);
		if (quantiElementiInseriti1 < 1)
			throw new RuntimeException("testFindAllByCompagnia : FAILED, impiegato non inserito");
		
		Date dataNascita2 = new SimpleDateFormat("dd-MM-yyyy").parse("25-04-1995");
		Date dataAssunzione2 = new SimpleDateFormat("dd-MM-yyyy").parse("14-02-2021");

		// me ne creo uno che fa al caso mio così da poterne trovare almeno uno
		Impiegato altroNuovoImpiegato = new Impiegato("Alessandra", "Carini", "CRNLSN95D65D969Z", dataNascita2, dataAssunzione2, compagniaRicaricata);

		int quantiElementiInseriti2 = impiegatoDAOInstance.insert(altroNuovoImpiegato);
		if (quantiElementiInseriti2 < 1)
			throw new RuntimeException("testFindAllByCompagnia : FAILED, impiegato non inserito");
		
		if(impiegatoDAOInstance.findAllByCompagnia(compagniaRicaricata) == null) {
			throw new RuntimeException("testFindAllByCodFisImpiegatoContiene : FAILED, ricerca non riuscita");
		};
		System.out.println(".......testFindAllByCompagnia fine: PASSED.............");
		System.out.println("");
	} 

	private static void testCountByDataFormazioneCompagniaGreaterThan(CompagniaDAO compagniaDAOInstance, ImpiegatoDAO impiegatoDAOInstance) throws Exception{
		System.out.println(".......testCountByDataFormazioneCompagniaGreaterThan inizio.............");
		System.out.println("");
		
		Date dataFondazione = new SimpleDateFormat("dd-MM-yyyy").parse("30-05-2005");
		Compagnia nuovaCompagnia = new Compagnia("Cyberline", (long)8500000, dataFondazione);
		int quantiElementiInseriti = compagniaDAOInstance.insert(nuovaCompagnia);
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testCountByDataFormazioneCompagniaGreaterThan : FAILED, compagnia non inserita");
		
		List<Compagnia> elencoCompagniePresenti = compagniaDAOInstance.list();
		Compagnia compagniaRicaricata = elencoCompagniePresenti.get(elencoCompagniePresenti.size()-1);
		if (compagniaRicaricata == null)
			throw new RuntimeException("testCountByDataFormazioneCompagniaGreaterThan : FAILED, compagnia non ricaricata");
		
		Date dataNascita = new SimpleDateFormat("dd-MM-yyyy").parse("14-08-1992");
		Date dataAssunzione = new SimpleDateFormat("dd-MM-yyyy").parse("13-05-2018");

		// me ne creo uno che fa al caso mio così da poterne trovare almeno uno
		Impiegato nuovoImpiegato = new Impiegato("Flavio", "Amato", " MTAFLV92M14A089T", dataNascita, dataAssunzione, compagniaRicaricata);

		int quantiElementiInseriti1 = impiegatoDAOInstance.insert(nuovoImpiegato);
		if (quantiElementiInseriti1 < 1)
			throw new RuntimeException("testCountByDataFormazioneCompagniaGreaterThan : FAILED, impiegato non inserito");
		
		Date dataNascita2 = new SimpleDateFormat("dd-MM-yyyy").parse("28-02-1994");
		Date dataAssunzione2 = new SimpleDateFormat("dd-MM-yyyy").parse("18-06-2019");

		// me ne creo uno che fa al caso mio così da poterne trovare almeno uno
		Impiegato altroNuovoImpiegato = new Impiegato("Vincenzo", "Collura", "CLLVCN94B28D514B", dataNascita2, dataAssunzione2, compagniaRicaricata);

		int quantiElementiInseriti2 = impiegatoDAOInstance.insert(altroNuovoImpiegato);
		if (quantiElementiInseriti2 < 1)
			throw new RuntimeException("testCountByDataFormazioneCompagniaGreaterThan : FAILED, impiegato non inserito");
		
		Date dataPerQuery = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2002");
		if(impiegatoDAOInstance.countByDataFormazioneCompagniaGreaterThan(dataPerQuery) < 0) {
			throw new RuntimeException("testCountByDataFormazioneCompagniaGreaterThan : FAILED, ricerca non riuscita");
		};
		System.out.println(".......testCountByDataFormazioneCompagniaGreaterThan fine: PASSED.............");
		System.out.println("");
		}
	}