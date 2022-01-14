package org.jhotdraw.io;


public class StreamPosTokenizerProduct {
	private char[] slashStar = new char[] { '/', '*' };
	private char[] starSlash = new char[] { '*', '/' };

	public char[] getSlashStar() {
		return slashStar;
	}

	public char[] getStarSlash() {
		return starSlash;
	}

	/**
	* Sets the slash star and star slash tokens. Due to limitations by this implementation, both tokens must have the same number of characters and the character length must be either 1 or 2.
	*/
	public void setSlashStarTokens(String slashStar, String starSlash, StreamPosTokenizer streamPosTokenizer) {
		if (slashStar.length() != starSlash.length()) {
			throw new IllegalArgumentException(
					"SlashStar and StarSlash tokens must be of same length: '" + slashStar + "' '" + starSlash + "'");
		}
		if (slashStar.length() < 1 || slashStar.length() > 2) {
			throw new IllegalArgumentException(
					"SlashStar and StarSlash tokens must be of length 1 or 2: '" + slashStar + "' '" + starSlash + "'");
		}
		this.slashStar = slashStar.toCharArray();
		this.starSlash = starSlash.toCharArray();
		streamPosTokenizer.commentChar(this.slashStar[0]);
	}
}