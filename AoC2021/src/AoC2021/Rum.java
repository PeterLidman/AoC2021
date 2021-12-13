package AoC2021;

import java.util.ArrayList;
import java.util.List;

class Rum {
	public String namn;
	public List<Rum> passage;

	public Rum(String rum) {
		this.namn = rum;
		passage = new ArrayList<>();
	}

	public void addPassage(Rum r) {
		passage.add(r);
	}

}