package com.example.omnisheba;

public class Post {
    private String Key;
    private String Question;
    private String userID,Doctor,Answer,postID;
    //private String Time;

    public String getDoctor() {
        return Doctor;
    }

   public String getPostID() {
        return postID;
    }

    public void setDoctor(String doctor) {
        Doctor = doctor;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setKey(String key) {
        Key = key;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Post(String key, String question, String userID, String doctor, String answer,String postID) {
        Key = key;
        Question = question;
        this.userID = userID;
        Doctor = doctor;
        Answer = answer;
        this.postID=postID;
    }

   /* public void setTime(String time) {
        Time = time;
    }*/


    public Post() {
    }

    public String getKey() {
        return Key;
    }

    public String getQuestion() {
        return Question;
    }

    public String getUserID() {
        return userID;
    }

   // public String getTime() {
     //   return Time;
    //}
}
