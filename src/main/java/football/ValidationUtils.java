package football;

public class ValidationUtils {
    public static void validateString(String value, String exMsg){
        if(value == null || value.trim().isEmpty()){
            throw new NullPointerException(exMsg);
        }
    }
    public static void validateInt(int value, String exMsg){
        if(value <= 0){
            throw new IllegalArgumentException(exMsg);
        }
    }
}
