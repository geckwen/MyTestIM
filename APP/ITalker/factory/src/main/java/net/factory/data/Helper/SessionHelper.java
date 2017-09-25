package net.factory.data.Helper;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.factory.model.db.Session;
import net.factory.model.db.Session_Table;

/**
 * Created by CLW on 2017/9/24.
 */

public class SessionHelper {
    public static Session findLocal(String id) {
        return SQLite.select()
                .from(Session.class)
                .where(Session_Table.id.eq(id))
                .querySingle();
    }
}
