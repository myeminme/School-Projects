/**
 * A Card represents an immutable playing card. For example, a card object that
 * represents the five of hearts will always represent the five of hearts.
 */
public class Card {

	final Rank rank;
	final Suit suit;

	/** Initial a new card with the specified rank and suit */
	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	/** Return the rank of this card */
	public Rank getRank() {
		return rank;
	}

	/** Return the rank of this card */
	public Suit getSuit() {
		return suit;
	}

	@Override
	public String toString() {
		return rank + " of " + suit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Card))
			return false;
		Card other = (Card) obj;
		if (rank != other.rank)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}

}
