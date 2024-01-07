package com.banana.bananawhatsapp.controlers;

import com.banana.bananawhatsapp.models.Message;
import com.banana.bananawhatsapp.models.User;
import com.banana.bananawhatsapp.services.IServiceMessage;
import lombok.Setter;

import java.util.List;

@Setter
public class ControlerMessages {
    private IServiceMessage servicioMensajeria;

    public boolean enviarMensaje(Integer remitente, Integer destinatario, String texto) {
        try {
            User uRemitente = new User();
            uRemitente.setId(remitente);
            User uDestinatario = new User();
            uDestinatario.setId(destinatario);

            Message message = servicioMensajeria.enviarMensaje(uRemitente, uDestinatario, texto);
            System.out.println("Mensaje enviado: " + message);
            return true;
        } catch (Exception e) {
            System.out.println("Ha habido un error: " + e.getMessage());
            throw e;
        }

    }


    public boolean mostrarChat(Integer remitente, Integer destinatario) {
        try {
            User uRemitente = new User();
            uRemitente.setId(remitente);
            User uDestinatario = new User();
            uDestinatario.setId(destinatario);

            List<Message> messages = servicioMensajeria.mostrarChatConUsuario(uRemitente, uDestinatario);
            if (messages != null && messages.size() > 0) {
                System.out.println("Mensajes entre: " + remitente + " y " + destinatario);
                for (Message message : messages) {
                    System.out.println(message);
                }
            } else {
                System.out.println("NO hay mensajes entre: " + remitente + " y " + destinatario);
            }
            return true;
        } catch (Exception e) {
            System.out.println("Ha habido un error: " + e.getMessage());
            throw e;
        }

    }

    public boolean eliminarChatConUsuario(Integer remitente, Integer destinatario) {
        try {
            User uRemitente = new User();
            uRemitente.setId(remitente);
            User uDestinatario = new User();
            uDestinatario.setId(destinatario);

            boolean checkOk = servicioMensajeria.borrarChatConUsuario(uRemitente, uDestinatario);
            if (checkOk) {
                System.out.println("Mensajes borrados entre: " + remitente + " y " + destinatario);
            } else {
                System.out.println("NO se han borrado mensajes entre: " + remitente + " y " + destinatario);
            }
            return checkOk;
        } catch (Exception e) {
            System.out.println("Ha habido un error: " + e.getMessage());
            throw e;
        }

    }


}
