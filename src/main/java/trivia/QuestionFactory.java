package trivia;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class QuestionFactory {
    public static Map<Category, Queue<String>> createDefaultQuestions(int countPerCategory) {
        Map<Category, Queue<String>> questions = new EnumMap<>(Category.class);

        for (Category category : Category.values()) {
            Queue<String> queue = new LinkedList<>();
            for (int i = 0; i < countPerCategory; i++) {
                queue.add(category.toString() + " Question " + i);
            }
            questions.put(category, queue);
        }

        return questions;
    }
}
