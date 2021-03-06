package Ex2;

import java.util.Scanner;

public class Matrice {

    private float matrice[][];
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
    public void showMatrice() {
        for(int i = 0; i < matrice.length; i++){
            for(int j = 0; j < matrice[i].length; j++){
                System.out.print(matrice[i][j] + " ");
            }
            System.out.println();
        }
    }
    /*******************************************/

    /** Pivot de Gauss par défaut**/
    public void pivotGauss(){
        pivotGauss(-1, -1);
    }

    public void pivotGauss(int colException, int ligneException){

        for (int i=0; i<matrice.length; i++){
            for (int j=0; j<matrice.length; j++){
                if(matrice[i][j] == 0) continue;
                else if(i==ligneException || j==colException) continue;
                else {
                    float pivot = matrice[i][j];
                    calcPivotligne(i, j);
                    return;
                }
            }
        }
    }

    void calcPivotligne(int indexLignePivot, int indexColonnePivot){
        Matrice newMatrice = new Matrice(matrice.length);

        for(int j=0; j<matrice.length; j++)
            newMatrice.matrice[indexLignePivot][j] = matrice[indexLignePivot][j]/matrice[indexLignePivot][indexColonnePivot]; //ligne pivot divisée par le pivot

        for (int i=1; i<matrice.length; i++) //colonne pivot à 0 (sauf pivot)
            newMatrice.matrice[i][indexColonnePivot] = 0;

        methodeRectangle(newMatrice, indexLignePivot, indexColonnePivot);
    }

    /** Methode du rectangle permettant de reduire x ou y ou z**/
    private void methodeRectangle(Matrice newMatrice, int indexLignePivot, int indexColonnePivot) {
        for(int j=indexColonnePivot+1; j<matrice.length; j++) {
            for (int i = indexLignePivot + 1; i<matrice.length; i++) {
                newMatrice.matrice[i][j] = (matrice[i][j] - (matrice[i][indexColonnePivot] * matrice[indexLignePivot][j])) / matrice[indexLignePivot][indexColonnePivot];
            }
        }

        if(newMatrice.check0()) {
            System.out.println("Matrice après pivot de gauss");
            newMatrice.showMatrice();
            System.out.println("\nDeterminant méthode combinaison : " + newMatrice.getDeterminant());
        }
        else newMatrice.pivotGauss(indexLignePivot, indexColonnePivot);
    }

    /** Verifie si il y n-1 0 sur une colonne ou ligne **/
    private boolean check0(){

        /** Recherche de l'element **/
        int[] nbLigne = new int[matrice.length];
        int[] nbColonne = new int[matrice.length];

        for(int i = 0; i<matrice.length; i++) {
            for (int j = 0; j < matrice.length; j++) {
                if (matrice[i][j] == 0) {
                    nbLigne[i] += 1; //incrementation quand trouvé
                    nbColonne[j] += 1;
                }
            }
        }

        for (int k=0; k<nbColonne.length; k++){ //test si sur une ligne ou colonne on a n-1 zéros ou plus, si oui on stop
            if((nbLigne[k]>=matrice.length-1) || nbColonne[k]>=matrice.length-1) return true;
        }
        return false;
    }
    /********************/


    /** Calcul deternimant améliorée **/
    public float getDeterminant() {

        int targets[] = findBestTarget(0);
        System.out.println("\nColonne : " + targets[0] + "\nLigne : " + targets[1]);
        System.out.println("Si colonne ou ligne >=0 alors le determinant est calculé par rapport à la ligne ou colonne indiqué");
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
}
