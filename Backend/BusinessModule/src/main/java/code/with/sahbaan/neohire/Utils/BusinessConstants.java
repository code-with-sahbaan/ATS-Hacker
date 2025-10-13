package code.with.sahbaan.neohire.Utils;

import java.util.Map;

public class BusinessConstants {
    public static final String QUALIFICATIONS = "Qualifications";

    public static final String NICE_TO_HAVE = "NiceToHave";

    public static final String RESPONSIBILITIES = "Responsibilities";

    public static final Map<String, Double> SECTION_WEIGHTS = Map.of(
            QUALIFICATIONS, 2.0,
            RESPONSIBILITIES, 1.5
    );
}
