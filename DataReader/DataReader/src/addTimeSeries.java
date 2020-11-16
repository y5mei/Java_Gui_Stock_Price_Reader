import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

public class addTimeSeries {
    private static int year;
    private static int month;
    private static int day;
    private static Double openPrice;
    private TimeSeries mys;

    public addTimeSeries(TimeSeries ts) {
        mys = ts;
    }
    

    public static boolean isDateTime(String s) {
        // Return true if the string is a date and time;

        try {
            year = Integer.parseInt(s.substring(0, 4));//Year
            month = Integer.parseInt(s.substring(5, 7));//Month
            day = Integer.parseInt(s.substring(8, 10));//Day
            String[] sepstr = s.split(",");
            openPrice = Double.parseDouble(sepstr[1]); //OpenPrice
            System.out.println("Year is " + s.substring(0, 4) +
                    " Month is " + s.substring(5, 7) +
                    " Day is " + s.substring(8, 10) +
                    " Price is " + openPrice);
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    // return the appended timeseries;
    public TimeSeries append(String s) {
        if (isDateTime(s)) {
            mys.addOrUpdate(new Day(day, month, year), openPrice);
        }
        return mys;
    }


    public static void main(String[] args) {
        String s1 = "2016-11-02,783.929993,784.750000,763.549988,765.559998,765.559998,5026500";
        String s2 = "Date,Open,High,Low,Close,Adj Close,Volume";
        System.out.println(isDateTime(s1));
        System.out.println(isDateTime(s2));

    }
}
