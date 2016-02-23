import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class LaunchMe {

	private int cardinalite;
	private int qid1Min;
	private int qid1Max;
	private int qid2Min;
	private int qid2Max;
	private static int nbValSD=5;
	private int kConfifential;
	
	public static void main(String[] args) {

		//Creation du fichier contenant les Qid
		BufferedWriter bw=null;
		try {
			bw = new BufferedWriter(new FileWriter(new File ( "/private/student/8/18/14011518/monJeuDeDonnees.csv" ),false));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Tableau contenant les sd
		List<String> monJeuDeSD= new ArrayList();
		for(int i=0;i<nbValSD;i++){
			monJeuDeSD.add("SD"+i);
		}
		//Affichage du tableau de SD
		System.out.println(monJeuDeSD.toString());
		//Tableau contenant les données
		List<Nuplet> monJeuDeDonnees= new ArrayList();
		//Génération du jeu de données
		Random randomGenerator = new Random();
		for(int i=0;i<10;i++){
			int randomQid1 = randomGenerator.nextInt(20);
			int randomQid2 = randomGenerator.nextInt(20);
			String randomSd = monJeuDeSD.get(randomGenerator.nextInt(4));
			monJeuDeDonnees.add(new Nuplet(randomQid1,randomQid2,randomSd));
			//Affichage du jeu de données
			System.out.println(monJeuDeDonnees.get(i).toString());
			//Ecriture du fichier contenant les qid
			try {
				bw.append (randomQid1+" "+randomQid2+"\n");
				bw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param jeuDeDonnees Ensemble de n-uplets
	 * @param k paramètre de confidentialité
	 */
	public List<ClasseEquivalence> mondrian(List jeuDeDonnees,int k){
		//Liste contenant les classes d'équivalence
		List<ClasseEquivalence> listClasseEquivalence = new ArrayList();
		//Test si on peut produire 2 sous ensemble d au moins k tuples
		if (jeuDeDonnees.size()<2*k){
			
		}else{
			//Choix de la dimension
			
			//Frequency set
			
			//FindMedian
			
			
		}
		return listClasseEquivalence;
	}
}