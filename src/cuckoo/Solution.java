package cuckoo;

import java.util.List;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		CuckooFilter cuckooFilter = new CuckooFilter();
		Scanner scanner = new Scanner(System.in);
		int choice = 1;
		while (choice < 5 && choice >= 1) {
			System.out.println("Enter value 1.insert 2.delete 3.lookup 4.getCuckooGraph 5.exit");
			choice = scanner.nextInt();
			int element;
			switch (choice) {
			case 1:
				System.out.println("Enter the value to insert into cuckoo");
				element = scanner.nextInt();
				boolean isInserted = cuckooFilter.insert(element, 1);
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
				List<List<Integer>> graph = cuckooFilter.getGraph();
				int m = graph.size();
				int n = graph.get(0).size();
				for (int i = 0; i < m; i++) {
					for (int j = 0; j < n; j++) {
						System.out.print(graph.get(i).get(j) + " ");
					}
					System.out.println();
				}
				break;
			case 5:
				break;

			}

		}
		scanner.close();
	}

}
