// Mykha Floresca, 0205, I pledge on my honor that I have not given or received any unauthorized assistance on this assignment/examination.
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the PokerAnalyzer interface. It will analyze the List
 * of Cards it is given at construction. Most of the tests will only give 5
 * cards in the List, like real Poker, but some tests will contain more than 5
 * cards. In that case, you have a hand (say, a flush) if ANY 5 of those cards
 * have that hand (flush in this case).
 *
 */
public class PokerAnalysis implements PokerAnalyzer {

	private List<Card> cards;
	private int[] rankCounts;
	private int[] suitCounts;

	/**
	 * The constructor has been partially implemented for you. cards is the
	 * ArrayList where you'll be adding all the cards you're given. In addition,
	 * there are two arrays. You don't necessarily need to use them, but using them
	 * will be extremely helpful.
	 * 
	 * The rankCounts array is of the same length as the number of Ranks. At
	 * position i of the array, keep a count of the number of cards whose
	 * rank.ordinal() equals i. Repeat the same with Suits for suitCounts. For
	 * example, if your Cards are (Clubs 4, Clubs 10, Spades 2), your suitCounts
	 * array would be {2, 0, 0, 1}.
	 * 
	 * @param cards the list of cards to be added
	 */
	public PokerAnalysis(List<Card> cards) {
		this.cards = new ArrayList<Card>();
		this.rankCounts = new int[Rank.values().length];
		this.suitCounts = new int[Suit.values().length];

		for (Card c : cards) {
			this.cards.add(c);
			// gets pos of rank. num of cards per rank
			rankCounts[c.getRank().ordinal()]++; // (1(one),1(duece),2(three)...
			// gets pos of suit, num of cards per suit
			suitCounts[c.getSuit().ordinal()]++; // 5(club),4(diamonds)...
		}
	}

	@Override
	public boolean hasPair() {
		// loop through num of cards per rank
		for (int num : rankCounts) {
			// if more than two cards for that rank it means theres a pair
			if (num >= 2)
				return true;
		}
		return false;
	}

	@Override
	public boolean hasThreeOfAKind() {
		// do same think as has pair just change num comparator to three
		for (int num : rankCounts) {
			if (num >= 3)
				return true;
		}
		return false;

	}

	@Override
	public boolean hasTwoPair() {
		// count for how many pairs in deck
		int numOfPair = 0;
		// same as checking two pairs
		for (int num : rankCounts) {
			if (num >= 2)
				// add to count of pairs when 2 or more then this will return true
				numOfPair++;
		}
		return numOfPair >= 2;
	}

	@Override
	public boolean hasFourOfAKind() {
		// loop find 4
		for (int num : rankCounts) {
			if (num >= 4)
				return true;
		}
		return false;
	}

	@Override
	public boolean hasFullHouse() {
		// full houses are a three of a kind and a pair
		boolean threeKind = false, pair = false;

		for (int num : rankCounts) {
			// check for three of a kind (3 of one card in rankCount)
			if (num >= 3)
				threeKind = true;
			// checks for two of a kind
			else if (num >= 2)
				pair = true;
		}
		return threeKind && pair;
	}

	/**
	 * You don't need to implement this, but it will be helpful. This method returns
	 * true if there is a straight hand starting with the Rank r and false
	 * otherwise. As the Wikipedia page says, no straight hand can start with a
	 * Jack, Queen or King. Also look into the nextRank() method of the Rank enum.
	 */
	private boolean hasStraight(Rank r) {
		int straight = 5; // 5 cards in a straight
		Rank start = r;
		// check at least one
		for (int i = 0; i < straight; i++) {
			if (rankCounts[start.ordinal()] == 0)
				return false;
			// go to next rank
			start = start.nextRank();
		}
		// all ranks there
		return true;
	}

	@Override
	public boolean hasStraight() {
		for (Rank r : Rank.values()) {
			if (r.ordinal() > Rank.TEN.ordinal())
				break; // highest start rank
			if (hasStraight(r))
				return true;
		}

		// do all ranks have at least one card
		return rankCounts[Rank.ACE.ordinal()] > 0 && rankCounts[Rank.DEUCE.ordinal()] > 0
				&& rankCounts[Rank.THREE.ordinal()] > 0 && rankCounts[Rank.FOUR.ordinal()] > 0
				&& rankCounts[Rank.FIVE.ordinal()] > 0;
	}

	@Override
	public boolean hasFlush() {
		// same as earlier methods. this one is 5 cards
		for (int num : suitCounts) {
			if (num >= 5)
				return true;
		}
		return false;
	}

	/**
	 * Private helper similar to hasStraight(Rank r), but this time you consider
	 * suit also. Optional, but very helpful to write.
	 */
	private boolean hasStraightFlush(Rank r, Suit s) {
		Rank start = r;

		// search for 5 consecutive cards of same suit
		for (int i = 0; i < 5; i++) {
			final Rank checkRank = start;
			boolean found = false;

			// loops through cards to find match
			for (Card card : cards) {
				if (card.getRank() == checkRank && card.getSuit() == s) {
					found = true;
					break;
				}
			}

			if (!found)
				return false;
			start = start.nextRank();
		}
		return true;
	}

	@Override
	public boolean hasStraightFlush() {
		// go through suits up to 10
		for (Suit s : Suit.values()) {
			for (Rank r : Rank.values()) {
				if (r.ordinal() > Rank.TEN.ordinal())
					break;
				if (hasStraightFlush(r, s)) // use helper
					return true;
			}
		}

		// special cases for low A
		for (Suit s : Suit.values()) {
			boolean allMatch = true;
			Rank[] lowestStraight = { Rank.ACE, Rank.DEUCE, Rank.THREE, Rank.FOUR, Rank.FIVE };

			// check if ranks exist in same suit
			for (Rank r : lowestStraight) {
				boolean found = false;
				for (Card card : cards) {
					if (card.getRank() == r && card.getSuit() == s) {
						found = true;
						break;
					}

				}
				if (!found) {
					allMatch = false;
					break;
				}
			}
			if (allMatch)
				return true;
		}
		return false;
	}

}
