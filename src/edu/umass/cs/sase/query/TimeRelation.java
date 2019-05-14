package edu.umass.cs.sase.query;

public class TimeRelation {
    public static String BEFORE = "$token_2.timestamp > $token_1.endTimestamp";
    public static String MEETS = "$token_1.endTimestamp = $token_2.timestamp";
    public static String OVERLAPS = "$token_1.timestamp < $token_2.timestamp\n"
            + "$token_2.timestamp < $token_1.endTimestamp\n"
            + "$token_1.endTimestamp < $token_2.endTimestamp";
    public static String STARTS = "$token_1.timestamp = $token_2.timestamp\n"
            + "$token_1.endTimestamp < $token_2.endTimestamp\n"
            + "$token_1.durationTime > 0";
    public static String DURING = "$token_1.timestamp < $token_2.timestamp\n"
            + "$token_1.endTimestamp > $token_2.endTimestamp";
    public static String ENDS = "$token_1.timestamp > $token_2.timestamp\n"
            + "$token_1.endTimestamp = $token_2.endTimestamp";
    public static String EQUALS = "$token_2.endTimestamp = $token_1.endTimestamp\n"
            + "$token_2.timestamp = $token_.timestamp";

    public static String setToken(String timeRelation, String token_1, String token_2) {
        String result = timeRelation.replace("$token_1", token_1);
        result = result.replace("$token_2", token_2);
        result = "AND " + result;
        if (result.contains("\n")) {
            result = result.replace("\n", "\nAND ");
        }
        return result;
    }
}
