package Ex3;

import java.util.Scanner;

public class main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String arg[]){
        Matrice matrice = new Matrice();
        matrice.setMatrice();

        System.out.println("Determinant de la matrice : " + matrice.detRep());
    }
}
