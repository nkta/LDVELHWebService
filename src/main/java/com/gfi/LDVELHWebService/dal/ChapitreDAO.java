package com.gfi.LDVELHWebService.dal;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gfi.LDVELHWebService.bo.Chapitre;
import com.gfi.LDVELHWebService.bo.Livre;



public interface ChapitreDAO extends CrudRepository<Chapitre, Short>{ 
 public Optional<Chapitre> findByNumeroChapitreAndLivre(short numChap,Livre livre);
 @Transactional
 @Modifying
 public void deleteByLivre(Livre livre);
 public short countByLivre(Livre livre);
 public Chapitre findByNumeroChapitre(short numChap);
}
