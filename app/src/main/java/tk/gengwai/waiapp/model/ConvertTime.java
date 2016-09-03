package tk.gengwai.waiapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by danielchan303 on 1/9/2016.
 */
public class ConvertTime {
    private ConvertTime() {
    }

    static String convertTime(Long unixtime) {
        Date dateObject = new Date(unixtime);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yy hh:mmaa");
        return dateFormatter.format(dateObject);
    }
}
