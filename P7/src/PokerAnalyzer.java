/**
 * This interface is implemented by classes that can be used to analyze a poker
 * hand to determine what poker hands it contains.
 * 
 * If you aren't to the point of implementing a method, just return false.
 * 
 * NOTE: You might be given more than 5 cards. For example, you might be
 * given 7 cards. You need to determine if there exist 5 cards that satisfy the
 * requirement of each hand.
 * 
 * Poker hands are described at
 * https://en.wikipedia.org/wiki/List_of_poker_hands
 *
 */
public interface PokerAnalyzer {

	/** has two cards with the same rank */
	public boolean hasPair();

	/** has three cards with the same rank */
	public boolean hasThreeOfAKind();

	/**
	 * there are two different ranks such that there are two cards of each of those
	 * ranks
	 */
	public boolean hasTwoPair();

	/** has four cards with the same rank */
	public boolean hasFourOfAKind();

	/** Has both a triple, and also a pair of a different rank */
	public boolean hasFullHouse();

	/**
	 * Has 5 cards with consecutive ranks. Note that an Ace can be counted as low or
	 * high for a straight.
	 */
	public boolean hasStraight();

	/** Has 5 cards of the same suite */
	public boolean hasFlush();

	/** Has 5 cards with consecutive ranks all of the same suit. */
	public boolean hasStraightFlush();

}