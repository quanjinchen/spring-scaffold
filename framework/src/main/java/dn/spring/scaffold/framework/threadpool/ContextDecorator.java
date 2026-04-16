package dn.spring.scaffold.framework.threadpool;

import dn.spring.scaffold.framework.web.trace.TraceContextHolder;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public class ContextDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        final Map<String, String> traceContext = TraceContextHolder.copy();
        final String traceId = MDC.get("traceId");
        return new Runnable() {
            @Override
            public void run() {
                try {
                    TraceContextHolder.restore(traceContext);
                    if (traceId != null) {
                        MDC.put("traceId", traceId);
                    }
                    runnable.run();
                } finally {
                    MDC.remove("traceId");
                    TraceContextHolder.clear();
                }
            }
        };
    }
}
