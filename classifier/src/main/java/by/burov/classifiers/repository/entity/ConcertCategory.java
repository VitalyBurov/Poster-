package by.burov.classifiers.repository.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import by.burov.classifiers.controllers.utills.json.LocalDateTimeDeserializer;
import by.burov.classifiers.controllers.utills.json.LocalDateTimeSerializer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "concert_category", schema = "spring")
public class ConcertCategory {

     @Id
     @GeneratedValue(generator = "UUID")
     @GenericGenerator(
             name = "UUID",
             strategy = "org.hibernate.id.UUIDGenerator")
     @Column(name = "uuid", updatable = false, nullable = false)
     private UUID uuid;

     @Column(name = "dt_create")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     private LocalDateTime dtCreate;

     @Column(name = "dt_update")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     private LocalDateTime dtUpdate;

     @Column(name = "title")
     private String title;


     public UUID getUuid() {
          return uuid;
     }

     public void setUuid(UUID uuid) {
          this.uuid = uuid;
     }

     public LocalDateTime getDtCreate() {
          return dtCreate;
     }

     public void setDtCreate(LocalDateTime dtCreate) {
          this.dtCreate = dtCreate;
     }

     public LocalDateTime getDtUpdate() {
          return dtUpdate;
     }

     public void setDtUpdate(LocalDateTime dtUpdate) {
          this.dtUpdate = dtUpdate;
     }

     public String getTitle() {
          return title;
     }

     public void setTitle(String title) {
          this.title = title;
     }

}
