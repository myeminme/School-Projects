public enum Rank {
  DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;
  
  /** Get the next rank, wrapping around. 
   * Thus KING.nextRank() == ACE, and ACE.nextRank() == DEUCE
   * 
   */
  public Rank nextRank() {
    if (this == Rank.ACE) return Rank.DEUCE;
    return Rank.values()[this.ordinal()+1];
  }

};
