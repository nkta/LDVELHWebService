package com.gfi.LDVELHWebService.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gfi.LDVELHWebService.bll.EpubToDB;
import com.gfi.LDVELHWebService.bo.Chapitre;
import com.gfi.LDVELHWebService.bo.Collection;
import com.gfi.LDVELHWebService.bo.Livre;
import com.gfi.LDVELHWebService.dal.ChapitreDAO;
import com.gfi.LDVELHWebService.dal.CollectionDAO;
import com.gfi.LDVELHWebService.dal.LivreDAO;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/ldvelh")
public class Ldvelhws {
	@Autowired
	EpubToDB metier;

	@Autowired
	LivreDAO livreDao;
	@Autowired
	ChapitreDAO chapitreDao;
	
	@Autowired
	CollectionDAO collectionDao;
	
	@GetMapping("/insert")
	public String livreRequest() {
		return metier.loadParagraphe();
	}
	
    @GetMapping("/{titre}/{nbchap}")
    public Optional<Chapitre> pageRequest(@PathVariable String titre, @PathVariable short nbchap) {
   	 return chapitreDao.findByNumeroChapitreAndLivre(nbchap, livreDao.findByTitre(titre));
    }
    
    @GetMapping("/delete/{titre}")
    public String supLivre(@PathVariable String titre) {
   	 metier.DeleteLivre(titre);
   	 return titre+" est supprimé";
    }
    
	@GetMapping("/listeLivre/{collection}")
	public List<Livre> livreParCollection(@PathVariable String collection) {

		return livreDao.findByCollection(collectionDao.findByCollection(collection));
	}
	
	@GetMapping("/listeCollection")
	public List<Collection> listeCollection() {

		return (List<Collection>) collectionDao.findAll();
	}
}
