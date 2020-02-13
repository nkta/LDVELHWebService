package com.gfi.LDVELHWebService.bll;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gfi.LDVELHWebService.bo.Chapitre;
import com.gfi.LDVELHWebService.bo.Livre;


public interface EpubToDB {
	public String loadParagraphe();
	public Optional<Chapitre> AfficheParagraphe(short numChap, Livre livre);
	public void DeleteLivre(String titre);
	public ArrayList<Livre> livresParCollection(String collection); 
}
