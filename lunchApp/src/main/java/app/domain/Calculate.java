package app.domain;

public class Calculate {
    private int totalPrice;
    private String kingNum;
    private int personCount;
    private int kingPrice;

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getKingNum() {
        return kingNum;
    }

    public void setKingNum(String kingNum) {
        this.kingNum = kingNum;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public int getKingPrice() {
        return kingPrice;
    }

    public void setKingPrice(int kingPrice) {
        this.kingPrice = kingPrice;
    }
}
