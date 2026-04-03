package dn.spring.scaffold.framework.async;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
public class AsyncManagerImpl implements AsyncManager {

    private final Executor executor;

    public AsyncManagerImpl(@Qualifier("scaffoldAsyncExecutor") Executor executor) {
        this.executor = executor;
    }

    @Override
    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
