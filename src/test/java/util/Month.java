package util;

public enum Month {
    enero(1), febrero(2), marzo(3), abril(4), mayo(5), junio(6), julio(7), agosto(8), septiembre(9), octubre(10), noviembre(11), diciembre(12);

    private int number;
    Month(int number) {
        this.number = number;
    }

    public int getValue() {
        return number;
    }
}
