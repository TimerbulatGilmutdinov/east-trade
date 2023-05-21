package ru.itis.easttrade.converters;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import ru.itis.easttrade.models.Topic;

import java.util.HashSet;
import java.util.Set;

public class StringToTopicConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        HashSet<ConvertiblePair> pairSet = new HashSet<>();
        pairSet.add(new ConvertiblePair(String.class, Topic.class));
        pairSet.add(new ConvertiblePair(Topic.class, String.class));
        return pairSet;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (String.class.equals(sourceType.getType()) && Topic.class.equals(targetType.getType())) {
            String topic = source.toString();
            return Topic.valueOf(topic.toUpperCase());
        }
        if (Topic.class.equals(sourceType.getType()) && String.class.equals(targetType.getType())) {
            Topic topic1 = (Topic) source;
            return topic1.name();
        }
        throw new IllegalArgumentException("Invalid types to convert");
    }
}
