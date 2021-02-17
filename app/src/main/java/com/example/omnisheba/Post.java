package com.example.omnisheba;

/**
 * Model post class for posts in discussion forum
 * @author Faria Binte Kader
 */
public class Post {
    private String Key;
    private String Question;
    private String userID,Doctor,Answer,postID;

    /**
     * getter setter functions
     */
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

    /**
     * All parameter constructor
     * @param key
     * @param question
     * @param userID
     * @param doctor
     * @param answer
     * @param postID
     */
    public Post(String key, String question, String userID, String doctor, String answer,String postID) {
        Key = key;
        Question = question;
        this.userID = userID;
        Doctor = doctor;
        Answer = answer;
        this.postID=postID;
    }

    /**
     * Default constructor
     */
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

}
