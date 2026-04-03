package dn.spring.scaffold.framework.threadpool;

import dn.spring.scaffold.framework.web.trace.TraceContextHolder;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public class ContextDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        final Map<String, String> traceContext = TraceContextHolder.copy();
        return new Runnable() {
            @Override
            public void run() {
                try {
                    TraceContextHolder.restore(traceContext);
                    runnable.run();
                } finally {
                    TraceContextHolder.clear();
                }
            }
        };
    }
}
