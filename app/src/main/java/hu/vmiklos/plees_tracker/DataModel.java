package hu.vmiklos.plees_tracker;

import android.content.Context;

import androidx.room.Room;

import java.util.Date;

/**
 * Data model is the singleton shared state between the activity and the
 * service.
 */
public class DataModel
{
    private static final DataModel sDataModel = new DataModel();

    private Date mStart = null;
    private Date mStop = null;
    private Context mContext = null;
    private AppDatabase mDatabase = null;

    public static DataModel getDataModel() { return sDataModel; }

    private DataModel() {}

    void setStart(Date start) { mStart = start; }

    Date getStart() { return mStart; }

    void setStop(Date stop) { mStop = stop; }

    Date getStop() { return mStop; }

    void setContext(Context context) { mContext = context; }

    public AppDatabase getDatabase()
    {
        if (mDatabase == null)
        {
            mDatabase =
                Room.databaseBuilder(mContext, AppDatabase.class, "database")
                    .allowMainThreadQueries()
                    .build();
        }
        return mDatabase;
    }

    void storeSleep()
    {
        Sleep sleep = new Sleep();
        sleep.start = mStart.getTime();
        sleep.stop = mStop.getTime();
        getDatabase().sleepDao().insert(sleep);
    }
}