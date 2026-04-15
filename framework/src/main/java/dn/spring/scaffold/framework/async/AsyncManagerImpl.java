package dn.spring.scaffold.framework.async;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

@Component
public class AsyncManagerImpl implements AsyncManager {

    @Resource(name = "scaffoldAsyncExecutor")
    private Executor executor;

    @Override
    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
