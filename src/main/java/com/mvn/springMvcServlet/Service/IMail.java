//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mvn.springMvcServlet.Service;

import com.mvn.springMvcServlet.model.Mail;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMail extends CrudRepository<Mail, Integer> {
    List<Mail> findMailBySenderID(int senderID);


    List<Mail> findMailByReceiverID(int receivedID);

    Mail findMailByReceiverIDAndSenderID(int senderID, int receivedID);

    @Query("select m from Mail m, User u where m.body LIKE %?1%" + "or m.subject like %?1%" +"or u.mailAddress like %?1%")
    List<Mail> findAll(String keyword);

    void deleteMailById(int id);
    Mail findMailById(int id);



}
