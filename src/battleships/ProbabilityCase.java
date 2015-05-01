package battleships;

class ProbabilityCase extends Case
{
    /**
     * Due to the large number of grids, and therefore cases, and no need for
     * great precision, we use floats instead of doubles for probabilities
     */
    private float probabilityIsShip = 0.0f;

    ProbabilityCase(float probability) {
        super();
        this.probabilityIsShip = probability;
    }

    float getProbabilityIsShip() {
        return this.probabilityIsShip;
    }

    void setProbabilityIsShip(float probability) {
        this.probabilityIsShip = probability;
    }
}
