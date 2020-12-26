package com.demo.weather

import com.demo.weather.di.DaggerAppComponent
import dagger.android.DaggerApplication
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor


@Suppress("unused")
class SPWeatherApplication : DaggerApplication() {
    private val appComponent = DaggerAppComponent.builder()
        .application(this)
        .build()

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        // Instantiate a FlutterEngine.
        val flutterEngine = FlutterEngine(this);
        // Configure an initial route.
        //flutterEngine.navigationChannel.setInitialRoute("/");
        // Start executing Dart code to pre-warm the FlutterEngine.
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        );
        // Cache the FlutterEngine to be used by FlutterActivity or FlutterFragment.
        FlutterEngineCache
            .getInstance()
            .put(FLUTTER_ENGINE_ID, flutterEngine);
    }

    override fun applicationInjector() = appComponent

    companion object {
        val FLUTTER_ENGINE_ID = "flutter_engine"
    }
}
