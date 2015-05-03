package battleships;

public class Coordinates {
    
    private int x;
    private int y;

    Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getY() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }

    int getX() {
        return x;
    }

    void setX(int x) {
        this.x = x;
    }
    
    static boolean alignedX(Coordinates[] coords) {
        boolean aligned = true;
        int firstX = coords[0].getX();
        for (int i = 1; i < coords.length; i++) {
            if (coords[i].getX() != firstX) {
                aligned = false;
                break;
            }
        }
        return aligned;
    }
    
    static boolean alignedY(Coordinates[] coords) {
        boolean aligned = true;
        int firstY = coords[0].getY();
        for (int i = 1; i < coords.length; i++) {
            if (coords[i].getY() != firstY) {
                aligned = false;
                break;
            }
        }
        return aligned;
    }
    
    static int minX(Coordinates[] coords) {
        int min = coords[0].getX();
        for (int i = 1; i < coords.length; i++) {
            if ( coords[i].getX() < min ) {
                min = coords[i].getX();
            }
        }
        return min;
    }
    
    static int maxX(Coordinates[] coords) {
        int max = coords[0].getX();
        for (int i = 1; i < coords.length; i++) {
            if ( coords[i].getX() > max ) {
                max = coords[i].getX();
            }
        }
        return max;
    }
    
    static int minY(Coordinates[] coords) {
        int min = coords[0].getY();
        for (int i = 1; i < coords.length; i++) {
            if ( coords[i].getY() < min ) {
                min = coords[i].getY();
            }
        }
        return min;
    }
    
    static int maxY(Coordinates[] coords) {
        int max = coords[0].getY();
        for (int i = 1; i < coords.length; i++) {
            if( coords[i].getY() > max ) {
                max = coords[i].getY();
            }
        }
        return max;
    }
    
    static int distance(Coordinates a, Coordinates b) {
        Coordinates[] pair = {a,b};
        if (alignedX(pair)) {
            return Math.abs(a.getX() - b.getX());
        }
        else if (alignedY(pair)) {
            return Math.abs(a.getY() - b.getY());
        }
        else {
            throw new IllegalArgumentException("coordinates are not aligned in either axis");
        }
    }
    
    static int distance(Coordinates[] coords) {
        if (alignedX(coords)) {
            return Math.abs(maxX(coords) - minX(coords));
        }
        else if (alignedY(coords)) {
            return Math.abs(maxY(coords) - minY(coords));
        }
        throw new IllegalArgumentException("coordinated must be vertical or horizontal");
    }
}
