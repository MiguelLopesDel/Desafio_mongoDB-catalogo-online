package com.miguel.catalogchallenge.service.aws;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import com.miguel.catalogchallenge.domain.aws.MessageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AwsSnsService {

    private final AmazonSNS snsClient;
    private final Topic catalogTopic;

    public AwsSnsService(AmazonSNS snsClient, @Qualifier("catalogTopic") Topic catalogTopic) {
        this.snsClient = snsClient;
        this.catalogTopic = catalogTopic;
    }


    public void publish(MessageDTO messageDTO) {
        String message = messageDTO.message();
        snsClient.publish(catalogTopic.getTopicArn(), message);
    }
}
