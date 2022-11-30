import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.cw_2.exception.QuestionAlreadyExistException;
import pro.sky.cw_2.exception.QuestionNotFoundException;
import pro.sky.cw_2.model.Question;
import pro.sky.cw_2.service.impl.JavaQoestionService;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(MockitoExtension.class)
public class JavaQuestionServiceTest {

    private final JavaQoestionService javaQoestionService = new JavaQoestionService();

    @Test
    public void add1Test() {
        javaQoestionService.add(new Question("test", "test"));

        assertThatExceptionOfType(QuestionAlreadyExistException.class)
                .isThrownBy(() -> javaQoestionService.add(new Question("test", "test")));
        assertThat(javaQoestionService.getAll()).containsExactlyInAnyOrder(new Question("test", "test"));
    }

    @Test
    public void add2Test() {
        String question = "test";
        String answer = "test";
        Question q = new Question(question, answer);
        javaQoestionService.add(question, answer);

        assertThatExceptionOfType(QuestionAlreadyExistException.class)
                .isThrownBy(() -> javaQoestionService.add(question, answer));
        assertThat(javaQoestionService.getAll()).containsExactlyInAnyOrder(q);
    }

    @Test
    public void removeTest() {
        javaQoestionService.add(new Question("test", "test"));
        javaQoestionService.remove(new Question("test", "test"));

        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() -> javaQoestionService.remove(new Question("test", "test")));
    }

    @ParameterizedTest
    @MethodSource("questions")
    public void getRandomQuestionTest(Set<Question> questions) {
        questions.forEach(javaQoestionService::add);

        assertThat(javaQoestionService.getRandomQuestion()).isIn(javaQoestionService.getAll());
    }


    public static Stream<Arguments> questions() {
        return Stream.of(
                Arguments.of(
                        Set.of(
                                new Question("Question1", "Answer1"),
                                new Question("Question2", "Answer2"),
                                new Question("Question3", "Answer3")
                        )
                )
        );
    }

}
