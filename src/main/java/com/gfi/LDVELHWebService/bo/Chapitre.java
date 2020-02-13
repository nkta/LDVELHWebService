package com.gfi.LDVELHWebService.bo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the chapitre database table.
 * 
 */
@Entity
@JsonIgnoreProperties
@NamedQuery(name="Chapitre.findAll", query="SELECT c FROM Chapitre c")
public class Chapitre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Lob
	private String chapitre;

	@Column(name="chapitre_suivant")
	private String chapitreSuivant;

	@Column(name="numero_chapitre")
	private short numeroChapitre;

	//bi-directional many-to-one association to Livre
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name="titre_id")
	private Livre livre;

	public Chapitre() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChapitre() {
		return this.chapitre;
	}

	public void setChapitre(String chapitre) {
		this.chapitre = chapitre;
	}

	public String getChapitreSuivant() {
		return this.chapitreSuivant;
	}

	public void setChapitreSuivant(String chapitreSuivant) {
		this.chapitreSuivant = chapitreSuivant;
	}

	public short getNumeroChapitre() {
		return this.numeroChapitre;
	}

	public void setNumeroChapitre(short numeroChapitre) {
		this.numeroChapitre = numeroChapitre;
	}

	public Livre getLivre() {
		return this.livre;
	}

	public void setLivre(Livre livre) {
		this.livre = livre;
	}

}