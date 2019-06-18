package com.digitalclassroom15.digitalclassroom.model;

import com.google.firebase.database.Exclude;

/**
 * Created by mahmu on 3/5/2018.
 */

public class Upload {
    private String mtitle;
    private String mImageUrl;
    private String className;
    private String subjectName;
    private String lastDate;
    private String sectionName;
    private String key;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String title, String imageUrl) {
        if (title.trim().equals("")) {
            title = "No Title";
        }

        mtitle = title;
        mImageUrl = imageUrl;
    }

    public Upload(String className, String title, String imageUrl) {

        if (title.trim().equals("")) {
            title = "No Title";
        }
        this.mtitle = title;
        this.mImageUrl = imageUrl;
        this.className = className;
    }

    public Upload(String className, String sectionName, String subjectName, String mtitle, String mImageUrl, String lastDate) {

        if (mtitle.trim().equals("")) {
            mtitle = "Null";
        }
        if (className.trim().equals("")) {
            className = "Null";
        }

        this.className = className;
        this.sectionName = sectionName;
        this.subjectName = subjectName;
        this.mtitle = mtitle;
        this.mImageUrl = mImageUrl;
        this.lastDate = lastDate;
    }

    public String getName() {
        return mtitle;
    }

    public void setName(String title) {
        mtitle = title;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }


    @Exclude
    public String getMkey() {
        return key;
    }

    @Exclude
    public void setMkey(String key) {
        this.key = key;
    }
}