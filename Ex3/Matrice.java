package Ex3;

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
    private void showMatrice() {
        for(int i = 0; i < matrice.length; i++){
            for(int j = 0; j < matrice[i].length; j++){
                System.out.print(matrice[i][j] + " ");
            }
            System.out.println();
        }
    }
    /*******************************************/

    /*** Calcul determinant Ex3 ***/

    public float detRep(){
        float a,b,c,d,e;
        a = matrice[1][0];
        b = matrice[1][1];
        c = matrice[1][2];
        d = matrice[1][3];
        e = matrice[1][4];
        float det = (b-a)*(c-a)*(d-a)*(e-a)*(c-b)*(d-b)*(e-b)*(d-c)*(e-c)*(e-d);
        return det;
    }

    /***************/
}
