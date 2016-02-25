import java.util.List;

public class LaunchMe {

	/*
	 * Nous avons repris pour ce TP l'exemple vu en TD (Codes postaux, Age et diagnostics). 
	 * La classe JeuDeDonnes genere les donnees aleatoirement. 
	 * Nous avons ecrit l'algorithme de Mondrian dans la classe Mondrian. 
	 * Nous l'avons decompose en plusieurs methodes : 
	 * - ChooseDimension
	 * - FindMedian
	 * Nous avons ajouté 2 méthodes pour la question 5 : 
	 * - isParametreK pour verifier le parametre de confidentialite k
	 * - isParametreL pour verifier la l-diversite
	 */
	public static void main(String[] args) {
		int cardinalite;
		int qid1Min=75001;
		int qid1Max=75099;
		int qid2Min=20;
		int qid2Max=40;
		int nbValSD=5;
		int kConfidential=4;
		int lDiversite=2;//ce paramètre permet de fixer le niveau de l-diversité
		
		//Creation d'un jeu de donnees. On determine le nombre de nUplet avec la variable nbData. 
		int nbData=30;
		JeuDeDonnees data=new JeuDeDonnees(qid1Min, qid1Max, qid2Min, qid2Max, nbValSD,nbData);
		//Appel de mondrian
		Mondrian mondrian=new Mondrian(kConfidential, lDiversite, data.getMonJeuDeDonnees());
		//Recuperation de la liste de classe d'equivalence
		List<ClasseEquivalence> ce= mondrian.getListClasseEquivalence();
		System.out.println("\n****************************************************************************");
		System.out.println("**************** du nombre de division ***********************");
		System.out.println("*                                                                          *");
		//Affichage du nombre de divisions
		int nbDiv=ce.size()-1;
		System.out.println("L'algorithme a effectué "+nbDiv+" division(s)");
		System.out.println("*                                                                          *");
		System.out.println("****************************************************************************");
		System.out.println("\n****************************************************************************");
		System.out.println("**************** Affichage des classes d'equivalence ***********************");
		System.out.println("*                                                                          *");
		//Parcours de la liste des classes d'equivalence
		for(ClasseEquivalence i:ce){
			//Affichage de la classe d'equivalence
			System.out.println(i.toString());
		}
		System.out.println("*                                                                          *");
		System.out.println("****************************************************************************");
	}
}