package com.client.ws.dptplus.integration;

public interface MailIntegration {

    void send(String mailTo, String message, String subject);

}
