package gameserver.logging;

import java.util.ArrayList;
import java.util.List;

import com.commons.util.TimeUtil;

public class LogEntry {

    private List<String> fields = new ArrayList<String>();

    public LogEntry(Object... args) {
        this.addFields(args);
    }

    public void addFields(Object... args) {
        for (Object field : args) {
            this.fields.add(field == null ? "(NULL)" : field.toString());
        }
    }
    
    public String toStringEx() {
    	StringBuilder buffer = new StringBuilder();
        int i = 0;
        for (String field : fields) {
            buffer.append("\"").append(field).append("\"");
            i++;
            if (i < fields.size()) {
                buffer.append(',');
            }
        }
        return buffer.toString();
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(TimeUtil.GetDateString());
        buffer.append('\t');
        int i = 0;
        for (String field : fields) {
            buffer.append(field);
            i++;
            if (i < fields.size()) {
                buffer.append('\t');
            }
        }
        return buffer.toString();
    }
}