package ru.estartsev.edms.service;

import com.haulmont.cuba.core.app.UniqueNumbers;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service(EntityCodeCreateService.NAME)
public class EntityCodeCreateServiceBean implements EntityCodeCreateService {

    @Inject
    private UniqueNumbers uniqueNumbers;

    /**
     * Вспомогательная функция для создания кода нескольких видов сущностей
     *
     * @param prefix   - префикс формируемого кода
     * @param sequence - номер из последовательности
     * @return возвращает отформатированный код
     */
    @Override
    public String createCode(String prefix, String sequence) {
        return String.format("%s%06d", prefix, uniqueNumbers.getNextNumber(sequence));
    }
}