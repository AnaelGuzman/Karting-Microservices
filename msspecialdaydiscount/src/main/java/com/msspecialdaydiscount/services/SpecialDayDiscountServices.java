package com.msspecialdaydiscount.services;

import com.msspecialdaydiscount.entities.SpecialDayDiscountEntity;
import com.msspecialdaydiscount.models.Fee;
import com.msspecialdaydiscount.models.User;
import com.msspecialdaydiscount.repository.SpecialDayDiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SpecialDayDiscountServices {
    @Autowired
    SpecialDayDiscountRepository specialDayDiscountRepository;
    @Autowired
    RestTemplate restTemplate;

    public List<SpecialDayDiscountEntity> getallSpecialDayDiscounts(){
        return specialDayDiscountRepository.findAll();
    }
    //elimina todos los descuentos especiales
    public void deleteAllSpecialDayDiscount(){
        specialDayDiscountRepository.deleteAll();
    }
    //eliminar descuento particular
    public void deleteSpecialDayDiscount(Long id){
        specialDayDiscountRepository.deleteById(id);
    }

    //crear tarifa generai con dia especial, retorna la creacion del descuento especial
    public SpecialDayDiscountEntity createSpecialDayDiscountFee(String type, Integer descuento, LocalDate fecha) {
        SpecialDayDiscountEntity newFee = new SpecialDayDiscountEntity();
        newFee.setType(type);
        newFee.setDiscount(descuento/100.0);
        newFee.setPrice(0.0);
        newFee.setDate(fecha);
        return specialDayDiscountRepository.save(newFee);
    }
    //TARIFA ESPECIAL CUMPLEAÑOS
    public SpecialDayDiscountEntity createBirthdayDiscountFee(Long idUser,LocalDate reservationDate) {
        SpecialDayDiscountEntity specialDayDiscountEntity = new SpecialDayDiscountEntity();
        if (idUser == null) {
            throw new IllegalArgumentException("El ID de Usuario no puede ser nulo");
        }
        try {
            User usuario = restTemplate.getForObject("http://msuser/api/v1/users/" + idUser, User.class);
            Fee tarifa = restTemplate.getForObject("http://msfee/api/v1/fees/" + usuario.getFeeId() , Fee.class);
            List<SpecialDayDiscountEntity> descuentosExistentes = getallSpecialDayDiscounts();
            for(SpecialDayDiscountEntity descuento: descuentosExistentes){
                if(descuento.getIdUserBirthday().equals(idUser) || descuento.getType().equals("NO CUMPLEAÑOS")){
                    deleteSpecialDayDiscount(descuento.getId());
                }
            }
            if (usuario == null) {
                throw new RuntimeException("Usuario no encontrado");
            }
            else{
                if(usuario.getFechaNacimiento().getDayOfMonth()==reservationDate.getDayOfMonth() && usuario.getFechaNacimiento().getMonthValue()==reservationDate.getMonthValue()){
                    specialDayDiscountEntity.setType("TARIFA DE CUMPLEAÑOS");
                    specialDayDiscountEntity.setDiscount(0.5);
                    specialDayDiscountEntity.setPrice(tarifa.getPrecio() - tarifa.getPrecio() * 0.5);
                    specialDayDiscountEntity.setDate(reservationDate);
                    specialDayDiscountEntity.setIdUserBirthday(idUser);
                    return specialDayDiscountRepository.save(specialDayDiscountEntity);
                }else {
                    specialDayDiscountEntity.setType("NO CUMPLEAÑOS");
                    specialDayDiscountEntity.setDiscount(0.0);
                    specialDayDiscountEntity.setPrice(0.0);
                    specialDayDiscountEntity.setDate(reservationDate);
                    specialDayDiscountEntity.setIdUserBirthday(idUser);
                    return specialDayDiscountRepository.save(specialDayDiscountEntity);
                }
            }
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RuntimeException("Tarifa no encontrada en msreservation", ex);
        } catch (RestClientException ex) {
            throw new RuntimeException("Error al comunicarse con msreservation", ex);
        }
    }

    //TARIFA FIN DE SEMANA
    public SpecialDayDiscountEntity createSpecialWeekendDiscount(LocalDate reservationDate, Integer discount){
        //VERIFICAMOS QUE SOLO EXISTA 1 DECUENTO DE FIN DE SEMANA
        List<SpecialDayDiscountEntity> descuentosExistentes = getallSpecialDayDiscounts();
        for(SpecialDayDiscountEntity descuento: descuentosExistentes){
            if(descuento.getType().equals("DESCUENTO DE FIN DE SEMANA") || descuento.getType().equals("SIN DESCUENTO")){
                deleteSpecialDayDiscount(descuento.getId());
            }
        }
        try {
            if(reservationDate.getDayOfWeek()==DayOfWeek.SATURDAY || reservationDate.getDayOfWeek()==DayOfWeek.SUNDAY){
                SpecialDayDiscountEntity newFee = new SpecialDayDiscountEntity();
                newFee.setType("DESCUENTO DE FIN DE SEMANA");
                newFee.setDiscount(discount / 100.0);
                newFee.setPrice(0.0);
                newFee.setDate(reservationDate);
                return specialDayDiscountRepository.save(newFee);
            }else {
                SpecialDayDiscountEntity newFee = new SpecialDayDiscountEntity();
                newFee.setType("SIN DESCUENTO");
                newFee.setDiscount(0.0);
                newFee.setPrice(0.0);
                newFee.setDate(reservationDate);
                return specialDayDiscountRepository.save(newFee);
            }
        }catch (Exception e){
            throw new RuntimeException("Error al crear la tarifa de fin de semana", e);
        }
    }

    //OBTENER DESCUENTOS CREADOS SOLO FIN DE SEMANA
    public Optional<SpecialDayDiscountEntity> getSpecialDayDiscountsWeekend(){
        return specialDayDiscountRepository.findByType("DESCUENTO DE FIN DE SEMANA");
    }
    //OBTENER DESCUENTO DE CUMPLEAÑOS PARA USUARIO POR ID
    public Optional<SpecialDayDiscountEntity> getSpecialDayDiscountsUser(Long idUser){
        return specialDayDiscountRepository.findByIdUserBirthday(idUser);
    }
    //OBTENER OTROS DESCUENTOS
    public Optional<SpecialDayDiscountEntity> getSpecialDayDiscountsOther(String type){
        return specialDayDiscountRepository.findByType(type);
    }
}
