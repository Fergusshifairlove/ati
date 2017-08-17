package ar.edu.itba.guice;

import ar.edu.itba.services.FxmlLoaderService;
import ar.edu.itba.services.ImageService;
import ar.edu.itba.services.impl.ImageServiceImpl;
import ar.edu.itba.services.impl.FxmlLoaderServiceImpl;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;

public final class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EventBus.class).asEagerSingleton();
        bind(FxmlLoaderService.class).to(FxmlLoaderServiceImpl.class).asEagerSingleton();
        bind(ImageService.class).to(ImageServiceImpl.class).asEagerSingleton();
    }
}
