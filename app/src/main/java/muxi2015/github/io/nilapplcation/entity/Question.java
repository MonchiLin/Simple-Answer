package muxi2015.github.io.nilapplcation.entity;

/**
 * Created by muxi on 6/13/2017.
 */

public class Question {

    //编号
    private int ID;
    //四个选项
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    //问题描述
    private String questionDetail;
    //答案
    private int answer;
    //解答
    private String ruslt;
    //选择的答案
    private int selectAnswer;

    public Question() {
    }

    public Question(int ID, String optionA, String optionB, String optionC, String optionD, String questionDetail, int answer, String ruslt, int selectAnswer) {
        this.ID = ID;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.questionDetail = questionDetail;
        this.answer = answer;
        this.ruslt = ruslt;
        this.selectAnswer = selectAnswer;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getRuslt() {
        return ruslt;
    }

    public void setRuslt(String ruslt) {
        this.ruslt = ruslt;
    }

    public int getSelectAnswer() {
        return selectAnswer;
    }

    public void setSelectAnswer(int selectAnswer) {
        this.selectAnswer = selectAnswer;
    }

    public String getQuestionDetail() {
        return questionDetail;
    }

    public void setQuestionDetail(String questionDetail) {
        this.questionDetail = questionDetail;
    }
}
