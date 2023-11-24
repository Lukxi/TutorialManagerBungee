package de.lukas.TutorialManager.Database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class QuizTable {

    @Id
    @Column(name = "playerID", nullable = false)
    int playerID = -1;

    @Column(name = "solvedQuiz", nullable = false)
    boolean solvedQuiz = false;


    public QuizTable(int playerID, boolean solvedQuiz) {

        this.playerID = playerID;
        this.solvedQuiz = solvedQuiz;

    }


    public QuizTable() {

    }

    public boolean isSolvedQuiz() {
        return solvedQuiz;
    }

    public int getPlayerID() {
        return playerID;
    }
}
