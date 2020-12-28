package com.example.omnisheba;

public class Post {
    private String Key;
    private String Question;
    private String userID;
    //private String Time;

    public void setKey(String key) {
        Key = key;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

   /* public void setTime(String time) {
        Time = time;
    }*/

    public Post(String key, String question, String userID) {
        Key = key;
        Question = question;
        this.userID = userID;
      //  Time = time;
    }

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
