package com.gfi.LDVELHWebService.bll.component;

import org.springframework.stereotype.Component;

import com.gfi.LDVELHWebService.bo.Livre;


public interface VerificationChargementLivre {
	public String nbChapVerif(short nbChap[], String titre[]);
}
