package fr.ebiz.computerDatabase.validator;

import fr.ebiz.computerDatabase.dto.ComputerDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

@Component
public class ComputerValidator implements Validator {
    private final String invalideDateIN = "Date introduced invalide";
    private final String invalideDateOUT = "Date discontinued invalide";
    private final String dateNotBefore = "date introduced before date discontinued";

    /**
     * @param clazz to verify
     * @return a boolean
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return ComputerDTO.class.equals(clazz);
    }

    /**
     * @param o computerDTO object
     * @param e errors
     */
    @Override
    public void validate(Object o, Errors e) {
        LocalDateTime dateIn = null;
        LocalDateTime dateOut = null;

        ComputerDTO cp = (ComputerDTO) o;

        String date1 = cp.getDateIn();
        if (!date1.equals("")) {
            dateIn = DateTime.convertDate(date1.trim().concat(" 00:00:00"));
            if (dateIn == null) {
                e.reject(invalideDateIN);
                return;
            }
        }
        String date2 = cp.getDateOut();
        if (!date2.equals("")) {
            dateOut = DateTime.convertDate(date2.trim().concat(" 00:00:00"));
            if (dateOut == null) {
                e.reject(invalideDateOUT);
                return;

            }
        }
        if ((!date1.equals("") && (!date2.equals("")))) {
            if (!DateTime.dateCompare(date1, date2)) {
                e.reject(dateNotBefore);
                return;
            }
        }

    }
}
