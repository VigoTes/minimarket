package com.minimarket.demo.model;

import java.util.List;

import com.dieselpoint.norm.Database;

public class ModeloGuardable {

	 
	
	public void guardar() {
		
		Database db = new Database();
		db.insert(this);
	}
	
	public void actualizar() {

		Database db = new Database();
		db.update(this);
		
	}
	
}
