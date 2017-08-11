package ar.edu.itba.guice;

import ar.edu.itba.service.FxmlLoaderService;
import ar.edu.itba.service.impl.FxmlLoaderServiceImpl;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;

public final class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EventBus.class).asEagerSingleton();
        bind(FxmlLoaderService.class).to(FxmlLoaderServiceImpl.class).asEagerSingleton();
    }
}
