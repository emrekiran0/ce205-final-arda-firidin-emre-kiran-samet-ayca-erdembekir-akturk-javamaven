package com.arda.erdem.samet.emre.legalcase;

class BPlusTreeNode {
    boolean isLeaf;               // Yaprak düğüm olup olmadığını belirtir
    int numKeys;                  // Mevcut anahtar sayısı
    int[] keys;                   // Anahtarları tutan dizi
    LegalCase[] cases;            // LegalCase nesnelerini tutar
    BPlusTreeNode[] children;     // Çocuk düğümleri tutan dizi
    BPlusTreeNode next;           // Sonraki yaprak düğüme işaretçi

    // Maksimum anahtar sayısını temsil eden sabit
    public static final int MAX = 20;

    // Yapıcı (Constructor)
    public BPlusTreeNode(boolean isLeaf) {
        this.isLeaf = isLeaf;                    // Yaprak mı değil mi
        this.numKeys = 0;                        // Başlangıçta anahtar sayısı 0
        this.keys = new int[MAX + 1];            // Anahtar dizisi
        this.cases = new LegalCase[MAX + 1];     // LegalCase dizisi
        this.children = new BPlusTreeNode[MAX + 2]; // Çocuklar dizisi
        this.next = null;                        // Sonraki düğüm başlangıçta null
    }

	public void printSortedCases(BPlusTreeNode root) {
		// TODO Auto-generated method stub
		
	}}