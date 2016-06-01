package it.polito.tdp.flight.model;

public class AirportWithKm implements Comparable<AirportWithKm>{
	
	private Airport airport;
	private int km;
	
	public AirportWithKm(Airport airport, int km) {
		super();
		this.airport = airport;
		this.km = km;
	}


	
	
	public Airport getAirport() {
		return airport;
	}




	public int getKm() {
		return km;
	}




	@Override
	public int compareTo(AirportWithKm o) {
		// TODO Auto-generated method stub
		return Integer.compare(this.km, o.km);
	}
	
	

}
