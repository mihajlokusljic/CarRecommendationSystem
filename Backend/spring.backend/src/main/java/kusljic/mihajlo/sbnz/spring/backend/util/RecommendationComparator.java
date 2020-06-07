package kusljic.mihajlo.sbnz.spring.backend.util;

import java.util.Comparator;

import kusljic.mihajlo.sbnz.spring.backend.facts.Recommendation;

public class RecommendationComparator implements Comparator<Recommendation> {

	@Override
	public int compare(Recommendation rec1, Recommendation rec2) {
		int scoresComparison = new Double(rec1.getScore()).compareTo(rec2.getScore());
		if (scoresComparison != 0) {
			return scoresComparison;
		}
		
		return rec1.getCarModel().getName().compareToIgnoreCase(rec2.getCarModel().getName());
	}

}
