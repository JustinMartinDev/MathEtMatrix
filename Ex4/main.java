package Ex4;

import Ex6.TransposeeException;

public class main {

    public static void main(String arg[]) throws TransposeeException {
        Matrice matrice = new Matrice();
        matrice.setMatrice();

        matrice.setMatriceValue(matrice.transposee());
        System.out.println("\nMatrice tranposee : ");
        matrice.showMatrice();

    }
}
