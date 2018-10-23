package com.odsaprojects.prod.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * @author Osvaldo Sandoval
 *
 */
@ManagedBean(name="indexBean")
@ViewScoped
public class IndexBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	   private String name;
	 
	   public IndexBean() {
	      name = "Osvaldo";
	   }
	 
	   public String getName() {
	       return name;
	   }
	 
	   public void setName(String name) {
	       this.name = name;
	   }	  

}
