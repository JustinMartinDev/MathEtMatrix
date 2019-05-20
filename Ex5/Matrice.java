package Ex5;

import java.util.Scanner;

public class Matrice {

    private float matrice[][];
    private float result[];
    private Scanner sc = new Scanner(System.in);

    /********* Methode Principale ********/
    public Matrice() { }

    //Initialise la taille de la matrice
    public Matrice(int taille) {
        matrice = new float[taille][taille];
    }

    //Permet d'entrer la matrice
    public void setMatrice(){
        int taille=0;
        do {
            System.out.println("Entrez la taille de la matrice");
            taille = sc.nextInt();
        }while(taille<2);

        matrice = new float[taille][taille];

        for(int i = 0; i < matrice.length; i++)
            matrice[i] = new float[taille];

        for(int i = 0; i < matrice.length; i++){
            for(int j = 0; j < matrice[i].length; j++){
                System.out.println("Entrez la valeur(" +i+ "," +j+ ");");
                matrice[i][j] = sc.nextFloat();
            }
        }

        System.out.println("\nMatrice créé avec sucess");

        showMatrice();
    }

    //Permet d'afficher
    private void showMatrice() {
        for(int i = 0; i < matrice.length; i++){
            for(int j = 0; j < matrice[i].length; j++){
                System.out.print(matrice[i][j] + " ");
            }
            System.out.println();
        }
    }
    /*******************************************/


    /** Calcul deternimant améliorée **/
    public float getDeterminant() {

        int targets[] = findBestTarget(0);
        return getDeterminant(targets[0],targets[1]);
    }

    private int[] findBestTarget(float gottaLookFor) {

        /** Recherche de l'element **/
        int[] nbLigne = new int[matrice.length];
        int[] nbColonne = new int[matrice.length];

        for(int i = 0; i<matrice.length; i++) {
            for (int j = 0; j < matrice.length; j++) {
                if (matrice[i][j] == gottaLookFor) {
                    nbLigne[i] += 1; //incrementation quand trouvé
                    nbColonne[j] += 1;
                }
            }
        }

        return compare(nbLigne, nbColonne);
    }

    private int[] compare(int[] nbLigne, int[] nbColonne) {
        int bestIndexLigne = 0;
        int bestIndexColonne = 0;

        for (int i = 0; i<nbLigne.length; i++){
            if(nbLigne[i] > nbLigne[bestIndexLigne])
                bestIndexLigne=i;

            if(nbColonne[i] > nbColonne[bestIndexColonne])
                bestIndexColonne = i;
        }

        if(nbColonne[bestIndexColonne] >= nbLigne[bestIndexLigne])
            return new int[]{bestIndexColonne, -1};
        else
            return new int[]{-1, bestIndexLigne};
    }

    /**
     * Calcule le determinant en fonction de la ligne et colonne passé en paramètres     * @param colTarget
     * @param ligneTarget
     * @return
     */
    public float getDeterminant(int colTarget, int ligneTarget) {
       Matrice sousMatrice = null;
        float value = 0;

        if (matrice.length < 3)
            return (getValue(0,0)*getValue(1,1) - getValue(1,0)*getValue(0,1));

        if(ligneTarget>=0 && colTarget<0) {
            for (int j = 0; j < matrice.length; j++) {
                sousMatrice = getSmaller(ligneTarget, j);
                value += (int) Math.pow(-1, ligneTarget + j) * (getValue(ligneTarget, j) * sousMatrice.getDeterminant(colTarget, ligneTarget));
            }
        }
        else if(colTarget>=0 && ligneTarget<0){
            for (int j = 0; j < matrice.length; j++) {
                sousMatrice = getSmaller(j, colTarget);
                value += (int) Math.pow(-1, colTarget + j) * (getValue(j, colTarget) * sousMatrice.getDeterminant(colTarget, ligneTarget));
            }
        }

        return value;
    }

    private float getValue(int i, int j) {
        return matrice[i][j];
    }

    private Matrice getSmaller(int row, int columns) {
        Matrice a = new Matrice(matrice.length-1);
        int k = -1, m = 0;

        for (int i=0; i<matrice.length; i++) { //ligne
            k++;
            if (i == row) {
                k--;
                continue;
            }
            m = -1;

            for (int j=0; j<this.matrice.length; j++) { //colonne
                m++;
                if (j==columns) {
                    m--;
                    continue;
                }
                a.setValue(k,m,this.getValue(i,j));
            }
        }
        return a;
    }

    private void setValue(int k, int m, float value) {
        matrice[k][m] = value;
    }

    /************************************/


    /**Cramer **/

    /**Permet de verifié si le système est de cramer ou non**/
    public void cramer(){
        float determinant = getDeterminant(); //calcul determinant
        if(determinant == 0){
            System.out.println("Helas le système que vous avez rentré n'est pas de cramer ");
        }
        else {
            System.out.println("Système de cramer\nCalcul de A^-1 en cours\n");
            calcCramer(determinant); //calcul des solution du système
        }
    }

    /**Permet de calculer les solution du système AX=B **/
    private void calcCramer(float determinant) {
        System.out.println("--------------------------------------\nResultat : ");

        showResume();

        System.out.println("Il existe une unique solution (matrice ci-dessous) : \n");

        float solution[] = new float[matrice.length];

        //Calcul chaque element de solution (x1, x2, x3,...)
        for (int k=0; k<result.length; k++){
            Matrice tmpMatrice = new Matrice(matrice.length);

            for (int i=0; i<matrice.length; i++) {
                for (int j=0; j<matrice.length; j++){
                    if(j==k) tmpMatrice.matrice[i][j] = result[i]; //si la colonne correspond à l'indice de x alors on remplace la colonne par la colonne des résultats
                    else tmpMatrice.matrice[i][j] = matrice[i][j]; //sinon on garde la matrice actuel
                }
            }
            solution[k] =((tmpMatrice.getDeterminant())/determinant); //calcul de la solution xi
        }

        //Affichage de la solution
        for (int l=0; l<solution.length; l++){
            System.out.println("x"+l+" = " + solution[l]);
        }
    }

    /**Affichage résumé du système **/
    private void showResume() {
        System.out.println("Dans l'équation suivante AX=B \n avec :\n");
        System.out.println("A = ");
        showMatrice();
        System.out.println("\nX = ");
        for (int i=0; i<matrice.length; i++){
            System.out.println("x"+i);
        }
        System.out.println("\nB = ");
        for (int i=0; i<matrice.length; i++){
            System.out.println(result[i]);
        }
    }

    /**Permet de renter la matrice resultat B (AX=B)**/
    public void setResult(){
        System.out.println("Rentrez la matrice résultat B de (AX=B)");

        result = new float[matrice.length];

        for(int i = 0; i < result.length; i++){
            System.out.println("Entrez la valeur(0," + i+")" );
            result[i] = sc.nextFloat();
        }
    }
}
