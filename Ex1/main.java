package Ex1;

import java.util.Scanner;

public class main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String arg[]){
        Matrice matrice = new Matrice();
        matrice.setMatrice();

        System.out.println("\nDeterminant (Ex1)) : " + matrice.getDeterminant());

    }
}
