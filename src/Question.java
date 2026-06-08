public class Question {
    protected String title;
    protected String questionText;
    protected int difficulty;
    protected String code;  // subject code

    public Question() {}

    public Question(String title, String questionText, int difficulty, String code) {
        this.title = title;
        this.questionText = questionText;
        this.difficulty = difficulty;
        this.code = code;
    }


}
