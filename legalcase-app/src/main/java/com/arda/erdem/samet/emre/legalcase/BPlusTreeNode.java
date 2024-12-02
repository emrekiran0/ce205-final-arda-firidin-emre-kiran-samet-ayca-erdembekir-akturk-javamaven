package com.arda.erdem.samet.emre.legalcase;

class BPlusTreeNode {
    boolean isLeaf;               // Yaprak düğüm olup olmadığını belirtir
    int numKeys;                  // Mevcut anahtar sayısı
    int[] keys;                   // Anahtarları tutan dizi
    LegalCase[] cases;            // LegalCase nesnelerini tutar
    BPlusTreeNode[] children;     // Çocuk düğümleri tutan dizi (iç düğümler için)
    BPlusTreeNode next;           // Yaprak düğümler arasında bağlantıyı sağlayan işaretçi

    public static final int MAX = 50; // Maksimum anahtar sayısı

    // Yapıcı (Constructor): Yeni bir düğüm oluşturur
    public BPlusTreeNode(boolean isLeaf) {
        this.isLeaf = isLeaf;                    // Yaprak düğüm olup olmadığını ayarlar
        this.numKeys = 0;                        // Başlangıçta anahtar sayısını sıfır yapar
        this.keys = new int[MAX];                // Anahtarları saklamak için bir dizi oluşturur
        this.cases = new LegalCase[MAX];         // LegalCase nesnelerini saklamak için bir dizi oluşturur
        this.children = new BPlusTreeNode[MAX + 1]; // Çocuk düğümler için dizi
        this.next = null;                        // Yaprak düğümler arasında bağlantı yok
    }
}
