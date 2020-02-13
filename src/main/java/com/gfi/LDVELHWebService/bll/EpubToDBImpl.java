package com.gfi.LDVELHWebService.bll;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gfi.LDVELHWebService.bll.component.VerificationChargementLivre;
import com.gfi.LDVELHWebService.bo.Chapitre;
import com.gfi.LDVELHWebService.bo.Collection;
import com.gfi.LDVELHWebService.bo.Livre;
import com.gfi.LDVELHWebService.dal.ChapitreDAO;
import com.gfi.LDVELHWebService.dal.CollectionDAO;
import com.gfi.LDVELHWebService.dal.LivreDAO;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

@Service
public class EpubToDBImpl implements EpubToDB {
	public static final String[] PATH = { "LDVELH - Sorcellerie 1 - Les Co - LDVELH SORC1 - Jackson,Steve.epub", "LDVELH - Sorcellerie 2 - La Cit - LDVELH SORC2 - Jackson,Steve.epub", "LDVELH - Sorcellerie 03 - Les S - LDVELH SORC3 - Jackson,Steve.epub",
			"LDVELH - Sorcellerie 4 - La Cou - LDVELH - SORC4 - Jackson,Steve.epub" };
	public static final String[] TITRE_LIVRE = { "les_collines_malefiques", "la_cite_des_pieges",
			"les_sept_serpents", "la_couronne_des_rois" };
	public static final String[] TITRE_LIVRE_SHOW = { "Les Collines Malefiques", "La Cite des Pieges",
			"Les Sept Serpents", "La Couronne des Rois" };
	public static final String COLLECTION = "sorcellerie";
	public static final String COLLECTION_SHOW = "Sorcellerie";
	public static final short[] NOMBRE_CHAPITRE = { 456, 511, 498, 800 };

	@Autowired
	ChapitreDAO chapitreDao;

	@Autowired
	LivreDAO livreDao;

	@Autowired
	CollectionDAO collectionDao;

	@Autowired
	VerificationChargementLivre verifLivre;

	//@Transactional
	@Override
	public String loadParagraphe() {
		// TODO Auto-generated method stub
		System.out.println(TITRE_LIVRE);

		Collection collection = new Collection();
		collection.setCollection(COLLECTION);
		collection.setCollectionShow(COLLECTION_SHOW);
		collection = collectionDao.save(collection);
		collection = collectionDao.findByCollection(COLLECTION);
		System.out.println("id collection : "+collection.getId());
		for (int j = 0; j < 5; j++) {
			EpubReader epubReader = new EpubReader();
			Book book = null;
			boolean ok = false;
			try {
				book = epubReader.readEpub(new FileInputStream("Sorcellerie/" + PATH[j]));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String entireContent = "";
			String textContent = "";
			String chapSuivant = "";
			short nbChap = 0;
			Chapitre chapitre = new Chapitre();

			Livre livre = new Livre();
			livre.setTitre(TITRE_LIVRE[j]);
			livre.setTitreShow(TITRE_LIVRE_SHOW[j]);

			collection.addLivre(livre);
			livre = livreDao.save(livre);
			livre = livreDao.findByTitre(TITRE_LIVRE[j]);
			int fileNumber = book.getContents().size();
			for (int i = 0; i < fileNumber; i++) {
				InputStream inputStream = null;
				try {
					inputStream = book.getContents().get(i).getInputStream();
					// System.out.println(book.getContents().get(i).getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
				} // file .html
				try {
					Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
					entireContent = scanner.hasNext() ? scanner.next() : "";
					// System.out.println(entireContent);
					PrintWriter writer = null;
					try {
						writer = new PrintWriter("mon-fichier_" + i + ".txt", "UTF-8");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					writer.println(entireContent);
					writer.close();
				} finally {
					try {
						inputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				org.jsoup.nodes.Document doc = null;

				Reader inputString = new StringReader(entireContent);
				BufferedReader reader = new BufferedReader(inputString);
				String st = null;

				try {
					while ((st = reader.readLine()) != null) {
						doc = Jsoup.parseBodyFragment(st);
						// System.out.println(st);
						// if(doc.select("p:matchesOwn([^a-zA-Z]) > b:matchesOwn([0-9]+)").isEmpty())
						if (((!doc.select("p > b:matchesOwn([0-9]+)").isEmpty()
								&& doc.select("p:matchesOwn([^a-zA-Z]+)").isEmpty()
								&& doc.select("p > b:matchesOwn([a-zA-Z]+)").isEmpty())
								|| !doc.select("p > i > b:matchesOwn([0-9]+)").isEmpty())) {
							if (ok) {
								System.out.println(nbChap);
								chapitre.setNumeroChapitre(nbChap);
								chapitre.setChapitre(textContent);
								if (chapSuivant.length() != 0)
									chapitre.setChapitreSuivant(chapSuivant.substring(0, chapSuivant.length() - 1));
								else
									chapitre.setChapitreSuivant("");
								livre.addChapitre(chapitre);
								chapitreDao.save(chapitre);
								//chapitreDao.findByNumeroChapitreAndLivre(nbChap, livre);
								chapitre = new Chapitre();
								textContent = "";
								chapSuivant = "";
							}
							ok = true;
							if (!doc.getElementsByTag("b").isEmpty())
								nbChap = Short.parseShort(getNumber(doc.getElementsByTag("b").first()));
							else
								nbChap = Short.parseShort(getNumber(doc.getElementsByTag("p").first()));

						} else if (!doc.select("p").isEmpty() && ok) {
							textContent += "<p>" + doc.select("p").html() + "</p>";
							if (!doc.select("a>b").isEmpty()) {
								for (Element e : doc.getElementsByTag("a")) {
									chapSuivant += getNumber(e.getElementsByTag("b").first()) + " ";
								}  

							}

							// }
						}
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			System.out.println(nbChap);
			System.out.println(textContent);
			System.out.println(chapSuivant);
			chapitre.setNumeroChapitre(nbChap);
			chapitre.setChapitre(textContent);
			if (chapSuivant.length() != 0)
				chapitre.setChapitreSuivant(chapSuivant.substring(0, chapSuivant.length() - 1));
			else 
				chapitre.setChapitreSuivant("");
			livre.addChapitre(chapitre);
			chapitreDao.save(chapitre);
			chapitre = new Chapitre();
			textContent = "";
			chapSuivant = "";
		}
		return verifLivre.nbChapVerif(NOMBRE_CHAPITRE, TITRE_LIVRE);

	}

	@Override
	public Optional<Chapitre> AfficheParagraphe(short numChap, Livre livre) {
		// TODO Auto-generated method stub
		return chapitreDao.findByNumeroChapitreAndLivre(numChap, livre);
	}

	public String getNumber(Element elem) {
		if (elem != null) {
			Matcher matcher = Pattern.compile("\\d+").matcher(elem.html()); // on cherche une suite de chiffres
			if (matcher.find()) { // si on trouve
				return matcher.group(0); // on récupère ce qui a été trouvé
			}
		}
		return "";
	}

	@Override
	public void DeleteLivre(String titre) {
		livreDao.deleteByTitre(titre);

	}

	@Override
	public ArrayList<Livre> livresParCollection(String collection) {
		// TODO Auto-generated method stub
		return null;//LivreDao.getDistinctTitreLivreByCollection(collection);
	}

}
