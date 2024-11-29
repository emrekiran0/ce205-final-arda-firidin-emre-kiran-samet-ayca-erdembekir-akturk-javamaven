package com.arda.erdem.samet.emre.legalcase;

class PlaintiffNode {
    LegalCase data;
    PlaintiffNode xorLink;

    public PlaintiffNode(LegalCase data) {
        this.data = data;
        this.xorLink = null;
    }}
