package dad.javafx.micv.formacion;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.micv.App;
import dad.javafx.micv.model.Titulo;
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

public class FormacionController implements Initializable {

	// model

	private ListProperty<Titulo> titulos = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Titulo> seleccionado = new SimpleObjectProperty<>();

	// view

	@FXML
	private BorderPane view;

	@FXML
	private TableView<Titulo> tvFormacion;
	
	@FXML
    private TableColumn<Titulo, LocalDate> tcDesde;

    @FXML
    private TableColumn<Titulo, LocalDate> tcHasta;

    @FXML
    private TableColumn<Titulo, String> tcDenominacion;

    @FXML
    private TableColumn<Titulo, String> tcOrganizador;

	@FXML
	private Button btAnadir;

	@FXML
	private Button btEliminar;

	public FormacionController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormacionView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcDesde.setCellValueFactory(v -> v.getValue().desdeProperty());
		tcHasta.setCellValueFactory(v -> v.getValue().hastaProperty());
		tcDenominacion.setCellValueFactory(v -> v.getValue().denominacionProperty());
		tcOrganizador.setCellValueFactory(v -> v.getValue().organizadorProperty());
		
		tcDesde.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		tcHasta.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		tcDenominacion.setCellFactory(TextFieldTableCell.forTableColumn());
		tcOrganizador.setCellFactory(TextFieldTableCell.forTableColumn());
		
		this.titulos.addListener((o, ov, nv) -> onTituloChanged(o, ov, nv));
	}

	private void onTituloChanged(ObservableValue<? extends ObservableList<Titulo>> o, ObservableList<Titulo> ov, ObservableList<Titulo> nv) {
		if (ov != null) {
			tvFormacion.setItems(null);
			seleccionado.unbind();
		}
		
		if (nv != null) {
			tvFormacion.setItems(nv);
			seleccionado.bind(tvFormacion.getSelectionModel().selectedItemProperty());
		}

	}

	@FXML
	void onClickAnadir(ActionEvent event) {
		Dialog<ResultadosDialogo> dialog = new Dialog<>();
		
		dialog.setTitle("Nuevo tÃ­tulo");
		
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		
		ButtonType btCrear = new ButtonType("Crear", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(btCrear, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField tfDenominacion = new TextField();
		TextField tfOrganizador = new TextField();
		
		DatePicker dpDesde = new DatePicker();
		DatePicker dpHasta = new DatePicker();
		
		Node nodeBtAnadir = dialog.getDialogPane().lookupButton(btCrear);
		nodeBtAnadir.setDisable(true);
		
		nodeBtAnadir.disableProperty().bind(
				tfDenominacion.textProperty().isEmpty().or(
				tfOrganizador.textProperty().isEmpty()).or(
				dpDesde.valueProperty().isNull()).or(
				dpHasta.valueProperty().isNull()));
		
		grid.add(new Label("DenominaciÃ³n"), 0, 0);
		grid.add(tfDenominacion, 1, 0);
		grid.add(new Label("Organizador"), 0, 1);
		grid.add(tfOrganizador, 1, 1);
		grid.add(new Label("Desde"), 0, 2);
		grid.add(dpDesde, 1, 2);
		grid.add(new Label("Hasta"), 0, 3);
		grid.add(dpHasta, 1, 3);
		
		dialog.getDialogPane().setContent(grid);
		
		Platform.runLater(() -> tfDenominacion.requestFocus());
		
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == btCrear) {
				return new ResultadosDialogo(
						tfDenominacion.getText(),
						tfOrganizador.getText(),
						dpDesde.getValue(),
						dpHasta.getValue());
			}
			return null;
		});
		
		Optional<ResultadosDialogo> result = dialog.showAndWait();
		
		if (result.isPresent()) {
			Titulo resultado = new Titulo();
			resultado.setDenominacion(result.get().getPrimero());
			resultado.setOrganizador(result.get().getSegundo());
			resultado.setDesde(result.get().getDesde());
			resultado.setHasta(result.get().getHasta());
			titulos.get().add(resultado);
		}
	}

	@FXML
	void onClickEliminar(ActionEvent event) {
		String title = "Eliminar formación";
		String header = "Antes de continuar, confirme";
		String content = "Esta operación es irreversible.\n ¿Está seguro de borrar la formación?";
		Titulo formacion = seleccionado.get();
		
		if (formacion != null && App.confirm(title, header, content))
			titulos.get().remove(formacion);
	}

	public BorderPane getView() {
		return view;
	}

	public final ListProperty<Titulo> titulosProperty() {
		return this.titulos;
	}
	

	public final ObservableList<Titulo> getTitulos() {
		return this.titulosProperty().get();
	}
	

	public final void setTitulos(final ObservableList<Titulo> titulos) {
		this.titulosProperty().set(titulos);
	}

}