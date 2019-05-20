package Ex6;

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

    //Remplace la matrice de la classe par celle en paramètre
    public void setMatriceValue(float[][] matriceValue){
      matrice = matriceValue;
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

    /*** A^m ****/

    /**Calcul A^M en fonction des Matrice P et D et de l'exposant placés en paramètre **/
    public void exposant(Matrice P, Matrice D, int exposant) throws TransposeeException {
/*        System.out.print("\nEntrez l'exposant m (A^m): ");
        int exposant = sc.nextInt();*/
        matrice = new float[P.matrice.length][P.matrice.length];
        Matrice pInverse = new Matrice(matrice.length);

        pInverse.setMatriceValue(P.commatrice().matrice);
        System.out.println( "----------- Commatrice de P --------");
        pInverse.showMatrice();
        System.out.println( "-------------------\n");

        pInverse.setMatriceValue(pInverse.transposee());
        System.out.println( "----------- Transposé de la commatrice de P --------");
        pInverse.showMatrice();
        System.out.println( "-------------------\n");

        pInverse.multiplicationCoeff(1/(P.getDeterminant()));
        System.out.println( "----------- Matrice P^-1 --------");
        pInverse.showMatrice();
        System.out.println( "-------------------\n");

        for (int i=0; i<D.matrice.length; i++){
            D.matrice[i][i] = (float) Math.pow(D.matrice[i][i], exposant);
        }

        System.out.println( "----------- Matrice D^m --------");
        D.showMatrice();
        System.out.println( "-------------------\n");


        setMatriceValue(mutiplication(P.matrice, D.matrice));
        setMatriceValue(mutiplication(matrice, pInverse.matrice));
        System.out.println( "----------- Matrice A^m --------");
        showMatrice();
        System.out.println( "-------------------\n");

    }

    /**Mutiplie une matrice par un coefficient**/
    private void multiplicationCoeff(float coeff) {
        for (int i=0; i<matrice.length; i++){
            for (int j=0; j<matrice.length; j++){
                matrice[i][j] = coeff*matrice[i][j];//multiplication de chaque élément par le coefficient
            }
        }
    }

    /**Mutiplie une matrice par un coefficient**/
    private float[][] mutiplication(float[][] a, float[][] b){
        float[][] newMatrice = new float[a.length][a.length];

        for (int i=0; i<a.length; i++){
            for (int j=0; j<a.length; j++){
                newMatrice[i][j]=0;
                for(int k=0 ; k<a.length; k++) {
                    newMatrice[i][j] += a[i][k] * b[k][j]; //mutiplication ligne*colonne
                }
            }
        }

        return newMatrice;
    }

    /**Calcul la commatrice de la matrice actuel**/
    private Matrice commatrice() {
        Matrice comatrice = new Matrice(matrice.length);

        for (int i=0; i<matrice.length; i++){
            for(int j=0; j<matrice.length; j++){
                comatrice.matrice[i][j] = (float) (Math.pow(-1, (i+j)) * getSmaller(i,j).getDeterminant()); // (-1)^(i+j) * determinant des sous matrices
            }
        }
        return comatrice;
    }

    /**************/

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

    /** Transposée **/
    private float[][] getTransposee(){
        int taille = matrice.length;
        float[][] mat = new float[taille][taille];
        for(int i = 0; i < taille; i++)
            mat[i] = new float[taille];

        for(int i = 0; i<taille; i++){
            for(int j = 0; j<taille; j++){
                mat[i][j] = matrice[j][i];
            }
        }
        return mat;
    }

    public float[][] transposee() throws TransposeeException {
        if(getDeterminant() == 0) {
            throw new TransposeeException("Le déterminant est égal à 0, vous ne pouvez pas calculer sa transposée.");
        }
        if(matrice.length < 3){
            throw new TransposeeException("La taille de la matrice est inférieure à 3.");
        }
        return getTransposee();
    }
}
