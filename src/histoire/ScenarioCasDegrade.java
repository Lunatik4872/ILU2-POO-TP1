package histoire;
import villagegaulois.Etal;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Etal etal = new Etal();
		etal.libererEtal();
		try {
            etal.acheterProduit(1, null);
        } catch (IllegalArgumentException e) {
            System.out.println("Une exception a ete lancee : " + e.getMessage());
        } catch (IllegalStateException e) {
        	 System.out.println("Une exception a ete lancee : " + e.getMessage());
        }
		System.out.println("Fin du test");
	}

}
