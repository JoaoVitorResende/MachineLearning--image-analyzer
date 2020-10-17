package extrator_caracteristicas;

import java.io.File;
import java.io.FileOutputStream;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class ExtractCaracteristicas {

	public static double[] extraiCaracteristicas(File f) {
		
		double[] caracteristicas = new double[5];
		
		/*double laranjaCamisaBart = 0;
		double azulCalcaoBart = 0;
		double azulSapatoBart = 0;
		double azulCalcaHomer = 0;
		double marromBocaHomer = 0;
		double cinzaSapatoHomer = 0; */
		
		double camisa_azul_comic = 0;
		double cabelo_marrom_comic = 0;
		double cor_de_pele_comic = 0;
		double camisa_roxa_krusty = 0;
		double cabelo_azul_krusty = 0;
		double cor_de_pele_krusty = 0;
		
		
		
		Image img = new Image(f.toURI().toString());
		PixelReader pr = img.getPixelReader();
		
		Mat imagemOriginal = Imgcodecs.imread(f.getPath());
        Mat imagemProcessada = imagemOriginal.clone();
		
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		
		
		for(int i=0; i<h; i++) {
			for(int j=0; j<w; j++) {
				
				Color cor = pr.getColor(j,i);
				
				double r = cor.getRed()*255; 
				double g = cor.getGreen()*255;
				double b = cor.getBlue()*255;
				
				if(isCamisaAzulComicGuy(r, g, b)) {
					camisa_azul_comic++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if(i<(h/2 + h/3) && isCabeloMarromComic(r, g, b)) {
					cabelo_marrom_comic++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if (i > (h/2 + h/3) && isCamisaKrusty(r, g, b)) {
					camisa_roxa_krusty++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if(i<(h/2 + h/3) && isCabeloKrusty(r, g, b)) {
					cabelo_azul_krusty++;
					imagemProcessada.put(i, j, new double[]{0, 255, 255});
				}
				/*if(i < (h/2 + h/3) && isBocaHomer(r, g, b)) {
					marromBocaHomer++;
					imagemProcessada.put(i, j, new double[]{0, 255, 255});
				}
				if (i > (h/2 + h/3) && isSapatoHomer(r, g, b)) {
					cinzaSapatoHomer++;
					imagemProcessada.put(i, j, new double[]{0, 255, 255});
				}*/
				
			}
		}
		
		// Normaliza as características pelo número de pixels totais da imagem para %
        camisa_azul_comic = (camisa_azul_comic / (w * h)) * 100;
        cabelo_marrom_comic = (cabelo_marrom_comic / (w * h)) * 100;
        camisa_roxa_krusty = (camisa_roxa_krusty / (w * h)) * 100;
        cabelo_azul_krusty = (cabelo_azul_krusty / (w * h)) * 100;
       // marromBocaHomer = (marromBocaHomer / (w * h)) * 100;
       // cinzaSapatoHomer = (cinzaSapatoHomer / (w * h)) * 100;
        
        caracteristicas[0] = camisa_azul_comic;
        caracteristicas[1] = cabelo_marrom_comic;
        caracteristicas[2] = camisa_roxa_krusty;
        caracteristicas[3] = cabelo_azul_krusty;
        //caracteristicas[4] = marromBocaHomer;
        //caracteristicas[5] = cinzaSapatoHomer;
        //APRENDIZADO SUPERVISIONADO - JÁ SABE QUAL A CLASSE NAS IMAGENS DE TREINAMENTO
        caracteristicas[4] = f.getName().charAt(0) == 'c'? 0:1;
		
//		HighGui.imshow("Imagem original", imagemOriginal);
//        HighGui.imshow("Imagem processada", imagemProcessada);
//        
//        HighGui.waitKey(0);
		
		return caracteristicas;
	}
	
	public static boolean isCamisaAzulComicGuy//imagem inteira
	(double r, double g, double b) {
		 if (b >=100  && b <= 217 &&  g >= 40 && g <= 127 &&  r >= 25 && r <= 50) {                       
         	return true;
         }
		 return false;
	}
	public static boolean isCabeloMarromComic(double r, double g, double b) {//matade para cima
		if (b >= 5 && b <= 12 &&  g >= 45 && g <= 59 &&  r >= 70 && r <= 103) {                       
			return true;
		}
		return false;
	}
	public static boolean isCamisaKrusty(double r, double g, double b) {// imagem inteira
		if (b >= 100 && b <= 168 &&  g >= 80 && g <= 113 &&  r >= 100 && r <= 186) {                       
			return true;
		}
		return false;
	}
	public static boolean isCabeloKrusty(double r, double g, double b) {
		if (b >= 75 && b <= 133 &&  g >= 98 && g <= 121 &&  r >= 0 && r <= 27) {                       
			return true;
		}
		return false;
	}
	/*public static boolean isBocaHomer(double r, double g, double b) {
		if (b >= 95 && b <= 140 &&  g >= 160 && g <= 185 &&  r >= 175 && r <= 200) {                       
			return true;
		}
		return false;
	}*/
	/*public static boolean isSapatoHomer(double r, double g, double b) {
		if (b >= 25 && b <= 45 &&  g >= 25 && g <= 45 &&  r >= 25 && r <= 45) {                       
			return true;
		}
		return false;
	}*/



	public static void extrair() {
				
	    // Cabeçalho do arquivo Weka
		String exportacao = "@relation caracteristicas\n\n";
		exportacao += "@attribute camisa_azul_comic real\n";
		exportacao += "@attribute cabelo_marrom_comic real\n";
		exportacao += "@attribute camisa_roxa_krusty real\n";
		exportacao += "@attribute cabelo_azul_krusty real\n";
		exportacao += "@attribute classe {Comic, Krusty}\n\n";
		exportacao += "@data\n";
	        
	    // Diretório onde estão armazenadas as imagens
	    File diretorio = new File("src\\imagens");
	    File[] arquivos = diretorio.listFiles();
	    
        // Definição do vetor de características
        double[][] caracteristicas = new double[1049][7];
        
        // Percorre todas as imagens do diretório
        int cont = -1;
        for (File imagem : arquivos) {
        	cont++;
        	caracteristicas[cont] = extraiCaracteristicas(imagem);
        	
        	String classe = caracteristicas[cont][4] == 0 ?"Comic":"Krusty";
        	
        	System.out.println("camisa azul comic: " + caracteristicas[cont][0] 
            		+ " - cabelo marrom comic: " + caracteristicas[cont][1] 
            		+ " - camisa roxa krusty: " + caracteristicas[cont][2] 
            		+ " - cabelo azul krusty: " + caracteristicas[cont][3] 
            		+ " - Classe: " + classe);
        	
        	exportacao += caracteristicas[cont][0] + "," 
                    + caracteristicas[cont][1] + "," 
        		    + caracteristicas[cont][2] + "," 
                    + caracteristicas[cont][3] + "," 
                    + classe + "\n";
        }
        
     // Grava o arquivo ARFF no disco
        try {
        	File arquivo = new File("caracteristicas.arff");
        	FileOutputStream f = new FileOutputStream(arquivo);
        	f.write(exportacao.getBytes());
        	f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

}

