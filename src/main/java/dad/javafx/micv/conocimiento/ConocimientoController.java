package dad.javafx.micv.conocimiento;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.micv.App;
import dad.javafx.micv.model.Titulo;
import dad.javafx.micv.model.conocimiento.Conocimiento;
import dad.javafx.micv.utils.ResultadosDialogo;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

public class ConocimientoController implements Initializable{
	
	//model
	
	private ListProperty<Conocimiento> conocimientos = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Conocimiento> seleccionado = new SimpleObjectProperty<>();
	
	//view
	
	@FXML
	private BorderPane view;
	
	@FXML
	private TableView<Conocimiento> tvConocimiento;
	
	@FXML
	private TableColumn<Conocimiento, String> tcDenominacion;
	
	@FXML
	private TableColumn<Conocimiento, Object> tcNivel;
	
	@FXML
	private Button btAñadirConocimiento;
	
	@FXML
	private Button btAñadirIdioma;
	
	@FXML
	private Button btEliminar;
	
	public ConocimientoController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConocimientoView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialaze(URL location, ResourceBundle resource) {
		tcDenominacion.setCellValueFactory(v -> v.getValue().denominacionProperty());
		//tcNivel.setCellValueFactory(v -> v.);
		
		tcDenominacion.setCellFactory(TextFieldTableCell.forTableColumn());
		tcNivel.setCellFactory(TextFieldTableCell.forTableColumn());
	}

}
