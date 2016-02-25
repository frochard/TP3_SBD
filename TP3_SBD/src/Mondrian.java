import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class Mondrian {

	private int kConfidential;
	private List<Nuplet> data;
	private List<ClasseEquivalence> listClasseEquivalence;

	public List<ClasseEquivalence> getListClasseEquivalence() {
		return listClasseEquivalence;
	}

	public Mondrian(int kConfidential, List<Nuplet> data) {
		this.kConfidential=kConfidential;
		this.data=data;
		//On recupere la taille du jeu de donnees
		int dataSize=data.size();
		//Liste contenant les classes d'Ã©quivalence
		this.listClasseEquivalence = new ArrayList<ClasseEquivalence>();
		//Test si on peut produire 2 sous ensemble d au moins k tuples
		if (isSplittable()){
			System.out.println("no allowable multidimensional cut for partition");
			//Transformation du jeu de données s'il n'est plus divisible en classe d'equivalence
			ClasseEquivalence ce=new ClasseEquivalence(data.get(0).getQid1(),data.get(0).getQid1(),data.get(0).getQid2(),data.get(0).getQid2(),new ArrayList<String>());
			for(int i=0;i<dataSize;i++){
				if(data.get(i).getQid1()<ce.getQid1Min()){
					ce.setQid1Min(data.get(i).getQid1());
				}
				if(data.get(i).getQid1()>ce.getQid1Max()){
					ce.setQid1Max(data.get(i).getQid1());
				}
				if(data.get(i).getQid2()<ce.getQid2Min()){
					ce.setQid2Min(data.get(i).getQid2());
				}
				if(data.get(i).getQid2()>ce.getQid2Max()){
					ce.setQid2Max(data.get(i).getQid2());
				}
				ce.getSdList().add(data.get(i).getSd());
			}
			listClasseEquivalence.add(ce);
		}else{
			//Choix de la dimension
			String dimension=chooseDimension();
			//********* Frequency set **************
			TreeMap<Integer, Integer> frequencySet = new TreeMap<Integer, Integer>();
			List<Nuplet> jeuDeDonneesL=new ArrayList<Nuplet>();
			List<Nuplet> jeuDeDonneesR=new ArrayList<Nuplet>();
			//Test de la plage d'id pour chaque qid. On choisit la dimension la plus large. 
			if(dimension=="qid1"){
				//Parcours du jeu de données pour construire un histogramme dans un tableau associatif
				for(int i=0;i<dataSize;i++){
					//Test si la cle existe dans la Hashtable
					if (frequencySet.containsKey(data.get(i).getQid1())){
						//La clé existe. On recupere le nombre d'occurence
						int nb=frequencySet.get(data.get(i).getQid1());
						frequencySet.put(data.get(i).getQid1(),nb+1);
					}else{
						//On crée la clé dans la hashtable
						frequencySet.put(data.get(i).getQid1(),1);
					}
				}
				System.out.println("*************** Tableau associatif **************");
				System.out.println(frequencySet.toString());
				//************* FindMedian *************
				int KeyMedian=this.findMedian(frequencySet,data);
				//On parcourt les données pour les séparer en 2
				for(int i=0;i<dataSize;i++){
					//Si en dessous médiane, on ajoute au tableau de gauche
					if (data.get(i).getQid1()<=KeyMedian){
						jeuDeDonneesL.add(data.get(i));
					}else{
						jeuDeDonneesR.add(data.get(i));
					}
				}
			}else{
				//Parcours du jeu de données pour construire un histogramme dans un tableau associatif
				for(int i=0;i<dataSize;i++){
					//Test si la clé existe dans la Hashtable
					if (frequencySet.containsKey(data.get(i).getQid2())){
						//La clé existe. On recupere le nombre d'occurence
						int nb=frequencySet.get(data.get(i).getQid2());
						frequencySet.put(data.get(i).getQid2(),nb+1);
					}else{
						//On crée la clé dans la hashtable
						frequencySet.put(data.get(i).getQid2(),1);
					}
				}
				System.out.println("*************** Tableau associatif **************");
				System.out.println(frequencySet.toString());
				//************* FindMedian *************
				int KeyMedian=this.findMedian(frequencySet,data);
				//On parcourt les données pour les séparer en 2
				for(int i=0;i<dataSize;i++){
					//Si en dessous médiane, on ajoute au tableau de gauche
					if (data.get(i).getQid2()<=KeyMedian){
						jeuDeDonneesL.add(data.get(i));
					}else{
						jeuDeDonneesR.add(data.get(i));
					}
				}
			}
			System.out.println("*************** jeuDeDonneesL **************");
			System.out.println(jeuDeDonneesL.toString());
			System.out.println("*************** jeuDeDonneesR **************");
			System.out.println(jeuDeDonneesR.toString());
			//Appel recursif
			Mondrian listClasseEquivalenceL = new Mondrian(this.kConfidential,jeuDeDonneesL);
			Mondrian listClasseEquivalenceR = new Mondrian(this.kConfidential,jeuDeDonneesR);
			//Ajout à la liste de classes d'equivalence les listes de classe d'equivalence retournees pour les sous ensembles
			this.listClasseEquivalence.addAll(listClasseEquivalenceL.getListClasseEquivalence());
			this.listClasseEquivalence.addAll(listClasseEquivalenceR.getListClasseEquivalence());
		}
	}

	public int findMedian(TreeMap<Integer, Integer> frequencySet, List<Nuplet> jeuDeDonnees){
		//Parcours du tableau associatif
		int median=0;
		int KeyMedian=0;
		Iterator<Integer> itKey = frequencySet.keySet().iterator();
		//On parcourt l'histogramme jusqu'a atteindre la valeur mediane
		while (itKey.hasNext() & median<jeuDeDonnees.size()/2){
			KeyMedian = (int) itKey.next();
			int valeur = frequencySet.get(KeyMedian);
			median+=valeur;
		}
		//Affichage de la valeur de la mediane
		System.out.println("La médiane est "+KeyMedian);
		return KeyMedian;
	}

	public boolean isSplittable(){
		boolean splittable=true;
		//On recupere la taille du jeu de donnees
		int dataSize=this.data.size();
		if(dataSize<2*this.kConfidential){
			splittable=false;
		}else{
			splittable=true;
		}
		return splittable;
	}

	public String chooseDimension(){
		//On recupere la taille du jeu de donnees
		int dataSize=this.data.size();

		String dimension="";
		int id1Min=this.data.get(0).getQid1();
		int id1Max=this.data.get(0).getQid1();
		int id2Min=this.data.get(0).getQid2();
		int id2Max=this.data.get(0).getQid2();
		//On parcourt le jeu de données pour obtenir les mini et maxi des qid
		for(int i=0;i<dataSize;i++){
			if(this.data.get(i).getQid1()<id1Min){
				id1Min=this.data.get(i).getQid1();
			}
			if(this.data.get(i).getQid1()>id1Max){
				id1Max=this.data.get(i).getQid1();
			}
			if(data.get(i).getQid2()<id2Min){
				id2Min=this.data.get(i).getQid2();
			}
			if(this.data.get(i).getQid2()>id2Max){
				id2Max=this.data.get(i).getQid2();
			}
		}
		//On calcule la plage pour chaque qid
		int plageQid1 = id1Max-id1Min;
		int plageQid2 = id2Max-id2Min;
		//On retourne la dimension la plus large
		if (plageQid1>=plageQid2){
			dimension="qid1";
		}else{
			dimension="qid2";			
		}
		return dimension;
	}
}