package identification_of_rice.utils;

public class RiceResultUtils {
    private String msg;

    private String result;

    public RiceResultUtils() {
    }

    public RiceResultUtils(String msg, String result) {
        this.msg = msg;
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RiceResultUtils{" +
                "msg='" + msg + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    public static String convertData(String msg) {
        switch (msg) {
            case "LeafBlast":
                return "稻瘟病";
            case "BrownSpot":
                return "褐斑病";
            case "Hispa":
                return "Hispa";
            default:
                return "Healthy";
        }
    }
}
