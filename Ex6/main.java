package Ex6;

import java.util.Scanner;

public class main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String arg[]) throws TransposeeException {
        Matrice matrice = new Matrice();

        Matrice d = new Matrice();
        Matrice p = new Matrice();

        d.setMatrice();
        p.setMatrice();

        matrice.exposant(p, d, 3);
    }
}
