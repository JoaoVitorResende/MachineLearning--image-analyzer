package Principal;

import java.io.File;

import Arvore.CalculoJ48;
import classificador.Classificador;
import extrator_caracteristicas.ExtractCaracteristicas;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class PrincipalController {
	
	@FXML
	private ImageView imageView;

	public double [] caracteristicas_para_classificacao;
	
	public double [] armazena_caracteristicas = new double [5];
	
	double [] porcentagem = new double[2];//classificação
	
	double [] porcentagem_J48 = new double [2];
	
	@FXML
	public void extrairCaracteristicas() {
		ExtractCaracteristicas.extrair();
	}
	
	int c = 0,personagem = 0;
	double classificacao;
	
	@FXML
	public void selecionaImagem() {
		File f = buscaImg();
		if(f != null) {
			Image img = new Image(f.toURI().toString());
			imageView.setImage(img);
			imageView.setFitWidth(img.getWidth());
			imageView.setFitHeight(img.getHeight());
			double[] caracteristicas = ExtractCaracteristicas.extraiCaracteristicas(f);
			caracteristicas_para_classificacao = caracteristicas;
		    armazena_caracteristicas = caracteristicas;
			AplicaValores();
		}
	}
	
	@FXML public Label cabelo_krusty;
	@FXML public Label camisa_krusty;
	
	@FXML public Label cabelo_comic;
	@FXML public Label camisa_comic;
	
	@FXML public Label classificacao_krusty;
	@FXML public Label classificacao_comic;
	
	@FXML public Label classificacao_krusty_J48;
	@FXML public Label classificacao_comic_J48;
	
	
	
	private File buscaImg() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new 
				   FileChooser.ExtensionFilter(
						   "Imagens", "*.jpg", "*.JPG", 
						   "*.png", "*.PNG", "*.gif", "*.GIF", 
						   "*.bmp", "*.BMP")); 	
		 fileChooser.setInitialDirectory(new File("src/imagens"));
		 File imgSelec = fileChooser.showOpenDialog(null);
		 try {
			 if (imgSelec != null) {
			    return imgSelec;
			 }
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		 return null;
	}
	
	public void RetornaClassificacao() {
		
		Classificador cl = new Classificador();
		
		porcentagem = cl.naiveBayes(caracteristicas_para_classificacao);
		
		CalculoJ48 cj = new CalculoJ48();
		
		porcentagem_J48 = cj.j48(caracteristicas_para_classificacao);
		
		//personagem = cl.RetornaClasse();
				
	}	
	
	public String Modifica(double valor) {
		
		
		return String.format("%.2f", valor);
	}
	
	
	public void AplicaValores() {
		
		RetornaClassificacao();
		
		for (int i = 0; i < armazena_caracteristicas.length; i++) {
			
			if(i == 0) {
				camisa_comic.setText(Modifica(armazena_caracteristicas[i]));
			}
			if(i == 1) {
				cabelo_comic.setText(Modifica(armazena_caracteristicas[i]));
			}
			if(i == 2) {
				camisa_krusty.setText(Modifica(armazena_caracteristicas[i]));
			}
			if(i == 3) {
				cabelo_krusty.setText(Modifica(armazena_caracteristicas[i]));
			}
		}
			classificacao_comic.setText(Modifica(porcentagem[0]));
			
			classificacao_krusty.setText(Modifica(porcentagem[1]));
			
			
			classificacao_comic_J48.setText(Modifica(porcentagem_J48[0]));
			
			classificacao_krusty_J48.setText(Modifica(porcentagem_J48[1]));
			
	}
}
