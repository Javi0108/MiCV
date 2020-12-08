package dad.javafx.micv.model.conocimiento;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Idioma extends Conocimiento {

	private StringProperty certificacion = new SimpleStringProperty();

	public final StringProperty certificacionProperty() {
		return this.certificacion;
	}	

	public final String getCertificacion() {
		return this.certificacionProperty().get();
	}	

	public final void setCertificacion(final String certificacion) {
		this.certificacionProperty().set(certificacion);
	}
	
}
