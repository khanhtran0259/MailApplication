package com.mvn.springMvcServlet.Service;

import com.mvn.springMvcServlet.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MailService {
    @Autowired
    private IMail iMail;
    public List<Mail>  listAll(String keyword){
        if(keyword !=null){
            return iMail.findAll(keyword);
        }
        return (List<Mail>) iMail.findAll();
    }

}
