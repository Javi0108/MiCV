package dad.javafx.micv.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class LectorCSV {
	
	public static ArrayList<String> leerFichero(String nombre) throws IOException {
		ArrayList<String> result = new ArrayList<String>();
		FileReader fr = new FileReader(LectorCSV.class.getResource(nombre).getFile());
		BufferedReader br = new BufferedReader(fr);
		String linea;
		
		while ((linea = br.readLine()) != null) {
			result.add(linea);
		}
		
		br.close();
		fr.close();
		
		return result;
	}

}
