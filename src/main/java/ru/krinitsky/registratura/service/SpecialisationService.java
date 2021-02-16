package ru.krinitsky.registratura.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.krinitsky.registratura.domain.Specialisation;
import ru.krinitsky.registratura.reposytory.SpecialisationRepository;

import java.util.List;

@Service
public class SpecialisationService {

    private final SpecialisationRepository specialisationRepository;

    @Autowired
    public SpecialisationService(SpecialisationRepository specialisationRepository) {
        this.specialisationRepository = specialisationRepository;
    }


    // Добавление специализации
    // Перед добавлением убеждаемся что в базе нет такой специализации
    public void addSpecialisation(Specialisation specialisation) {
        specialisationRepository.save(specialisation);
    }


    // Метод возвращает лист со специализациями которые есть в базе данных
    public List<Specialisation> getSpecialisations() {
        return specialisationRepository.findAll();
    }


    // Метод метод удаляет из базы данных специализацию
    public void deleteSpecialisations(long specialisationId) {
        specialisationRepository.deleteById(specialisationId);
    }


    // Метод ищет по id специальность и возвращает ее из базы данных
    public Specialisation getSpecialisationById(long specialisationId) {
        return specialisationRepository.findById(specialisationId).get();
    }
}
