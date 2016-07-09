import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;

public class Main {

	private static final String CSV_SEPARATOR = ",";
	private static final String newLine = System.getProperty("line.separator");

	public static void main(String[] args) {

		String ano_ = "";
		String cargo_ = "";
		String partido_ = "";
		String estado_ = "";
		String municipio_ = "";
		String eleito_ = "";
		
		String outputPath = "";
		Integer numOfPages = 0;
		
		for (String arg : args){
			String[] args_ = arg.split(":");
			
			String key = args_[0];
			String value = "";
			
			for (String v : args_){
				if (!v.equals(key)){
					if (value.isEmpty())
						value = v;
					else
						value += ":" + v;
				}
			}
			
			if (key.equals("outputPath")){
				outputPath = value;
			} else if (key.equals("ano")){
				ano_ = value;
			} else if (key.equals("cargo")){
				cargo_ = value;
			} else if (key.equals("partido")){
				partido_ = value;
			} else if (key.equals("estado")){
				estado_ = value;
			} else if (key.equals("municipio")){
				municipio_ = value;
			} else if (key.equals("eleito")){
				eleito_ = value;
			} else if (key.equals("numOfPages")){
				numOfPages = Integer.parseInt(value);
			}
		}
		

		URL url;

		List<DataPattern> data = new ArrayList<>();

		try {
			for (int i = 0; i < numOfPages; i += 10) {

				// get URL content

				String a = "http://www.asclaras.org.br/partes/index/@candidatos_frame.php?ano="
				+ ano_ + "&cargo=" + cargo_ + "&partido=" + partido_ + "&estado="
				+ estado_ + "&municipio=" + municipio_ + "&eleito=" + eleito_ + "&CAoffset="
				+ i;
				
				url = new URL(a);
				URLConnection conn = url.openConnection();

				System.out.println(a);

				// open the stream and put it into BufferedReader
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				StringBuilder sb = new StringBuilder();

				String inputLine;
				while ((inputLine = br.readLine()) != null) {
					sb.append(inputLine);
					// System.out.println(inputLine);
				}
				br.close();

				Document doc = Jsoup.parse(sb.toString());

				Element e = doc.select("table").first();

				if (e != null) {

					for (int j = 1; j <= 2; j++) {

						Iterator<Element> attrs = e.select("td[class=linhas" + j).iterator();

						if (attrs != null) {
							int aux = 0;

							String nome = "";
							String cargo = "";
							String municipio = "";
							String partido = "";
							String votos = "";
							String receita = "";
							String receita_de_comites = "";
							String custo_do_voto = "";
							String resultado = "";

							while (attrs.hasNext()) {
								String text = attrs.next().text();
								text = text.replace(",", " ");
								
								if (aux == 0) {
									nome = text;
								} else if (aux == 1) {
									cargo = text;
								} else if (aux == 2) {
									municipio = text;
								} else if (aux == 3) {
									partido = text;
								} else if (aux == 4) {
									votos = text;
								} else if (aux == 5) {
									receita = text;
								} else if (aux == 6) {
									receita_de_comites = text;
								} else if (aux == 7) {
									custo_do_voto = text;
								} else if (aux == 8) {
									resultado = text;
								}
								
								aux++;
								
								if (aux == 9){
									DataPattern d = new DataPattern(nome, cargo, municipio, partido, votos, receita,
											receita_de_comites, custo_do_voto, resultado);
									data.add(d);
									
									aux = 0;
								}
							}
							
							
						}
					}
				} else {
					System.out.println("Could not find table on this one.");
				}

				System.out.println("Done");
			}

			writeCSV(outputPath, data);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeCSV(String outputPath, List<DataPattern> list) {

		String csvContent = "Nome,Cargo,Município,Partido,Votos,Receitas,Receitas de Comitês,Custo do Voto,Resultado";
		for (DataPattern dp : list) {

			csvContent += newLine + dp.getNome() + "," + dp.getCargo() + "," + dp.getMunicipio() + "," + dp.getPartido()
					+ "," + dp.getVotos() + "," + dp.getReceita() + "," + dp.getReceita_de_comites() + ","
					+ dp.getCusto_do_voto() + "," + dp.getResultado();

			try (Writer writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputPath), "utf-8"))) {
				writer.write(csvContent);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			}

		}

	}
}
