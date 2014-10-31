package mobile.output;

import com.truemesh.squiggle.output.Output;

import java.util.List;

public class ToStringerCriteria {
    
    public static String toStringCriteria(OutputableCriteria outputable,List criteria) {
        Output out = new Output("    ");
        outputable.writeCriteria(out,criteria);
        return out.toString();
    }
}
