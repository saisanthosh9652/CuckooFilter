package cuckoo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CuckooFilter {

	private int noOfBuckets;
	private int fingerPrintLength;
	private int numberOfEntriesPerBucket;
	private int maxNoOfKicks;
	private byte[][] filter;
	private List<List<Integer>> graph;

	public CuckooFilter() {
		this.noOfBuckets = 16;
		this.fingerPrintLength = 4;
		this.graph = new ArrayList();
		for (int i = 0; i < noOfBuckets; i++) {
			List<Integer> temp = new ArrayList<>();
			for (int j = 0; j < noOfBuckets; j++) {
				temp.add(0);
			}
			this.graph.add(temp);
		}
		this.numberOfEntriesPerBucket = 4;
		this.maxNoOfKicks = 20;
		this.filter = new byte[noOfBuckets][numberOfEntriesPerBucket];

	}

	private byte[] sha256(byte[] data) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			return digest.digest(data);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	private byte[] sha512(byte[] data) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			return digest.digest(data);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	private byte[] sha384(byte[] data) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-384");
			return digest.digest(data);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	private byte fingerprint(Object item) {
		byte[] itemBytes = item.toString().getBytes();
		byte[] hash = sha384(itemBytes);

		// Take the last byte of the hash
		byte lastByte = hash[hash.length - 1];

		// Take only the last 4 bits
		return (byte) (lastByte & 0b00001111);
	}

	private int hash1(int item) {
		byte fp = fingerprint(item);
		return Math.abs(Arrays.hashCode(sha256(new byte[] { fp })) % noOfBuckets);
	}

	private int hash2(int item) {
		byte fp = fingerprint(item);
		return Math.abs(Arrays.hashCode(sha512(new byte[] { fp })) % noOfBuckets);
	}

	public boolean insert(int element, int presentKick) {
		if (presentKick >= this.maxNoOfKicks) {
			return false;
		}
		byte fp = fingerprint(element);
		int pos1 = hash1(element) % this.noOfBuckets;
		int pos2 = (pos1 ^ hash2(element)) % this.noOfBuckets;
		for (int i = 0; i < this.noOfBuckets; i++) {
			if (filter[pos1][i] == 0) {
				filter[pos1][i] = fp;
				return true;
			}
			if (filter[pos2][i] == 0) {
				filter[pos2][i] = fp;
				return true;
			}
		}

		return false;
	}

	public boolean delete(int element) {
		byte fp = fingerprint(element);
		int pos1 = hash1(element) % this.noOfBuckets;
		int pos2 = (pos1 ^ hash2(element)) % this.noOfBuckets;

		for (int i = 0; i < numberOfEntriesPerBucket; i++) {
			if (filter[pos1][i] == fp) {
				filter[pos1][i] = 0;
				return true;
			}
			if (filter[pos2][i] == fp) {
				filter[pos2][i] = 0;
				return true;
			}
		}
		return false;
	}

	public boolean find(int element) {
		byte fp = fingerprint(element);
		int pos1 = hash1(element) % this.noOfBuckets;
		int pos2 = (pos1 ^ hash2(element)) % this.noOfBuckets;

		for (int i = 0; i < numberOfEntriesPerBucket; i++) {
			if (filter[pos1][i] == fp || filter[pos2][i] == fp) {
				return true;
			}
		}
		return false;
	}

	public List<List<Integer>> getGraph() {
		return this.graph;
	}

}
