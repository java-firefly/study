class Multi {
	public static void main(String args[]) {
		/* for循环实现乘法表 */

		int i, j;
		for (i = 1; i < 10; i++) {
			System.out.printf("for循环实现乘法表\n");
			for (j = 1; j < i + 1; j++) {
				System.out.printf("%4dx%d=%d", i, j, i * j);
				if (i == j) {
					System.out.println();
				}
			}
		}
		/* while循环实现乘法表 */
		System.out.printf("while循环实现乘法表\n");
		i = 1;
		while (i < 10) {
			j = 1;
			while (j < i + 1) {
				System.out.printf("%4dx%d=%d", i, j, i * j);
				if (i == j) {
					System.out.println();
				}
				j++;
			}
			i++;
		}

		/* do while循环实现乘法表 */
		System.out.printf("do while循环实现乘法表\n");
		i = 1;
		do {
			j = 1;
			do {
				System.out.printf("%4dx%d=%d", i, j, i * j);
				if (i == j) {
					System.out.println();
				}
				j++;
			} while (j < i + 1);
			i++;
		} while (i < 10);
	}
}
