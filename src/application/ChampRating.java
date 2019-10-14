package application;

public class ChampRating {
	public String name;
	public double rating;
	public int noRatings;
	
	public ChampRating(String name, double rating, int noRatings) {
		this.name = name;
		this.rating = rating;
		this.noRatings = noRatings;
	}
	
	public double getRating() {
		return rating;
	}
	
	public void addRating(int rating) {
		this.rating = ((noRatings * this.rating) + rating) / (noRatings + 1);
		noRatings++;
	}
}
