import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JeuDeDonnees {

	//Attributs utilisés pour la génération des données
	private int qid1Min;
	private int qid1Max;
	private int qid2Min;
	private int qid2Max;
	private int nbValSD;
	private String fichierCsv="monJeuDeDonnees.csv";
	private int nbData=60;
	private List<Nuplet> monJeuDeDonnees;
	
	public JeuDeDonnees(int qid1Min, int qid1Max, int qid2Min, int qid2Max, int nbValSD) {
		this.qid1Min = qid1Min;
		this.qid1Max = qid1Max;
		this.qid2Min = qid2Min;
		this.qid2Max = qid2Max;
		this.nbValSD = nbValSD;
		//Creation du fichier contenant les Qid
		BufferedWriter bw=null;
		try {
			bw = new BufferedWriter(new FileWriter(new File (fichierCsv),false));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//Tableau contenant les sd
		List<String> monJeuDeSD= new ArrayList<String>();
		//On remplit le tableau de SD
		monJeuDeSD.add("Rhume");
		monJeuDeSD.add("Grippe");
		monJeuDeSD.add("Angine");
		monJeuDeSD.add("Angine blanche");
		monJeuDeSD.add("Angine rouge");
		//Affichage du tableau de SD
		System.out.println("**************** Liste des SD ****************");
		System.out.println(monJeuDeSD.toString());
		//Tableau contenant les data
		this.monJeuDeDonnees= new ArrayList<Nuplet>();
		//Generation du jeu de donnees
		Random randomGenerator = new Random();
		System.out.println("*************** jeu de donnees **************");
		for(int i=0;i<nbData;i++){
			//Génération des qid de façon aléatoire entre les bornes définies en attribut
			int randomQid1 = qid1Min + randomGenerator.nextInt(qid1Max - qid1Min);
			int randomQid2 = qid2Min + randomGenerator.nextInt(qid2Max - qid2Min);
			//Selection aleatoire dans le tableau de SD
			String randomSd = monJeuDeSD.get(randomGenerator.nextInt(nbValSD-1));
			this.monJeuDeDonnees.add(new Nuplet(randomQid1,randomQid2,randomSd));
			//Affichage du jeu de donnees
			System.out.println(this.monJeuDeDonnees.get(i).toString());
			//Ecriture du fichier externe contenant les qid
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
	}

	public List<Nuplet> getMonJeuDeDonnees() {
		return monJeuDeDonnees;
	}

	public int getQid1Min() {
		return qid1Min;
	}

	public int getQid1Max() {
		return qid1Max;
	}

	public int getQid2Min() {
		return qid2Min;
	}

	public int getQid2Max() {
		return qid2Max;
	}

	public int getNbValSD() {
		return nbValSD;
	}

	public String getFichierCsv() {
		return fichierCsv;
	}

	public int getNbData() {
		return nbData;
	}

	public JeuDeDonnees(List<Nuplet> monJeuDeDonnees) {
		super();
		this.monJeuDeDonnees = monJeuDeDonnees;
	}
	
}
