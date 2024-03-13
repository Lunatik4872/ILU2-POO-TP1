package villagegaulois;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtal);
	}
	
	private class Marche {
		private Etal[] etals;

		public Marche(int nbEtal) {
			etals = new Etal[nbEtal];
			for (int i = 0; i < nbEtal; i++) {
				etals[i] = new Etal();
			}
		}

		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);   
		}

		public int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}

		public Etal[] trouverEtals(String produit) {
			if (etals.length == 0) {
				return new Etal[0];
			}
			int count = 0;
			for (Etal etal : etals) {
				if (etal != null && etal.contientProduit(produit)) {
					count++;
				}
			}
			Etal[] etalsAvecProduit = new Etal[count];
			int index = 0;
			for (Etal etal : etals) {
				if (etal != null && etal.contientProduit(produit)) {
					etalsAvecProduit[index] = etal;
					index++;
				}
			}
			return etalsAvecProduit;
		}		
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for (Etal etal : etals) {
				if (etal.getVendeur() == gaulois) {
					return etal;
				}
			}
			return null;
		}

		public String afficherMarche() {
			StringBuilder sb = new StringBuilder();
			int nbEtalVide = 0;
			for (Etal etal : etals) {
				if (!etal.isEtalOccupe()) {
					nbEtalVide++;
				} else {
					sb.append(etal.afficherEtal()).append("\n");
				}
			}
			if (nbEtalVide > 0) {
				sb.append("Il reste ").append(nbEtalVide).append(" étals non utilisés dans le marché.\n");
			}
			return sb.toString();
		}
	}

	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] tab = marche.trouverEtals(produit);
		
		if (tab.length == 0) {
			return "Il n'y a pas de vendeur qui propose des " + produit + " au marché.";
		} else if (tab.length == 1) {
			return "Seul le vendeur " + (tab[0].getVendeur()).getNom() + " propose des " + produit + " au marché.";
		} else {
			chaine.append("Les vendeurs qui proposent des ").append(produit).append(" sont :\n");
			for (int i = 0; i < tab.length; i++) {
				chaine.append("  - ").append((tab[i].getVendeur()).getNom()).append("\n");
			}
			return chaine.toString();
		}
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		System.out.println(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit +".");
		int indiceEtalLibre = marche.trouverEtalLibre();
		if (indiceEtalLibre != -1) {
			marche.utiliserEtal(indiceEtalLibre, vendeur, produit, nbProduit);
			return "Le vendeur " + vendeur.getNom() + " vend " + nbProduit + " " + produit + " à l'étal n°" + (indiceEtalLibre + 1) + ".\n";
		} else {
			return "Désolé, " + vendeur.getNom() + ", tous les étals sont occupés.\n";
		}
	}
	
	public Etal rechercherEtal(Gaulois gaulois) {
		return marche.trouverVendeur(gaulois);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etal = marche.trouverVendeur(vendeur);
		return etal.libererEtal();
	}
	
	public String afficherMarche() {
		return "Le marché du village \"" + this.nom + "\" possède plusieurs étals :\n" + marche.afficherMarche();
	}	

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}
	
	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}