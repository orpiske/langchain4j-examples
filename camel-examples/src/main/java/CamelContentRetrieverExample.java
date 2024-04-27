import dev.langchain4j.ingestion.camel.CamelContentRetriever;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.apache.camel.builder.component.dsl.KafkaComponentBuilderFactory;
import org.apache.camel.builder.endpoint.EndpointBuilderFactory;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.builder.endpoint.dsl.KafkaEndpointBuilderFactory;
import org.apache.camel.component.kafka.KafkaComponent;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class CamelContentRetrieverExample {

    public static void main(String[] args) {
        final CamelContentRetriever.Builder builder = CamelContentRetriever.builder();

        try (var kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"))) {
            kafkaContainer.start();

            // Setup the Kafka component using the component DSL
            final CamelContentRetriever.EndpointBuilder endpointBuilder = CamelContentRetriever.endpointBuilder();

            final KafkaEndpointBuilderFactory.KafkaEndpointBuilder kafkaSource =
                    endpointBuilder.newSource("example").kafka("topic").brokers(kafkaContainer.getBootstrapServers());

            endpointBuilder.withSource("example", kafkaSource);
            builder.withEndpointBuilder(endpointBuilder);


        }

    }
}
