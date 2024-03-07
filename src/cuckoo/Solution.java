package cuckoo;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter noOfBuckets,fingerPrintLength, numberOfEntriesPerBucket,maxNoOfKicks");

		CuckooFilter cuckooFilter = new CuckooFilter(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(),
				scanner.nextInt());

		int choice = 1;
		while (choice < 5 && choice >= 1) {
			System.out.println("Enter value 1.insert 2.delete 3.lookup 4.getCuckooGraph 5.exit");
			choice = scanner.nextInt();
			int element;
			switch (choice) {
			case 1:
				System.out.println("Enter the value to insert into cuckoo");
				element = scanner.nextInt();
				boolean isInserted = cuckooFilter.insert(element);
				if (isInserted) {
					System.out.println("Element inserted successfully");
				} else {
					System.out.println("Element could not be inserted");
				}
				break;
			case 2:
				System.out.println("Enter the value to be deleted from cuckoo");
				element = scanner.nextInt();
				boolean isDeleted = cuckooFilter.delete(element);
				if (isDeleted) {
					System.out.println("Element deleted successfully");
				} else {
					System.out.println("Element not present in cuckoo to delete");
				}
				break;
			case 3:
				System.out.println("Enter the value to be searched in cuckoo");
				element = scanner.nextInt();
				boolean isFound = cuckooFilter.find(element);
				if (isFound) {
					System.out.println("Element found successfully");
				} else {
					System.out.println("Element not present in cuckoo");
				}
				break;
			case 4:
				int[][] cfGraph = cuckooFilter.generateCuckooFilterGraph();
				printCuckooFIlterGraph(cfGraph);
				break;
			case 5:
				break;

			}

		}
		scanner.close();
	}

	public static void printCuckooFIlterGraph(int[][] graph) {
		for (int[] row : graph) {
			for (int cell : row) {
				System.out.print(cell + " ");
			}
			System.out.println();
		}
	}
}
