package it.polito.tdp.borders.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.*;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private BordersDAO dao;
	private List<Country> clist;
	private Map<Integer, Country> cmap;
	private SimpleGraph<Country, DefaultEdge> grafo;

	public Model() {
		this.dao = new BordersDAO();
		this.cmap = new HashMap<Integer, Country>();
		this.clist = this.dao.loadAllCountries(cmap);
	}

	public void creaGrafo(Integer anno) {
		this.grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		
//		I vertici del grafo sono soltanto i Country che possiedono un arco	
		for(Border b : dao.getCountryPairs(anno, cmap)) {
			this.grafo.addVertex(b.getC1());
			this.grafo.addVertex(b.getC2());
			this.grafo.addEdge(b.getC1(), b.getC2());
		}
		
		System.out.println("Grafo creato!");
		System.out.println("# vertici: " +this.grafo.vertexSet().size());
		System.out.println("# archi: " +this.grafo.edgeSet().size());
	}
	
	public int getNumberOfConnectedComponents() {
		if (grafo == null)
			throw new RuntimeException("Grafo non esistente");
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<Country, DefaultEdge>(grafo);
		return ci.connectedSets().size();
	}

	public Map<Country, Integer> getCountryCounts() {
		if (grafo == null)
			throw new RuntimeException("Grafo non esistente");
		Map<Country, Integer> stats = new HashMap<Country, Integer>();
		for (Country c : grafo.vertexSet())
			stats.put(c, grafo.degreeOf(c));
		return stats;
	}

}
