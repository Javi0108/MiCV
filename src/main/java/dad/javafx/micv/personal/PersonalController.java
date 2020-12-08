package dad.javafx.micv.personal;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.micv.App;
import dad.javafx.micv.model.personal.Nacionalidad;
import dad.javafx.micv.model.personal.Personal;
import dad.javafx.micv.utils.LectorCSV;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PersonalController implements Initializable {

	// model

	private ObjectProperty<Personal> personal = new SimpleObjectProperty<>();
	private ObjectProperty<Nacionalidad> nacionalidadSeleccionada = new SimpleObjectProperty<>();

	// view

	@FXML
	private GridPane view;

	@FXML
	private TextField identificacionText;

	@FXML
	private TextField nombreText;

	@FXML
	private TextField apellidosText;
	
	@FXML
    private DatePicker fechaNacimientoDatePicker;

    @FXML
    private TextArea direccionTextArea;

    @FXML
    private TextField codPostalText;

    @FXML
    private TextField localidadText;

    @FXML
    private ComboBox<String> paisCombo;

    @FXML
    private ListView<Nacionalidad> nacionalidadList;
    
    private List<String> listadoNacionalidades = new ArrayList<>();

	public PersonalController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PersonalView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Creamos combo de paises
		try {
			paisCombo.getItems().addAll(LectorCSV.leerFichero("/csv/paises.csv"));
		} catch (Exception e) {
			App.error("Listado de países no disponible.", e.getLocalizedMessage());
			Platform.exit();
		}
		
		// Cargamos listado de posibles nacionalidades
		try {
			listadoNacionalidades.addAll(LectorCSV.leerFichero("/csv/nacionalidades.csv"));
		} catch (Exception e) {
			App.error("Listado de nacionalidades no disponible.", e.getLocalizedMessage());
			Platform.exit();
		}

		this.personal.addListener((o, ov, nv) -> onPersonalChanged(o, ov, nv));
		
	}

	private void onPersonalChanged(ObservableValue<? extends Personal> o, Personal ov, Personal nv) {
		
		if (ov != null) {
			identificacionText.textProperty().unbindBidirectional(ov.identificacionProperty());
			nombreText.textProperty().unbindBidirectional(ov.nombreProperty());
			apellidosText.textProperty().unbindBidirectional(ov.apellidosProperty());
			fechaNacimientoDatePicker.valueProperty().unbindBidirectional(ov.fechaNacimientoProperty());
			direccionTextArea.textProperty().unbindBidirectional(ov.direccionProperty());
			codPostalText.textProperty().unbindBidirectional(ov.codigoPostalProperty());
			localidadText.textProperty().unbindBidirectional(ov.localidadProperty());
			paisCombo.valueProperty().unbindBidirectional(ov.paisProperty());
			nacionalidadList.itemsProperty().unbind();
			nacionalidadSeleccionada.unbind();
		}

		if (nv != null) {
			identificacionText.textProperty().bindBidirectional(nv.identificacionProperty());
			nombreText.textProperty().bindBidirectional(nv.nombreProperty());
			apellidosText.textProperty().bindBidirectional(nv.apellidosProperty());
			fechaNacimientoDatePicker.valueProperty().bindBidirectional(nv.fechaNacimientoProperty());
			direccionTextArea.textProperty().bindBidirectional(nv.direccionProperty());
			codPostalText.textProperty().bindBidirectional(nv.codigoPostalProperty());
			localidadText.textProperty().bindBidirectional(nv.localidadProperty());
			paisCombo.valueProperty().bindBidirectional(nv.paisProperty());
			nacionalidadList.itemsProperty().bind(nv.nacionalidadesProperty());
			nacionalidadSeleccionada.bind(nacionalidadList.getSelectionModel().selectedItemProperty());
		}
		
	}
	
	@FXML
    void onClickNuevaNacionalidad(ActionEvent event) {
		ChoiceDialog<String> dialog = new ChoiceDialog<>(listadoNacionalidades.get(0), listadoNacionalidades);
		dialog.setTitle("Nueva nacionalidad");
		dialog.setHeaderText("Añadir nacionalidad");
		dialog.setContentText("Seleccione una nacionalidad");
		
		Optional<String> result = dialog.showAndWait();
		
		if (result.isPresent()) {
			Nacionalidad nacionalidad = new Nacionalidad();
			nacionalidad.setDenominacion(result.get());
			
			personal.get().getNacionalidades().add(nacionalidad);
		}
    }

    @FXML
    void onClickQuitarNacionalidad(ActionEvent event) {
    	personal.get().getNacionalidades().remove(nacionalidadSeleccionada.get());
    }
	
	public GridPane getView() {
		return view;
	}

	public final ObjectProperty<Personal> personalProperty() {
		return this.personal;
	}

	public final Personal getPersonal() {
		return this.personalProperty().get();
	}

	public final void setPersonal(final Personal personal) {
		this.personalProperty().set(personal);
	}

}

