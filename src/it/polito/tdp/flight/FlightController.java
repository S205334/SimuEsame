package it.polito.tdp.flight;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.flight.model.Airline;
import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.AirportWithKm;
import it.polito.tdp.flight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FlightController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Airline> boxAirline;

    @FXML
    private ComboBox<Airport> boxAirport;

    @FXML
    private TextArea txtResult;

    @FXML
    void doRaggiungibili(ActionEvent event) {
    	
    	List<AirportWithKm> list = model.getDestinationsByAirport(boxAirport.getValue());
    	
		txtResult.appendText("\nDall'aeroporto "+boxAirport.getValue()+" è possibile raggiungere: \n");
    	
    	for( AirportWithKm a : list)
    		txtResult.appendText(a.getAirport()+"\n");
    	
    	
    }

    @FXML
    void doServiti(ActionEvent event) {

    	model.getAllAirport();
    	model.creaGrafo(boxAirline.getValue());
    	
    	List<Airport> list = model.getListAirport();
    	
    	if( list.isEmpty()) {
    		txtResult.appendText("La compagnia aerea "+boxAirline.getValue()+" non serve nessun aeroporto!\n");
    		return;
    	}
    		
    	boxAirport.getItems().addAll(list);
    	
    	txtResult.appendText("La compagnia aerea "+boxAirline.getValue()+" serve :\n");
    	for ( Airport a : list)
    		txtResult.appendText(a+"\n");
    	
    }
    
    public void setModel(Model m) {
    	this.model = m;
    	boxAirline.getItems().addAll(m.getAllAirline());
    	
    }

    @FXML
    void initialize() {
        assert boxAirline != null : "fx:id=\"boxAirline\" was not injected: check your FXML file 'Flight.fxml'.";
        assert boxAirport != null : "fx:id=\"boxAirport\" was not injected: check your FXML file 'Flight.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Flight.fxml'.";

    }
}
