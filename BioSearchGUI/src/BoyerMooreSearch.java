public class BoyerMooreSearch {
	private final int R;
	private int[] right;
	private String pattern;
	
	public BoyerMooreSearch(String pattern) {
		this.R = 256;
		this.pattern = pattern;

		// position of rightmost occurrence of c in the pattern
		right = new int[R];
		for (int c = 0; c < R; c++)
			right[c] = -1;
		for (int j = 0; j < pattern.length(); j++)
			right[pattern.charAt(j)] = j;
	}

	// return offset of first match; N if no match
	public int search(String txt) {
		int M = pattern.length();
		int N = txt.length();
		int skip;
		for (int i = 0; i <= N - M; i += skip) {
			skip = 0;
			for (int j = M - 1; j >= 0; j--) {
				if (pattern.charAt(j) != txt.charAt(i + j)) {
					skip = Math.max(1, j - right[txt.charAt(i + j)]);
					break;
				}
			}
			if (skip == 0)
				return i; // found
		}
		return N; // not found
	}
}