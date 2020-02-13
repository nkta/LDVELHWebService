package com.gfi.LDVELHWebService.dal;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.gfi.LDVELHWebService.bo.Collection;
import com.gfi.LDVELHWebService.bo.Livre;

public interface LivreDAO extends CrudRepository<Livre, Integer> {
	public void deleteByTitre(String titre);
	public Livre findByTitre(String titre);
	public List<Livre> findByCollection(Collection collection);
}
