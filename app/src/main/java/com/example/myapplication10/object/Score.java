package com.example.myapplication10.object;

public class Score implements Comparable<Score>{

    private String title = "";

    private String image = "";

    private int score = 0;

    private double longitude = 0;

    private double latitude = 0;

    public Score() {
    }

    public String getTitle() {
        return title;
    }

    public Score setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Score setImage(String image) {
        this.image = image;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Score setScore(int score) {
        this.score = score;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public  int compareTo(Score score){
        Integer score1 = this.getScore();
        Integer score2 = score.getScore();
        return score2.compareTo(score1);
    }


    public String toString() {
        return "Score{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", score=" + score + '\'' + ", longitude=" + longitude + '\'' + ", latitude=" + latitude +
                '}';
    }


}
