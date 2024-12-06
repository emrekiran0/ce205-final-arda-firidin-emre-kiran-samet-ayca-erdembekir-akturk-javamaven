package com.arda.erdem.samet.emre.legalcase;

import java.io.Serializable;

public class LegalCaseDocument implements Serializable {
    private static final long serialVersionUID = 1L;
    int caseID;
    String title, plaintiff, defendant, winner, loser, decision, sentence;

    public LegalCaseDocument(int caseID, String title, String plaintiff, String defendant,
                             String winner, String loser, String decision, String sentence) {
        this.caseID = caseID;
        this.title = title;
        this.plaintiff = plaintiff;
        this.defendant = defendant;
        this.winner = winner;
        this.loser = loser;
        this.decision = decision;
        this.sentence = sentence;
    }
}

