package Ex1;

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

        showMatrice(); /**affichage de la matrice**/
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

    /** 1er méthode utilisée pour le calcule de determinant (commençant à 0,0 ligne par ligne)**/
    public float baseMethode() {
        float sum=0;
        int s;
        if(matrice.length==1){
            return(matrice[0][0]);
        }
        for(int i=0;i<matrice.length;i++){ //pour chaque element de la ligne
            float[][]smaller= new float[matrice.length-1][matrice.length-1];

            /**On calcul la sous matrice **/
            for(int a=1;a<matrice.length;a++){
                for(int b=0;b<matrice.length;b++){
                    if(b<i){
                        smaller[a-1][b]=matrice[a][b];
                    }
                    else if(b>i){
                        smaller[a-1][b-1]=matrice[a][b];
                    }
                }
            }

            /**coeff**/
            if(i%2==0) s=1;
            else s=-1;

            Matrice smallerMatrice = new Matrice();
            smallerMatrice.matrice = smaller;
            sum+=s*matrice[0][i]*(smallerMatrice.baseMethode()); //on appelle la methode de calcul de determinant sur la sous matricec
        }
        return(sum);
    }


    /** Calcul de dertiminant améliorer vise la ligne avec le plus de 0 **/
    public float getDeterminant() {
        // peut-être modifié pour rechercher les 1 ou -1 au lieu des 0
        int targets[] = findBestTarget(0); //Recherche la meilleur colonne ou ligne pour le calcul de determinant
        return getDeterminant(targets[0],targets[1]);
    }

    /** Retourne la meilleur ligne ou colonne a ciblé pour le calcul de determinant**/
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

    /** Compare le nombre de 0 sur les lignes et les colonnes pour savoir si la ligne ou la colonne doit être utilisé  **/
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


    /** Calcule le determinant en fonction de la ligne et colonne passé en paramètres **/
    public float getDeterminant(int colTarget, int ligneTarget) {
        Matrice sousMatrice = null;
        float value = 0;

        if (matrice.length < 3)
            return (getValue(0,0)*getValue(1,1) - getValue(1,0)*getValue(0,1));

        if(ligneTarget>=0 && colTarget<0) { //calcul du determinant par rapport à la ligne choisi
            for (int j = 0; j < matrice.length; j++) {
                sousMatrice = getSmaller(ligneTarget, j); //récupération de la sous matrice
                value += (int) Math.pow(-1, (ligneTarget+1) + j) * (getValue(ligneTarget, j) * sousMatrice.getDeterminant(colTarget, ligneTarget)); //calcul du determinant
            }
        }
        else if(colTarget>=0 && ligneTarget<0){ //calcul du determinant par rapport à la colonne choisi
            for (int j = 0; j < matrice.length; j++) {
                sousMatrice = getSmaller(j, colTarget);
                value += (int) Math.pow(-1, (colTarget) + j) * (getValue(j, colTarget) * sousMatrice.getDeterminant(colTarget, ligneTarget));
            }
        }

        return value;
    }

    private float getValue(int i, int j) {
        return matrice[i][j];
    }

    /** Retourne la sousMatrice par rapport a la ligne et colonne indiquée **/
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
                if (j==columns) { //remplace toutes les colonnes
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


}
