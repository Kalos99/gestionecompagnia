package it.gestionecompagnia.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Impiegato {
	private Long id;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private Date dataDiNascita;
	private Date dataDiAssunzione;
	private Compagnia compagniaDiAppartenenza;
	
	public Impiegato() {
	}

	public Impiegato(Long id, String nome, String cognome, String codiceFiscale, Date dataDiNascita, Date dataDiAssunzione) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.dataDiNascita = dataDiNascita;
		this.dataDiAssunzione = dataDiAssunzione;
	}
	
	public Impiegato(String nome, String cognome, String codiceFiscale, Date dataDiNascita, Date dataDiAssunzione) {
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.dataDiNascita = dataDiNascita;
		this.dataDiAssunzione = dataDiAssunzione;
	}

	public Impiegato(Long id, String nome, String cognome, String codiceFiscale, Date dataDiNascita, Date dataDiAssunzione, Compagnia compagniaDiAppartenenza) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.dataDiNascita = dataDiNascita;
		this.dataDiAssunzione = dataDiAssunzione;
		this.compagniaDiAppartenenza = compagniaDiAppartenenza;
	}
	
	public Impiegato(String nome, String cognome, String codiceFiscale, Date dataDiNascita, Date dataDiAssunzione, Compagnia compagniaDiAppartenenza) {
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.dataDiNascita = dataDiNascita;
		this.dataDiAssunzione = dataDiAssunzione;
		this.compagniaDiAppartenenza = compagniaDiAppartenenza;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Date getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public Date getDataDiAssunzione() {
		return dataDiAssunzione;
	}

	public void setDataDiAssunzione(Date dataDiAssunzione) {
		this.dataDiAssunzione = dataDiAssunzione;
	}

	public Compagnia getCompagniaDiAppartenenza() {
		return compagniaDiAppartenenza;
	}

	public void setCompagniaDiAppartenenza(Compagnia compagniaDiAppartenenza) {
		this.compagniaDiAppartenenza = compagniaDiAppartenenza;
	}
	
	@Override
	public String toString() {
		String dateCreatedString1 = dataDiNascita != null ? new SimpleDateFormat("dd/MM/yyyy").format(dataDiNascita) : " N.D.";
		String dateCreatedString2 = dataDiAssunzione != null ? new SimpleDateFormat("dd/MM/yyyy").format(dataDiAssunzione) : " N.D.";
		
		return "Nome impiegato: " + this.getNome() + "\nCognome impiegato: " + this.getCognome() 
				+ "\nCodice fiscale impiegato: " + this.getCodiceFiscale() + "\nData di nascita: " 
				+ dateCreatedString1 + "\nData di assunzione: " + dateCreatedString2; 
	}		
}