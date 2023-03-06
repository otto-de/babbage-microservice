package de.otto.babbage.core.metrics

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.TimeGauge
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.actuate.health.HealthEndpoint
import org.springframework.boot.actuate.health.Status.UP
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.availability.ReadinessState
import org.springframework.context.event.EventListener
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import javax.management.timer.Timer.ONE_SECOND
import kotlin.concurrent.schedule
import kotlin.system.exitProcess

@Component
@ConditionalOnProperty("babbage.metrics.startup-metric.enabled", havingValue = "true")
class StartupDurationReporter(
    registry: MeterRegistry,
    environment: Environment,
    private val healthEndpoint: HealthEndpoint
) {
    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(StartupDurationReporter::class.java)

        private var startTime: Long = -1

        /**
         * !!! init() must be invoked manually before SpringApplication.run() is called !!!
         */
        @JvmStatic
        fun init() {
            startTime = System.currentTimeMillis()
        }
    }

    private var isUp = false
    private var isAcceptingTraffic = false
    private var timeGaugeValue: AtomicInteger = AtomicInteger(0)

    init {
        if (startTime == -1L) {
            LOG.error("start time was not set. must be manually set before SpringApplication.run()");
            exitProcess(1)
        }

        TimeGauge.builder(
            "application_ready_duration",
            timeGaugeValue,
            TimeUnit.SECONDS
        ) { obj: AtomicInteger -> obj.toDouble() }
            .tag("stage", environment.activeProfiles[0])
            .register(registry)

        Timer().schedule(0, ONE_SECOND) {
            val status = healthEndpoint.health().status
            if (status == UP) {
                isUp = true
                LOG.info("service is up now ($status)")
                checkStartupFinished();
                cancel();
            } else {
                LOG.info("service is not up yet ($status)")
            }
        }
    }

    @EventListener
    fun listen(event: ReadinessState) {
        if (event == ReadinessState.ACCEPTING_TRAFFIC) {
            isAcceptingTraffic = true;
            LOG.info("service is accepting traffic now ($event)")
            checkStartupFinished();
        }
    }

    private fun checkStartupFinished() {
        if (isUp && isAcceptingTraffic && timeGaugeValue.get() == 0) {
            val duration = (System.currentTimeMillis() - startTime) / ONE_SECOND
            LOG.info("service startup finished after ${Duration.ofSeconds(duration)}")
            timeGaugeValue.set(duration.toInt());
        }
    }

}
