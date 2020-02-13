package com.gfi.LDVELHWebService.bo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the collection database table.
 * 
 */
@Entity
@NamedQuery(name="Collection.findAll", query="SELECT c FROM Collection c")
public class Collection implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String collection;

	@Column(name="collection_show")
	private String collectionShow;

	//bi-directional many-to-one association to Livre
	@OneToMany(mappedBy="collection",fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Livre> livres = new ArrayList<Livre>();

	public Collection() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCollection() {
		return this.collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getCollectionShow() {
		return this.collectionShow;
	}

	public void setCollectionShow(String collectionShow) {
		this.collectionShow = collectionShow;
	}

	public List<Livre> getLivres() {
		return this.livres;
	}

	public void setLivres(List<Livre> livres) {
		this.livres = livres;
	}

	public Livre addLivre(Livre livre) {
		getLivres().add(livre);
		livre.setCollection(this);

		return livre;
	}

	public Livre removeLivre(Livre livre) {
		getLivres().remove(livre);
		livre.setCollection(null);

		return livre;
	}

}