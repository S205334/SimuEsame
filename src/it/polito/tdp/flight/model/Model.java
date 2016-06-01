package it.polito.tdp.flight.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import com.sun.javafx.geom.Edge;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	
	private List<Airport> airports;
	private List<Airline> airlines;
	private List<Route> routes;
	private FlightDAO dao;
	private SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge> graph;
	
	
	
	public List<Airport> getAllAirport() {
		
		if(this.airports != null)
			return this.airports;
		
		dao = new FlightDAO();
		this.airports = dao.getAllAirports();
		// System.out.println(airports.size());
		return airports;	
	}
	
	public List<Airline> getAllAirline() {
		
		if(this.airlines != null)
			return this.airlines;
		
		dao = new FlightDAO();
		this.airlines = dao.getAllAirlines();
		
		return airlines;	
	}
	
	public void creaGrafo(Airline airline) {
		
		this.graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(graph, airports);
		
		dao = new FlightDAO();
		routes = dao.getRoutesByAirline(airline);
		
		System.out.println(routes.size());
		
		for(Route r : routes) {
			Airport source =  airports.get(airports.indexOf(new Airport (r.getSourceAirportId())));
			Airport destination = airports.get(airports.indexOf(new Airport(r.getDestinationAirportId())));
			int km = calcolaKm(source, destination); 
			
			Graphs.addEdge(graph, source, destination, km);
		}
		
		System.out.println("Archi: "+graph.edgeSet().size()+" Vertici: "+graph.vertexSet().size());
		
	}
	
	public List<Airport> getListAirport() {
		List<Airport> list = new ArrayList<>();
		
		for(Airport a : graph.vertexSet())
			for(Airport a2 : Graphs.neighborListOf(graph, a))
				if(!list.contains(a2))
					list.add(a2);
		
		return list;
	}

	private int calcolaKm(Airport s, Airport d) {
		return (int) LatLngTool.distance(new LatLng(s.getLatitude(), s.getLongitude()), new LatLng(d.getLatitude(), d.getLongitude()), LengthUnit.KILOMETER);
	}

	public List<AirportWithKm> getDestinationsByAirport(Airport a) {
		dao = new FlightDAO();
		List<AirportWithKm> list = new ArrayList<>();
		
		for(Airport ap : dao.getDestinationsByAirport(a)) {
			list.add( new AirportWithKm(ap, calcolaKm(a,ap)));
		}
		
		Collections.sort(list);

		for(AirportWithKm ak : list)
			System.out.format("Airport: %s KM: %d\n", ak.getAirport(), ak.getKm());
		
		return list;
	}
	
}
