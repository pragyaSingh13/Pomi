package com.example.pomodoro;

public class GoalText {
    private String desc;
    private String date;

    public GoalText(){}

    public GoalText( String _desc, String _date)
    {
        this.desc = _desc;
        this.date = _date;
    }

    public String getDesc()
    {
        return desc;
    }

    public String getDate()
    {
        return date;
    }

    public void setDesc (String desc)
    {
        this.desc= desc;
    }

    public void setDate (String date)
    {
        this.date = date;
    }
}