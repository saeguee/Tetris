package Logic;

public class UserState {
    private String name;
    private int score;

    public UserState(){
        this.name =  String.valueOf(System.currentTimeMillis());
        this.score = 0;
    }

    public UserState(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String toString(){
        return String.format("%-23s%-5d%-1s" , name, score, "分");
    }

   /* public final void setName(String name) {
        this.name = name;
    }*/
    public final void setScore(int score) {
        this.score = score;
    }
    public final String getName() {
        return name;
    }
    public final int getScore() {
        return score;
    }
}
