package demo.simple.service;

import com.github.microservice.components.data.mongo.mongo.data.MongoDataCleanTask;
import demo.simple.domain.BaseUser;
import org.springframework.stereotype.Component;


@Component
public class TestDataCleanService extends MongoDataCleanTask<BaseUser> {

    @Override
    public void clean(BaseUser[] baseUsers) {
        System.out.println(Thread.currentThread() + ":" + baseUsers.length);
    }

}
