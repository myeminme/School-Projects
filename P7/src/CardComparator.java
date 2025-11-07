// Mykha Floresca, 0205, I pledge on my honor that I have not given or received any unauthorized assistance on this assignment/examination.
import java.util.Comparator;

/**
 * A comparator that compares cards.
 * 
 * If suitMatters is false, then suite is ignored when comparing cards. For
 * example compare(FIVE_OF_HEARTS,FIVE_OF_CLUBS) should return 0.
 * 
 * If suitMatters is true, then ONLY when comparing two cards with the same
 * rank, the cards are ordered by their suit (according to the natural order of
 * Suit, which means the order which they are in in the Suit Enum). For example,
 * compare(FIVE_OF_HEARTS, FIVE_OF_CLUBS) should return a positive number.
 * 
 * In other words, compare by rank first. Then, IF the ranks are same AND
 * suitMatters is true, compare by suit also.
 */
public class CardComparator implements Comparator<Card> {

	final boolean suitMatters;

	/**
	 * Constructor that takes the flag for whether the suit matters or not.
	 */
	public CardComparator(boolean suitMatters) {
		this.suitMatters = suitMatters;

	}

	/**
	 * Simple getter
	 */
	public boolean doesSuitMatter() {
		return suitMatters;
	}

	@Override
	public int compare(Card o1, Card o2) {
		int rankCompare = o1.getRank().ordinal() - o2.getRank().ordinal();
		if (rankCompare != 0) {
			return rankCompare;
		}
		if (suitMatters) {
			return o1.getSuit().ordinal() - o2.getSuit().ordinal();
		}
		return 0;
	}

}
