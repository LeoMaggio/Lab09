/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {

	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader
	
	@FXML
    private ComboBox<Country> cmbNazione;

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doCalcolaConfini(ActionEvent event) {
		this.txtResult.clear();
		Integer anno = null;
		try {
			anno = Integer.parseInt(this.txtAnno.getText().trim());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			this.txtResult.setText("Devi inserire un anno valido!");
		}
		this.model.creaGrafo(anno);
		this.cmbNazione.getItems().addAll(this.model.getCountries());
		Map <Country, Integer> stats = model.getCountryCounts();
		for (Country c : stats.keySet())
			txtResult.appendText(String.format("%s (%d)\n", c, stats.get(c)));
		txtResult.appendText("Il numero di componenti connesse del grafo e': " +this.model.getNumberOfConnectedComponents());
	}
	
	@FXML
    void doTrovaTuttiVicini(ActionEvent event) {
		this.txtResult.clear();
		Country country = this.cmbNazione.getValue();
		if(country == null) {
			this.txtResult.setText("Devi selezionare un Country dal menu'!");
			return;
		}
		for (Country c : this.model.trovaTuttiVicini(country))
			txtResult.appendText(c.toString()+ "\n");
    }

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
		assert cmbNazione != null : "fx:id=\"cmbNazione\" was not injected: check your FXML file 'Borders.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
}
