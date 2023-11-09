package com.TJokordeGdeAgungAbelPutraJBusER;

public class Rating{
    private long count;
    private long total;
    
    public Rating(){
        this.total = 0;
        this.count = 0;
    }
    public void insert(int rating){
        this.total += rating;
        this.count += 1;
    }
    public long getAverage(){
        return this.total / this.count;
    }
    public long getCount(){
        return this.count;
    }
    public long getTotal(){
        return this.total;
    }
    public String toString(){
        String count_str = String.valueOf(this.count);
        String total_str = String.valueOf(this.total);
        return count_str + total_str;
    }
}
