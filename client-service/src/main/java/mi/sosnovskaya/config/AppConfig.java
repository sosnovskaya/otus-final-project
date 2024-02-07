package mi.sosnovskaya.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.annotation.Nullable;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
@SuppressWarnings("java:S2095")
public class AppConfig {
    private static final int THREAD_POOL_SIZE = 2;

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public WebClient datastoreClient(WebClient.Builder builder, @Value("${datastore.url}") String url) {
        return builder.baseUrl(url).build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ReactorResourceFactory reactorResourceFactory() {
        var eventLoopGroup = new NioEventLoopGroup(THREAD_POOL_SIZE, new ThreadFactory() {
            private final AtomicLong threadIdGenerator = new AtomicLong(0);

            @Override
            public Thread newThread(@Nullable Runnable task) {
                return new Thread(task, "client-thread-" + threadIdGenerator.incrementAndGet());
            }
        });

        var resourceFactory = new ReactorResourceFactory();
        resourceFactory.setLoopResources(b -> eventLoopGroup);
        resourceFactory.setUseGlobalResources(false);
        return resourceFactory;
    }

    @Bean
    public ReactorClientHttpConnector reactorClientHttpConnector(ReactorResourceFactory resourceFactory) {
        return new ReactorClientHttpConnector(resourceFactory, mapper -> mapper);
    }

//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
//        return builder -> {
//            builder.simpleDateFormat(DATE_TIME_FORMAT);
//            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
//        };
//    }
}
