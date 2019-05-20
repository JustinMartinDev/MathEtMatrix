import Ex6.TransposeeException;

import java.util.Scanner;
public class main {
    public static void main(String argv[]) {

        System.out.println("TP Noté Mathématiques par Justin MARTIN et Maeva ESPANA");
        System.out.println("----------------------------\n");
        System.out.println("1: Calcul de determinant, méthode cofacteurs");
        System.out.println("2: Calcul de determinant, méthode combinaisons linéaires (pivot de gauss)");
        System.out.println("3: Calcul de determinant:\n" +
                "| 1  1  1  1  1  |\n" +
                "| a  b  c  d  e  |\n" +
                "| a² b² c² d² e² |\n" +
                "| a^3 ...... e^3 |\n" +
                "| a^4 ...... e^4 |\n"
        );
        System.out.println("4: Calcul de A^-1");
        System.out.println("5: Système de cramer");
        System.out.println("6: Calcul de A^m (Bonus)");

        int input;
        boolean initialized = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez un nombre : ");
        do{
            if(initialized)
                System.out.println("Erreur, Entrez un nouveau nombre");
            initialized = true;
            input = scanner.nextInt();
        }while (input<1 || input>6);

        if(input==1){
            Ex1.Matrice matrice = new Ex1.Matrice();
            matrice.setMatrice();

            System.out.println("\nDeterminant (Ex1)) : " + matrice.getDeterminant());
        }
        else if(input==2){
            Ex2.Matrice matrice = new Ex2.Matrice();
            matrice.setMatrice();

            matrice.pivotGauss();
        }
        else if(input==3){
            Ex3.Matrice matrice = new Ex3.Matrice();
            matrice.setMatrice();

            System.out.println("Determinant de la matrice : " + matrice.detRep());
        }
        else if(input==4){
            Ex4.Matrice matrice = new Ex4.Matrice();
            matrice.setMatrice();

            matrice.setMatrice();

            try {
                matrice.setMatriceValue(matrice.transposee());
            } catch (TransposeeException e) {
                e.printStackTrace();
            }
            System.out.println("\nMatrice tranposee : ");
            matrice.showMatrice();
        }
        else if(input==5){
            Ex5.Matrice matrice = new Ex5.Matrice();
            matrice.setMatrice();
            matrice.setResult();

            matrice.cramer();
        }
        else if(input==6){
            Ex6.Matrice matrice = new Ex6.Matrice();

            System.out.println("1ère matrice : D");
            Ex6.Matrice d = new Ex6.Matrice();

            System.out.println("2ème matrice : P");
            Ex6.Matrice p = new Ex6.Matrice();

            d.setMatrice();
            p.setMatrice();

            try {
                matrice.exposant(p, d, 3);
            } catch (TransposeeException e) {
                e.printStackTrace();
            }
        }
    }
}
