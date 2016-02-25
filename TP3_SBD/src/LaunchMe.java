import java.util.List;

public class LaunchMe {

	public static void main(String[] args) {
		int cardinalite;
		int qid1Min=75001;
		int qid1Max=75020;
		int qid2Min=20;
		int qid2Max=30;
		int nbValSD=5;
		int kConfidential=5;

		//Creation d'un jeu de donnees
		JeuDeDonnees data=new JeuDeDonnees(qid1Min, qid1Max, qid2Min, qid2Max, nbValSD);
		
		//Appel de mondrian
		Mondrian mondrian=new Mondrian(kConfidential, data.getMonJeuDeDonnees());
		
		//Recuperation de la liste de classe d'equivalence
		List<ClasseEquivalence> ce= mondrian.getListClasseEquivalence();
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