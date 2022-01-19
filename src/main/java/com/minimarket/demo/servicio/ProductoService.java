package com.minimarket.demo.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimarket.demo.interfaceService.IProductoService;
import com.minimarket.demo.interfaces.IProducto;
import com.minimarket.demo.modelo.Producto;

@Service
public class ProductoService implements IProductoService{

	@Autowired
	private IProducto data;
	
	@Override
	public List<Producto> listar() {
		return (List<Producto>)data.findAll();
	}

	@Override
	public Optional<Producto> listarId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(Producto p) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
}
