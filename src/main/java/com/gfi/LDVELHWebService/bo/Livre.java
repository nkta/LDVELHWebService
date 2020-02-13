package com.gfi.LDVELHWebService.bo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the livre database table.
 * 
 */
@Entity
@NamedQuery(name="Livre.findAll", query="SELECT l FROM Livre l")
public class Livre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String titre;

	@Column(name="titre_show")
	private String titreShow;

	//bi-directional many-to-one association to Chapitre
	@OneToMany(mappedBy="livre",fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Chapitre> chapitres = new ArrayList<Chapitre>();

	//bi-directional many-to-one association to Collection
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Collection collection;

	public Livre() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return this.titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getTitreShow() {
		return this.titreShow;
	}

	public void setTitreShow(String titreShow) {
		this.titreShow = titreShow;
	}

	public List<Chapitre> getChapitres() {
		return this.chapitres;
	}

	public void setChapitres(List<Chapitre> chapitres) {
		this.chapitres = chapitres;
	}

	public Chapitre addChapitre(Chapitre chapitre) {
		getChapitres().add(chapitre);
		chapitre.setLivre(this);

		return chapitre;
	}

	public Chapitre removeChapitre(Chapitre chapitre) {
		getChapitres().remove(chapitre);
		chapitre.setLivre(null);

		return chapitre;
	}

	public Collection getCollection() {
		return this.collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

}