package com.gfi.LDVELHWebService.bll.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gfi.LDVELHWebService.bo.Livre;
import com.gfi.LDVELHWebService.dal.ChapitreDAO;
import com.gfi.LDVELHWebService.dal.LivreDAO;

@Component
public class VerificationChargementLivreImpl implements VerificationChargementLivre {

	@Autowired
	ChapitreDAO chapitreDao;
	
	@Autowired
	LivreDAO livreDao;

	public String nbChapVerif(short nbChap[], String titre[]) {
		String str = "";
		Livre livre = new Livre();
		for (int j=0; j < nbChap.length; j++) {
			livre = livreDao.findByTitre(titre[j]);
			if (chapitreDao.countByLivre(livre) != nbChap[j]) {
				for (short i = 1; i < nbChap[j]; i++) {
					if (!chapitreDao.findByNumeroChapitreAndLivre(i, livre).isPresent()) {
						str += i + "";
					}
				}
				System.out.println(
						"Le livre "+ livre.getTitreShow()+" est chargé avec erreurs \nle(s) chapitre(s) " + str + " est/sont manquant(s)");
			} else {
				System.out.println("Le livre "+ livre.getTitreShow()+" est chargé sans erreurs");
			}
			str="";
		}
		return "chargement fini";
	}

}
