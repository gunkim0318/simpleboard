package application.util;

import org.springframework.validation.Errors;

import java.util.HashMap;
import java.util.Map;

/**
 * Errors객체를 Map으로 변환함
 */
public class ErrorsTransUtil {
    private final Map<String, String> errorMap;

    /**
     * TODO Errors객체를 받아서 변환하는 작업
     * @param errors
     */
    public ErrorsTransUtil(Errors errors){
        Map<String, String> errorMap = new HashMap<String, String>();
        errors.getFieldErrors().stream().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String errorMsg = fieldError.getDefaultMessage();

            errorMap.put(fieldName, errorMsg);
        });
        this.errorMap = errorMap;
    }

    /**
     * 커스텀 에러 메시지 추가
     * @param fieldName
     * @param errorMsg
     */
    public void addCustomErrorMsg(String fieldName, String errorMsg){
        this.errorMap.put(fieldName, errorMsg);
    }
    /**
     * 생성자를 통해 변환된 Map을 반환함.
     * @return
     */
    public Map<String, String> getMap(){
        return this.errorMap;
    }
}