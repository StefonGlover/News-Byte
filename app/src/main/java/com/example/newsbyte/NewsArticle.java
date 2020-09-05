package com.example.newsbyte;

public class NewsArticle {

    //Instance variables for the objects
    private String sectionName;
    private String publicationDate;
    private String webTitle;
    private String webURL;
    private String type;
    private String author;


    public NewsArticle(String sectionName, String publicationDate, String webTitle, String webURL, String type, String author) {
        this.sectionName = sectionName;
        this.publicationDate = publicationDate;
        this.webTitle = webTitle;
        this.webURL = webURL;
        this.type = type;
        this.author = author;
    }

    public NewsArticle() {
    }

    public String getType() {
        return type;
    }

    public String getAuthor() {
        return author;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebURL() {
        return webURL;
    }

    @Override
    public String toString() {
        return "NewsArticle{" +
                "sectionName='" + sectionName + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                ", webTitle='" + webTitle + '\'' +
                ", webURL='" + webURL + '\'' +
                '}';
    }
}
