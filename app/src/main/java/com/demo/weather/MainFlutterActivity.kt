package com.demo.weather

import android.os.Bundle
import android.util.Log
import androidx.annotation.NonNull
import com.demo.weather.model.apidata.City
import com.demo.weather.model.repository.RecentCityRepo
import dagger.android.AndroidInjection
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import javax.inject.Inject

class MainFlutterActivity: FlutterActivity() {
    private val CHANNEL = "flutter_module/weather"

    @Inject
    lateinit var storageRepo: RecentCityRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
                call, result ->
            // Note: this method is invoked on the main thread.
            if (call.method == "setCityList") {
                (call.arguments as? List<String>)?.forEach { name ->
                    Log.d(CHANNEL, "Insert city: $name")
                    val timestamp = System.currentTimeMillis()
                    storageRepo.insertCity(City(name, timestamp))
                }
                result.success("")
            }
        }
    }

    companion object {
        fun withCachedEngine(cachedEngineId: String): CachedEngineIntentBuilder {
            return CachedEngineIntentBuilder(
                MainFlutterActivity::class.java,
                cachedEngineId
            )
        }
    }
}
