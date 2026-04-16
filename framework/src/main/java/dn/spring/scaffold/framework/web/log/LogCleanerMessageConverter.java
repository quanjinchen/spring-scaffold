package dn.spring.scaffold.framework.web.log;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class LogCleanerMessageConverter extends MessageConverter {

    @Override
    public String convert(ILoggingEvent event) {
        String message = event.getFormattedMessage();
        if (message == null) {
            return "";
        }
        return message.replace('\r', ' ')
                .replace('\n', ' ')
                .replace('\t', ' ');
    }
}
