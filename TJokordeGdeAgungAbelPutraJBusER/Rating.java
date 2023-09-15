package tJokordeGdeAgungAbelPutraJBusER;
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
}
