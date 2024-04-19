package org.marrero.vaco;

public class Levels {

    private int levelNumber;
    private String difficulty;

    private int scoreMin;
    private int scoreMax;
    private int candyRedMin;
    private int candyGreenMin;
    private int candyBleuMin;
    private int movementMax;
    private boolean unlocked;

    public enum TypeObjectif {
        SCORE_MIN_MAX,
        CANDY_COULEUR,
        MOUVEMENTS,
        SCORE_MOUVEMENTS,
        CANDY_SCORE,
        CANDY_MOUVEMENTS
    }

    private TypeObjectif typeObjectif;


    public Levels(int levelNumber, String difficulty, int scoreMin, int scoreMax, int candyBleuMin, int candyGreenMin, int candyRedMin, int movementMax, boolean unlocked, TypeObjectif typeObjectif) {
        this.levelNumber = levelNumber;
        this.difficulty = difficulty;
        this.unlocked = unlocked;
        this.scoreMin = scoreMin;
        this.scoreMax = scoreMax;
        this.candyBleuMin = candyBleuMin;
        this.candyGreenMin = candyGreenMin;
        this.candyRedMin = candyRedMin;
        this.movementMax = movementMax;
        this.typeObjectif = typeObjectif;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public int getScoreMin() {
        return scoreMin;
    }

    public void setScoreMin(int scoreMin) {
        this.scoreMin = scoreMin;
    }

    public int getScoreMax() {
        return scoreMax;
    }

    public void setScoreMax(int scoreMax) {
        this.scoreMax = scoreMax;
    }

    public int getCandyGreenMin() {
        return candyGreenMin;
    }

    public void setCandyGreenMin(int candyGreenMin) {
        this.candyGreenMin = candyGreenMin;
    }

    public int getCandyBleuMin() {
        return candyBleuMin;
    }

    public void setCandyBleuMin(int candyBleuMin) {
        this.candyBleuMin = candyBleuMin;
    }

    public int getCandyRedMin() {
        return candyRedMin;
    }

    public void setCandyRedMin(int candyRedMin) {
        this.candyRedMin = candyRedMin;
    }

    public int getMovementMax() {
        return movementMax;
    }

    public void setMovementMax(int movementMax) {
        this.movementMax = movementMax;
    }

    public TypeObjectif getTypeObjectif() {
        return typeObjectif;
    }

    public void setTypeObjectif(TypeObjectif typeObjectif) {
        this.typeObjectif = typeObjectif;
    }

    public boolean verifierObjectifsNiveau(int score, int coupsRestants, int candyRouge, int candyBleu, int candyVert) {
        switch (typeObjectif) {
            case SCORE_MIN_MAX:
                // Vérifier si le score atteint est entre le score minimum et le score maximum
                return score >= scoreMin || score <= scoreMax;
            case CANDY_COULEUR:
                // Vérifier si le nombre de bonbons de chaque couleur est supérieur ou égal au nombre requis
                return candyRouge >= candyRedMin &&
                        candyBleu >= candyBleuMin &&
                        candyVert >= candyGreenMin;
            case MOUVEMENTS:
                // Vérifier si le nombre de coups restants est supérieur ou égal au nombre minimum de coups
                return coupsRestants <= movementMax;
            case SCORE_MOUVEMENTS:
                // Vérifier si le score atteint est supérieur ou égal au score minimum et le nombre de coups restants est inférieur ou égal au nombre maximum de coups
                return score >= scoreMin && coupsRestants <= movementMax;
            case CANDY_SCORE:
                // Vérifier si le nombre de bonbons de chaque couleur est supérieur ou égal au nombre requis et le score atteint est supérieur ou égal au score minimum
                return candyRouge >= candyRedMin &&
                        candyBleu >= candyBleuMin &&
                        candyVert >= candyGreenMin &&
                        score >= scoreMin;
            case CANDY_MOUVEMENTS:
                // Vérifier si le nombre de bonbons de chaque couleur est supérieur ou égal au nombre requis et le nombre de coups restants est inférieur ou égal au nombre maximum de coups
                return candyRouge >= candyRedMin &&
                        candyBleu >= candyBleuMin &&
                        candyVert >= candyGreenMin &&
                        coupsRestants <= movementMax;
            default:
                return false;
        }
    }

}