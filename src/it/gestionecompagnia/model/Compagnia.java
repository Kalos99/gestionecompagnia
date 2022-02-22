package it.gestionecompagnia.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Compagnia {
	private Long id;
	private String ragioneSociale;
	private Long fatturatoAnnuo;
	private Date dataFondazione;
	private List<Impiegato> impiegati = new ArrayList<>();
	
	public Compagnia() {
	}
	
	public Compagnia(Long id, String ragioneSociale, Long fatturatoAnnuo, Date dataFondazione) {
		this.id = id;
		this.ragioneSociale = ragioneSociale;
		this.fatturatoAnnuo = fatturatoAnnuo;
		this.dataFondazione = dataFondazione;
	}

	public Compagnia(Long id, String ragioneSociale, Long fatturatoAnnuo, Date dataFondazione, List<Impiegato> impiegati) {
		this.id = id;
		this.ragioneSociale = ragioneSociale;
		this.fatturatoAnnuo = fatturatoAnnuo;
		this.dataFondazione = dataFondazione;
		this.impiegati = impiegati;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public Long getFatturatoAnnuo() {
		return fatturatoAnnuo;
	}

	public void setFatturatoAnnuo(Long fatturatoAnnuo) {
		this.fatturatoAnnuo = fatturatoAnnuo;
	}

	public Date getDataFondazione() {
		return dataFondazione;
	}

	public void setDataFondazione(Date dataFondazione) {
		this.dataFondazione = dataFondazione;
	}

	public List<Impiegato> getImpiegati() {
		return impiegati;
	}

	public void setImpiegati(List<Impiegato> impiegati) {
		this.impiegati = impiegati;
	}

	@Override
	public String toString() {
		String dateCreatedString = dataFondazione != null ? new SimpleDateFormat("dd/MM/yyyy").format(dataFondazione) : " N.D.";
		
		return "Nome compagnia: " + this.getRagioneSociale() + "\nFatturato annuo della compagnia: " 
				+ this.getFatturatoAnnuo() + "\nData di fondazione della compagnia: " + dateCreatedString;
	}
}
