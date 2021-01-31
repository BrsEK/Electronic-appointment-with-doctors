package ru.krinitsky.registratura.service;

import org.springframework.stereotype.Service;
import ru.krinitsky.registratura.domain.Doctor;
import ru.krinitsky.registratura.domain.Subscriber;
import ru.krinitsky.registratura.reposytory.SubscriberRepository;

@Service
public class SubscriberService {

    private SubscriberRepository subscriberRepository;
    private DoctorService doctorService;
    private MailService mailService;


    public SubscriberService(SubscriberRepository subscriberRepository, DoctorService doctorService, MailService mailService) {
        this.subscriberRepository = subscriberRepository;
        this.doctorService = doctorService;
        this.mailService = mailService;
    }

    //Создаем нового подписчика, привязываем к нему доктора
    public void createSubscriber(String email, long doctorId) {
        Subscriber subscriber = new Subscriber(email, doctorService.getDoctorById(doctorId));
        subscriberRepository.save(subscriber);
    }


    // Метод оповещает всех подписчиков у выбранного врача
    public void notifySubscribers(Doctor doctor) {
        if (!doctor.getSubscribers().isEmpty()) {
            doctor.getSubscribers().forEach(
                    subscriber -> {
                        Doctor doctor1 = doctorService.getDoctorById(doctor.getId());
                        mailService.send(subscriber.getEmail(), "Уведомление от поликлиники",
                                createMessage(doctor));
                        subscriberRepository.delete(subscriber);
                    });
        }
    }


    // Метод формирует сообщение с доктором на которого подписан клиент
    private String createMessage(Doctor doctor) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Здравствуйте, уведомляем Вас о появлении новых талонов, \n");
        stringBuilder.append("успейте записаться.\n");
        stringBuilder.append("с уважением ");
        stringBuilder.append(doctor.getSurname() + " " + doctor.getName() + " " + doctor.getPatronymic());
        return stringBuilder.toString();
    }
}
