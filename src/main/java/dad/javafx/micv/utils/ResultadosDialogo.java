package dad.javafx.micv.utils;

import java.time.LocalDate;

public class ResultadosDialogo {
		
	private String primero;
	private String segundo;
	private LocalDate desde;
	private LocalDate hasta;
	
	public ResultadosDialogo(String primero, String segundo, LocalDate desde, LocalDate hasta) {
		this.primero = primero;
		this.segundo = segundo;
		this.desde = desde;
		this.hasta = hasta;
	}

	public String getPrimero() {
		return primero;
	}

	public void setPrimero(String primero) {
		this.primero = primero;
	}

	public String getSegundo() {
		return segundo;
	}

	public void setSegundo(String segundo) {
		this.segundo = segundo;
	}

	public LocalDate getDesde() {
		return desde;
	}

	public void setDesde(LocalDate desde) {
		this.desde = desde;
	}

	public LocalDate getHasta() {
		return hasta;
	}

	public void setHasta(LocalDate hasta) {
		this.hasta = hasta;
	}
	
}
