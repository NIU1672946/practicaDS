package baseNoStates;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class UserGroup {
    private ArrayList<User> users = new ArrayList<User>();
    private final ArrayList<Area> areas;

    private final int[] years;
    private final int[] months;
    private final int[] weekdays;
    private final int[] hours;
    private final int[] days;

    private ArrayList<String> actions = new ArrayList<String>();

    public UserGroup(ArrayList<Area> areas,
                     int[] years, int[] months, int[] days,int[] weekdays, int[] hours,ArrayList<String> actions){

        this.areas = areas;
        this.years = years;
        this.months = months;
        this.days = days;
        this.weekdays = weekdays;
        this.hours = hours;

        this.actions = actions;

    }

/*
    public boolean canSendRequests(LocalDateTime now){

        if (
                (years[0] <= now.getYear() && now.getYear() <= years[1])&&
                (months[0] <= now.getMonthValue() && now.getMonthValue() <= months[1])&&
                (weekdays[0] <= now.getDayOfWeek().getValue() && now.getDayOfWeek().getValue() <= weekdays[1])&&
                (hours[0] <= now.getHour() && now.getHour() <= hours[1])
        ){
            return true;
        }else{
            return false;
        }
    }

 */
    public boolean canDoAction(String action){
        return actions.contains(action);
    }

    public ArrayList<Area> getAreas(){
        return areas;
    }
    public void addUser(User user){
        users.add(user);
    }

    public User findUserByCredential(String credential) {
        for (User user : users) {
            if (user.getCredential().equals(credential)) {
                return user;
            }
        }
        System.out.println("user with credential " + credential + " not found");
        return null; // otherwise we get a Java error
    }

}
