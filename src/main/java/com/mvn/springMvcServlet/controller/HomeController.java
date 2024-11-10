//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mvn.springMvcServlet.controller;

import com.mvn.springMvcServlet.Service.IDetail;
import com.mvn.springMvcServlet.Service.IMail;
import com.mvn.springMvcServlet.Service.IUser;
import com.mvn.springMvcServlet.Service.MailService;
import com.mvn.springMvcServlet.model.Mail;
import com.mvn.springMvcServlet.model.User;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/home"})
public class HomeController {
    @Autowired
    IUser dbUser;
    @Autowired
    IMail dbMail;
    @Autowired
    IDetail dbDetail;
    @Autowired
    HttpSession session;
    @Autowired
    MailService mailService;

    public HomeController() {
    }

    @GetMapping({""})
    public String index(Model model, @Param("keyword") String keyword) {
        User user = (User)this.session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        } else {
            List<Mail> listMail = this.mailService.listAll(keyword);
            List<Mail> listMailOfUser = new ArrayList();
            Iterator var5 = listMail.iterator();

            while(var5.hasNext()) {
                Mail m = (Mail)var5.next();
                if (m.getSenderID() == user.getId()) {
                    listMailOfUser.add(m);
                } else if (m.getReceiverID() == user.getId()) {
                    listMailOfUser.add(m);
                }
            }

            model.addAttribute("listMail", listMailOfUser);
            model.addAttribute("keyword", keyword);
            model.addAttribute("mailCount", listMailOfUser.size());
            model.addAttribute("currentUserMail", user.getMailAddress());
            model.addAttribute("currentUserName", user.getUserName());
            return "/home/mail";
        }
    }

    @GetMapping({"/sent"})
    public String sent(HttpSession session, Model model) {
        User sender = (User)session.getAttribute("currentUser");
        if (sender == null) {
            return "redirect:/login";
        } else {

            List<Mail> listMainSend = this.dbMail.findMailBySenderID(sender.getId());
            model.addAttribute("listMail", listMainSend);
            model.addAttribute("mailCount", listMainSend.size());
            model.addAttribute("currentUserMail", sender.getMailAddress());
            model.addAttribute("currentUserName", sender.getUserName());
            return "/home/sent";
        }
    }
    @GetMapping({"/receive"})
    public String receive(HttpSession session, Model model) {
        User current = (User)session.getAttribute("currentUser");
        if (current == null) {
            return "redirect:/login";
        } else {
            List<Mail> listMainReceive = this.dbMail.findMailByReceiverID(current.getId());
            List<Mail> listMailOfUser = new ArrayList();
            Iterator var5 = listMainReceive.iterator();
            while(var5.hasNext()) {
                Mail m = (Mail)var5.next();
                if (m.getReceiverID() == current.getId())
                    listMailOfUser.add(m);

            }
            model.addAttribute("listMail", listMainReceive);
            model.addAttribute("mailCount", listMainReceive.size());
            model.addAttribute("currentUserMail", current.getMailAddress());
            model.addAttribute("currentUserName", current.getUserName());
            return "/home/receive";
        }
    }

    @GetMapping({"/view"})
    public String view_mail(HttpSession session, Model model, @RequestParam("id_mail") String id_mail) {
        User user = (User)session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }else {
            int id = Integer.parseInt(id_mail);
            Mail viewMail = this.dbMail.findMailById(id);
            User viewUser = this.dbUser.findUserById(viewMail.getSenderID());
            model.addAttribute("detailView", viewMail);
            model.addAttribute("viewUser", viewUser.getMailAddress());
            model.addAttribute("currentUserMail", user.getMailAddress());
            model.addAttribute("currentUserName", user.getUserName());
        }
        return "/home/view";

    }

    @GetMapping({"/delete"})
    public String remove(@RequestParam("id_mail") String id_mail){
        User user = (User)session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }else {
            int id = Integer.parseInt(id_mail);
            dbMail.deleteById(id);
            dbDetail.deleteById(id);
        }
        return "redirect:/home";
    }
}
