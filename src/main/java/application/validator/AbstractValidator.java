package application.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 커스텀 유효성 검증을 위해 Validator을 구현한 클래스
 * @param <T>
 */
@Slf4j
public abstract class AbstractValidator<T> implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
    @SuppressWarnings("unchecked")
    @Override
    public void validate(Object target, Errors errors) {
        try{
            doValidate((T) target, errors);
        }catch(RuntimeException e){
            log.error("유효성 검증 에러", e);
            throw e;
        }
    }

    /**
     * 유효성 검증 로직이 들어감
     * @param form
     * @param errors
     */
    protected abstract void doValidate(final T form, final Errors errors);
}