package raspored;

public class Vreme {
	public static final int MINUTA_U_DANU = 1440;
	private int sat;
	private int minut;

	public Vreme() {
		sat = 0; 
		minut = 0;
	}

	public Vreme(int sat, int minut) throws GVreme {
		if (sat < 0 || sat > 23 || minut < 0 || minut > 59 || minut % 15 != 0)
			throw new GVreme();

		this.sat = sat;
		this.minut = minut;
	}

	public int vremeUMinutima() {
		return minut + sat * 60;
	}

	@Override
	public String toString() {
		return String.format("(%d:%d)", sat, minut);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vreme other = (Vreme) obj;
		if (minut != other.minut)
			return false;
		if (sat != other.sat)
			return false;
		return true;
	}

	public static Vreme saberi(Vreme vreme1, Vreme vreme2) {
		int ukupniMinuti = (vreme1.vremeUMinutima() + vreme2.vremeUMinutima()) % MINUTA_U_DANU;
		
		try {
			return new Vreme(ukupniMinuti / 60, ukupniMinuti % 60);
		} catch (GVreme g) {
			throw new Error();
		}
		
	}	
}
