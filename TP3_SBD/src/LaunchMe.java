import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;


public class LaunchMe {

	private int cardinalite;
	private static int qid1Min=75001;
	private static int qid1Max=75020;
	private static int qid2Min=20;
	private static int qid2Max=40;
	private static int nbValSD=5;
	private static int kConfifential=5;

	//Attributs utilisés pour la génération des données
	private static String fichierCsv="monJeuDeDonnees.csv";//"/private/student/8/18/14011518/monJeuDeDonnees.csv";
	private static int nbData=25;

	public static void main(String[] args) {
		
		//Creation du fichier contenant les Qid
		BufferedWriter bw=null;
		try {
			bw = new BufferedWriter(new FileWriter(new File (fichierCsv),false));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//Tableau contenant les sd
		List<String> monJeuDeSD= new ArrayList();
		for(int i=0;i<nbValSD;i++){
			monJeuDeSD.add("SD"+i);
		}
		//Affichage du tableau de SD
		System.out.println(monJeuDeSD.toString());
		//Tableau contenant les donnÃ©es
		List<Nuplet> monJeuDeDonnees= new ArrayList();
		//GÃ©nÃ©ration du jeu de donnÃ©es
		Random randomGenerator = new Random();
		for(int i=0;i<nbData;i++){
			//Génération des qid de façon aléatoire
			int randomQid1 = qid1Min + randomGenerator.nextInt(qid1Max - qid1Min);
			int randomQid2 = qid2Min + randomGenerator.nextInt(qid2Max - qid2Min);
			String randomSd = monJeuDeSD.get(randomGenerator.nextInt(4));
			monJeuDeDonnees.add(new Nuplet(randomQid1,randomQid2,randomSd));
			//Affichage du jeu de donnÃ©es
			System.out.println(monJeuDeDonnees.get(i).toString());
			//Ecriture du fichier contenant les qid
			try {
				bw.append (randomQid1+" "+randomQid2+"\n");
				bw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Appel de mondrian
		List<ClasseEquivalence> ce= mondrian(monJeuDeDonnees,kConfifential);
		System.out.println(ce.toString());
	}

	/**
	 * 
	 * @param jeuDeDonnees Ensemble de n-uplets
	 * @param k paramÃ¨tre de confidentialitÃ©
	 */
	public static List<ClasseEquivalence> mondrian(List<Nuplet> jeuDeDonnees,int k){
		//Liste contenant les classes d'Ã©quivalence
		List<ClasseEquivalence> listClasseEquivalence = new ArrayList();
		//Test si on peut produire 2 sous ensemble d au moins k tuples
		if (jeuDeDonnees.size()<2*k){
			System.out.println("no allowable multidimensional cut for partition");
			//Transformation du jeu de données s'il n'est plus divisible en classe d'equivalence
			ClasseEquivalence ce=new ClasseEquivalence(qid1Max,qid1Min,qid2Max,qid2Min,null);
			for(int i=0;i<jeuDeDonnees.size();i++){
				if(jeuDeDonnees.get(i).getQid1()<ce.getQid1Min()){
					ce.setQid1Min(jeuDeDonnees.get(i).getQid1());
				}
				if(jeuDeDonnees.get(i).getQid1()>ce.getQid1Max()){
					ce.setQid1Max(jeuDeDonnees.get(i).getQid1());
				}
				if(jeuDeDonnees.get(i).getQid2()<ce.getQid2Min()){
					ce.setQid2Min(jeuDeDonnees.get(i).getQid2());
				}
				if(jeuDeDonnees.get(i).getQid2()>ce.getQid2Max()){
					ce.setQid2Max(jeuDeDonnees.get(i).getQid2());
				}
			}
			listClasseEquivalence.add(ce);
		}else{
			//Choix de la dimension
			int id1Min=qid1Max;
			int id1Max=qid1Min;
			int id2Min=qid2Max;
			int id2Max=qid2Min;
			//On parcourt le jeu de données pour obtenir les mini et maxi des qid
			for(int i=0;i<jeuDeDonnees.size();i++){
				if(jeuDeDonnees.get(i).getQid1()<id1Min){
					id1Min=jeuDeDonnees.get(i).getQid1();
				}
				if(jeuDeDonnees.get(i).getQid1()>id1Max){
					id1Max=jeuDeDonnees.get(i).getQid1();
				}
				if(jeuDeDonnees.get(i).getQid2()<id2Min){
					id2Min=jeuDeDonnees.get(i).getQid2();
				}
				if(jeuDeDonnees.get(i).getQid2()>id2Max){
					id2Max=jeuDeDonnees.get(i).getQid2();
				}
			}
			//On calcule la plage pour chaque qid
			int plageQid1 = id1Max-id1Min;
			int plageQid2 = id2Max-id2Min;
			//Test de la plage d'id pour chaque qid. On choisit la dimension la plus large. 
			//********* Frequency set **************
			TreeMap<Integer, Integer> frequencySet = new TreeMap<Integer, Integer>();
			List<Nuplet> jeuDeDonneesL=new ArrayList();
			List<Nuplet> jeuDeDonneesR=new ArrayList();
			//Test de la plage des dimensions
			if(plageQid1>=plageQid2){
				//Parcours du jeu de données pour construire un histogramme dans un tableau associatif
				for(int i=0;i<jeuDeDonnees.size();i++){
					//Test si la clé existe dans la Hashtable
					if (frequencySet.containsKey(jeuDeDonnees.get(i).getQid1())){
						//La clé existe. On recupere le nombre d'occurence
						int nb=frequencySet.get(jeuDeDonnees.get(i).getQid1());
						frequencySet.put(jeuDeDonnees.get(i).getQid1(),nb+1);
					}else{
						//On crée la clé dans la hashtable
						frequencySet.put(jeuDeDonnees.get(i).getQid1(),1);
					}
				}
				System.out.println("*************** Tableau associatif **************");
				System.out.println(frequencySet.toString());
				//************* FindMedian *************
				//Parcours du tableau associatif
				int median=0;
				int KeyMedian=0;
				Iterator<Integer> itKey = frequencySet.keySet().iterator();

				while (itKey.hasNext() & median<jeuDeDonnees.size()/2){
					KeyMedian = (int) itKey.next();
					int valeur = frequencySet.get(KeyMedian);
					median+=valeur;
				}
				System.out.println("La médiane est "+KeyMedian);
				
				//On parcourt les données pour les séparer en 2
				for(int i=0;i<jeuDeDonnees.size();i++){
					//Si en dessous médiane, on ajoute au tableau de gauche
					if (jeuDeDonnees.get(i).getQid1()<=KeyMedian){
						jeuDeDonneesL.add(jeuDeDonnees.get(i));
					}else{
						jeuDeDonneesR.add(jeuDeDonnees.get(i));
					}
				}

			}else{
				//Parcours du jeu de données pour construire un histogramme dans un tableau associatif
				for(int i=0;i<jeuDeDonnees.size();i++){
					//Test si la clé existe dans la Hashtable
					if (frequencySet.containsKey(jeuDeDonnees.get(i).getQid2())){
						//La clé existe. On recupere le nombre d'occurence
						int nb=frequencySet.get(jeuDeDonnees.get(i).getQid2());
						frequencySet.put(jeuDeDonnees.get(i).getQid2(),nb+1);
					}else{
						//On crée la clé dans la hashtable
						frequencySet.put(jeuDeDonnees.get(i).getQid2(),1);
					}
				}
				System.out.println("*************** Tableau associatif **************");
				System.out.println(frequencySet.toString());
				//************* FindMedian *************
				//Parcours du tableau associatif
				int median=0;
				int KeyMedian=0;
				Iterator<Integer> itKey = frequencySet.keySet().iterator();

				while (itKey.hasNext() & median<jeuDeDonnees.size()/2){
					KeyMedian = (int) itKey.next();
					int valeur = frequencySet.get(KeyMedian);
					median+=valeur;
				}
				System.out.println("La médiane est "+KeyMedian);
				
				//On parcourt les données pour les séparer en 2
				for(int i=0;i<jeuDeDonnees.size();i++){
					//Si en dessous médiane, on ajoute au tableau de gauche
					if (jeuDeDonnees.get(i).getQid2()<=KeyMedian){
						jeuDeDonneesL.add(jeuDeDonnees.get(i));
					}else{
						jeuDeDonneesR.add(jeuDeDonnees.get(i));
					}
				}
			}
			System.out.println("*************** jeuDeDonneesL **************");
			System.out.println(jeuDeDonneesL.toString());
			System.out.println("*************** jeuDeDonneesR **************");
			System.out.println(jeuDeDonneesR.toString());
			
			List<ClasseEquivalence> listClasseEquivalenceL = mondrian(jeuDeDonneesL,k);
			List<ClasseEquivalence> listClasseEquivalenceR = mondrian(jeuDeDonneesR,k);
			listClasseEquivalence.addAll(listClasseEquivalenceL);
			listClasseEquivalence.addAll(listClasseEquivalenceR);
		}
		
		return listClasseEquivalence;
	}
}